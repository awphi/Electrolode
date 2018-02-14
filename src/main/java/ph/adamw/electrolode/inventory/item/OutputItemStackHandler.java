package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class OutputItemStackHandler extends ItemStackHandlerBase {
    public OutputItemStackHandler(ItemStackHandler hidden) {
        super();
        internalSlot = hidden;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        internalSlot.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return internalSlot.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return internalSlot.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if(canAccess(slot)) {
            return internalSlot.extractItem(slot, amount, simulate);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStack insertItemInternally(int slot, ItemStack stack, boolean simulate) {
        return internalSlot.insertItem(slot, stack, simulate);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return internalSlot.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound e) {
        internalSlot.deserializeNBT(e);
    }
}