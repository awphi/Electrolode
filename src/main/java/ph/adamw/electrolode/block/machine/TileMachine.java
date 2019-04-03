package ph.adamw.electrolode.block.machine;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.Config;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.energy.ElectroEnergyReceiver;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;
import ph.adamw.electrolode.manager.GuiManager;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;
import ph.adamw.electrolode.util.SidedHashMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TileMachine extends TileEntity implements ICapabilityProvider, ITickable {
    @Getter
    @Setter
    private ElectroEnergyStorage energy;

    double processedTime = 0;
    private boolean toUpdate = false;
    public boolean autoEject = Config.autoEjectDefault;

    public int getGuiId() {
        return GuiManager.getGuiId(this);
    }

    public SidedHashMap faceMap = new SidedHashMap();
    private List<EnumFacing> disabledFaces = new ArrayList<>();
    public List<EnumFaceRole> potentialRoles = new ArrayList<>();

    public TileMachine() {
        addPotentialFaceRoles();
        if(!potentialRoles.contains(EnumFaceRole.NO_ROLE)) {
            potentialRoles.add(EnumFaceRole.NO_ROLE);
        }

        energy = new ElectroEnergyReceiver(this, getBaseMaxEnergy());
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    protected abstract void addPotentialFaceRoles();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    public IBlockState getState() {
        return world.getBlockState(this.pos);
    }

    public void markForUpdate() {
        toUpdate = true;
    }

    @Override
    public void update() {
        tick();

        if(toUpdate) {
            toUpdate = false;
            world.markBlockRangeForRenderUpdate(pos, pos);
            world.notifyBlockUpdate(pos, getState(), getState(), 3);
            world.scheduleBlockUpdate(pos, this.getBlockType(),0,0);
            markDirty();
        }
    }

    public void tick() {
        if(!world.isRemote) {
            if (canProcess()) {
                if(energy.extractEnergyInternal(getEnergyUsage(), true) >= getEnergyUsage()) {
                    processedTime ++;
                    energy.extractEnergyInternal(getEnergyUsage(), false);

                    if (processedTime >= getProcTime()) {
                        processingComplete();
                        resetProcess();
                    }

                    markForUpdate();
                }
            } else {
                resetProcess();
            }
        }
    }

    /* Inventory NBT management */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("processedTime")) {
            /* Side configuration */
            for(int i : compound.getIntArray("configuredFaces")) {
                int roleId = compound.getInteger(EnumFacing.byIndex(i).getName() + "role");
                int index = compound.getInteger(EnumFacing.byIndex(i).getName() + "index");
                faceMap.put(EnumFacing.byIndex(i), EnumFaceRole.getRole(roleId), index);
            }

            for(int i : compound.getIntArray("disabledFaces")) {
                disabledFaces.add(EnumFacing.byIndex(i));
            }
            /* --- */
            processedTime = compound.getDouble("processedTime");
            energy = ElectroEnergyStorage.fromNBT(this, compound.getCompoundTag("energyStorage"));
            autoEject = compound.getBoolean("autoEject");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        /* Side configuration */
        int[] x = new int[faceMap.keySet().size()];
        EnumFacing[] arr = faceMap.keySet().toArray(new EnumFacing[faceMap.keySet().size()]);
        for(int i = 0; i < arr.length; i ++) {
            x[i] = arr[i].getIndex();
        }
        compound.setIntArray("configuredFaces", x);
        for(int i : x) {
            compound.setInteger(EnumFacing.byIndex(i).getName() + "role", faceMap.getRole(EnumFacing.byIndex(i)).ordinal());
            compound.setInteger(EnumFacing.byIndex(i).getName() + "index", faceMap.getContainerIndex(EnumFacing.byIndex(i)));
        }

        int[] y = new int[disabledFaces.size()];
        for(int i = 0; i < disabledFaces.size(); i ++) {
            y[i] = disabledFaces.get(i).getIndex();
        }
        compound.setIntArray("disabledFaces", y);
        /* --- */


        compound.setDouble("processedTime", processedTime);
        compound.setTag("energyStorage", energy.toNBT());
        compound.setBoolean("autoEject", autoEject);
        return compound;
    }
    /* --- */

    /* Syncing tile entity props between client and server */
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        handleUpdateTag(packet.getNbtCompound());
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    /* --- */

    /**
     *
     * @param face - Face to disable - NORTH = front, EAST = right etc.
     *             (as if the machine is facing up from birds-eye view then round clockwise)
     */
    public void disableFace(EnumFacing face) {
        if(disabledFaces.contains(face)) return;

        if(face == EnumFacing.DOWN || face == EnumFacing.UP) {
            disabledFaces.add(face);
        } else {
            EnumFacing facing = this.getState().getValue(BlockMachine.FACING);
            int i = 0;
            switch (face) {
                case NORTH: i = 0; break;
                case EAST: i = 1; break;
                case SOUTH: i = 2; break;
                case WEST: i = 3; break;
            }

            for(int j = 0; j < i; j ++) {
                facing = facing.rotateY();
            }

            disabledFaces.add(facing);
        }
    }

    protected void disableAllFaces() {
        for(EnumFacing i : EnumFacing.values()) {
            disableFace(i);
        }
    }

    public boolean isFaceDisabled(EnumFacing e) {
        return disabledFaces.contains(e);
    }

    /* Basic processing stuff */
    public abstract void processingComplete();

    public boolean canProcess() {
        return RecipeHandler.hasRecipe(getClass(), getInputContents())
                && RecipeUtils.canComponentArraysStack(getNextRecipe().getOutput(), getOutputContents());
    }

    protected void resetProcess() {
        if(processedTime > 0) {
            processedTime = 0;
            markForUpdate();
        }
    }

    public int getProcTime() {
        final MachineRecipe recipe = getNextRecipe();

        if(recipe == null) {
            return Integer.MAX_VALUE;
        }

        return recipe.getEnergy() / getEnergyUsage();
    }

    public double getCompletedPercentage() {
        return (processedTime / (double) getProcTime());
    }
    /* --- */

    /* Energy */
    public abstract int getBaseEnergyUsage();

    public int getEnergyUsage() {
        // TODO include upgrades and other things that affect it
        return getBaseEnergyUsage();
    }

    public abstract int getBaseMaxEnergy();

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return (T) energy;
        }

        return super.getCapability(capability, facing);
    }


    public double getEnergyPercentage() {
        return ((double) energy.getEnergyStored() / (double) energy.getMaxEnergyStored());
    }
    /* --- */

    public abstract RecipeComponent[] getInputContents();

    public abstract RecipeComponent[] getOutputContents();

    public MachineRecipe getNextRecipe() {
        return RecipeHandler.findRecipe(this.getClass(), getInputContents());
    }
}
