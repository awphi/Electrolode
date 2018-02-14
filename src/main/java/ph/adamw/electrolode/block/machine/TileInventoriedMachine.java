package ph.adamw.electrolode.block.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.inventory.item.*;
import ph.adamw.electrolode.util.EnergyUtils;

public abstract class TileInventoriedMachine extends TileBaseMachine {
    protected InputItemStackHandler inputOnlySlotsWrapper = new InputRecipeItemStackHandler(new ItemStackHandler(getInputSlots()), this.getClass());
    protected OutputItemStackHandler outputOnlySlotsWrapper = new OutputItemStackHandler(new ItemStackHandler(getOutputSlots()));
    protected ItemStackHandler chargeSlotWrapper = new ItemStackHandler(1);
    protected CombinedInvWrapper allSlotsWrapper = new CombinedInvWrapper(inputOnlySlotsWrapper.internalSlot, outputOnlySlotsWrapper, chargeSlotWrapper);

    public abstract void ejectOutput();


    public abstract int getInputSlots();
    public abstract int getOutputSlots();

    public int getCombinedSlots() {
        return getInputSlots() + getOutputSlots() + 1;
    }

    @Override
    public void resetProcess() {
        if(processedTime > 0 && autoEject) {
            ejectOutput();
        }
        super.resetProcess();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            ItemStackHandlerBase value;
            if(facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(allSlotsWrapper);
            } else if(faceMap.getRole(facing) == EnumFaceRole.INPUT_ITEM) {
                value = inputOnlySlotsWrapper;
            } else if(faceMap.getRole(facing) == EnumFaceRole.OUTPUT_ITEM) {
                value = outputOnlySlotsWrapper;
            } else {
                value = new DummyItemStackHandler();
            }

            value.setAllowedSlot(faceMap.getContainerIndex(facing));
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(value);
        }

        return super.getCapability(capability, facing);
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
