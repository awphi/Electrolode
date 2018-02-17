package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;

/**
 * Simple dummy implementation to trick item pipes etc. into connecting to things that don't
 * have a capability by providing a dummy to connect to.
 */
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
