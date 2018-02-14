package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;

public class DummyItemStackHandler extends ItemStackHandlerBase {
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }
}
