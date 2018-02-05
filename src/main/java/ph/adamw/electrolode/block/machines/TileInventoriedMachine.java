package ph.adamw.electrolode.block.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import ph.adamw.electrolode.inventory.InputItemStackHandler;
import ph.adamw.electrolode.inventory.InputRecipeItemStackHandler;
import ph.adamw.electrolode.inventory.OutputItemStackHandler;
import ph.adamw.electrolode.util.EnergyUtils;

public abstract class TileInventoriedMachine extends TileBaseMachine {
    protected InputItemStackHandler inputOnlySlotsWrapper = new InputRecipeItemStackHandler(new ItemStackHandler(getInputSize()), this.getClass());
    protected OutputItemStackHandler outputOnlySlotsWrapper = new OutputItemStackHandler(new ItemStackHandler(getOutputSize()));
    protected ItemStackHandler chargeSlotWrapper = new ItemStackHandler(1);
    protected CombinedInvWrapper allSlotsWrapper = new CombinedInvWrapper(inputOnlySlotsWrapper.internalSlot, outputOnlySlotsWrapper, chargeSlotWrapper);

    public abstract void ejectOutput();


    public abstract int getInputSize();
    public abstract int getOutputSize();


    public int getCombinedSize() {
        return getInputSize() + getOutputSize() + 1;
    }

    @Override
    public void resetProcess() {
        if(processedTime > 0 && autoEject) {
            ejectOutput();
        }
        super.resetProcess();
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            EnergyUtils.discharge(chargeSlotWrapper.getStackInSlot(0), this);
        }
        super.tick();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inputItems", inputOnlySlotsWrapper.serializeNBT());
        compound.setTag("outputItems", outputOnlySlotsWrapper.serializeNBT());
        compound.setTag("chargeItem", chargeSlotWrapper.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inputOnlySlotsWrapper.deserializeNBT((NBTTagCompound) compound.getTag("inputItems"));
        outputOnlySlotsWrapper.deserializeNBT((NBTTagCompound) compound.getTag("outputItems"));
        chargeSlotWrapper.deserializeNBT((NBTTagCompound) compound.getTag("chargeItem"));
    }
}
