package ph.adamw.electrolode.tile.machine.core;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.Config;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.energy.ElectroEnergyReceiver;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.manager.GuiManager;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;
import ph.adamw.electrolode.tile.TileTickable;
import ph.adamw.electrolode.util.SidedHashMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TileMachine extends TileTickable implements ICapabilityProvider {
    @Getter
    @Setter
    private ElectroEnergyStorage energy;

    double processedTime = 0;
    public boolean autoEject = Config.autoEjectDefault;

    public SidedHashMap faceMap = new SidedHashMap();
    public List<EnumFaceRole> potentialRoles = new ArrayList<>();

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public int getGuiId() {
        return GuiManager.getGuiId(this);
    }

    public TileMachine() {
        addPotentialFaceRoles();
        if(!potentialRoles.contains(EnumFaceRole.NO_ROLE)) {
            potentialRoles.add(EnumFaceRole.NO_ROLE);
        }

        GuiManager.registerGui(getGuiClass(), getContainerClass(), getClass());
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
            for(int i : compound.getIntArray("configuredFaces")) {
                int roleId = compound.getInteger(EnumFacing.byIndex(i).getName() + "role");
                int index = compound.getInteger(EnumFacing.byIndex(i).getName() + "index");
                faceMap.put(EnumFacing.byIndex(i), EnumFaceRole.getRole(roleId), index);
            }

            processedTime = compound.getDouble("processedTime");
            energy = ElectroEnergyStorage.readFromNBT(this, compound.getCompoundTag("energyStorage"));
            autoEject = compound.getBoolean("autoEject");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
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

        compound.setDouble("processedTime", processedTime);
        compound.setTag("energyStorage", energy.writeToNbt(new NBTTagCompound()));
        compound.setBoolean("autoEject", autoEject);
        return compound;
    }
    /* --- */

    public abstract Class<? extends ElectrolodeContainer> getContainerClass();

    public abstract Class<? extends GuiMachine> getGuiClass();

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
            return CapabilityEnergy.ENERGY.cast(energy);
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
