package ph.adamw.electrolode.inventory.item;

import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerBase extends ItemStackHandler {
    private int allowedSlot;
    public ItemStackHandler internalSlot;

    public void setAllowedSlot(int index) {
        allowedSlot = index;
    }

    boolean canAccess(int slot) {
        return slot == allowedSlot || allowedSlot == -1;
    }
}
