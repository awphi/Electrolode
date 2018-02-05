package ph.adamw.electrolode.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.lwjgl.Sys;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.GuiUtils;
import ph.adamw.electrolode.util.SidedHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TileBaseMachine extends TileEntity implements ITickable, IEnergyStorage {
    double processedTime = 0;
    private int energy = 0;
    private boolean toUpdate = false;
    private int currentEnergyUsage;
    public boolean autoEject = false;

    public int getGuiId() {
        return GuiUtils.getGuiId(this);
    }

    public SidedHashMap faceMap = new SidedHashMap();


    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

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
            world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
            markDirty();
        }
    }

    public void tick() {
        if(!world.isRemote) {
            if (canProcess()) {
                if(extractEnergy(getEnergyUsage(), true) >= getEnergyUsage()) {
                    processedTime++;
                    extractEnergy(getEnergyUsage(), false);
                    if (processedTime >= getBaseProcTime()) {
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
                int v = compound.getInteger(EnumFacing.getFront(i).getName());
                faceMap.put(EnumFacing.getFront(i), EnumFaceRole.getRole(v));
            }
            /* --- */
            processedTime = compound.getDouble("processedTime");
            energy = compound.getInteger("energyStored");
            currentEnergyUsage = compound.getInteger("energyUsage");
            autoEject = compound.getBoolean("autoEject");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        /* Side configuration */
        int[] x = new int[faceMap.keySet().size()];
        Object[] arr = faceMap.keySet().toArray();
        for(int i = 0; i < arr.length; i ++) {
            x[i] = ((EnumFacing) arr[i]).getIndex();
        }
        compound.setIntArray("configuredFaces", x);
        for(int i : x) {
            compound.setInteger(EnumFacing.getFront(i).getName(), faceMap.get(EnumFacing.getFront(i)).getValue());
        }
        /* --- */


        compound.setDouble("processedTime", processedTime);
        compound.setInteger("energyStored", energy);
        compound.setInteger("energyUsage", currentEnergyUsage);
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
    /* --- */

    /* Basic processing stuff */
    public abstract void processingComplete();

    public abstract boolean canProcess();

    protected void resetProcess() {
        if(processedTime > 0) {
            processedTime = 0;
            markForUpdate();
        }
    }

    public abstract int getBaseProcTime();

    public double getCompletedPercentage() {
        return (processedTime / (double) getBaseProcTime());
    }
    /* --- */

    /* Energy */
    public abstract int getBaseEnergyUsage();

    public int getEnergyUsage() {
        // TODO: include upgrades and other things that affect it
        // - Could be overridden in child classes to include other, machine-specific factors maybe.
        return getBaseEnergyUsage();
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) return 0;

        int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(getMaxReceive(), maxReceive));
        if (!simulate) {
            energy += energyReceived;
            markForUpdate();
        }
        markForUpdate();
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        int energyExtracted = Math.min(energy, Math.min(getMaxExtract(), maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            markForUpdate();
        }
        return energyExtracted;
    }

    public int getMaxReceive() {
        return getEnergyUsage() * 2;
    }

    public int getMaxExtract() {
        return getEnergyUsage() * 2;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public abstract int getMaxEnergyStored();

    @Override
    public boolean canExtract() {
        return this.getBaseEnergyUsage() * 2 > 0;
    }

    @Override
    public boolean canReceive() {
        return this.getBaseEnergyUsage() * 2 > 0;
    }

    public double getEnergyPercentage() {
        return ((double) getEnergyStored() / (double) getMaxEnergyStored());
    }

    /* --- */
}
