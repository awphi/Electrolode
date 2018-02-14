package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InputItemStackHandler extends ItemStackHandlerBase {
    public InputItemStackHandler(ItemStackHandler hidden) {
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
        if(canAccess(slot)) {
            return internalSlot.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    public ItemStack extractItemInternally(int slot, int amount, boolean simulate) {
        return internalSlot.extractItem(slot, amount, simulate);
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
