package ph.adamw.electrolode.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machines.TileItemMachine;

public abstract class BaseMachineContainer extends Container {
    public TileItemMachine tileEntity;
    protected int TOTAL_PLAYER_INVENTORY_SIZE = 36;

    /*
        Player inventory:
            0 -> 8 = hotbar
            9 -> 35 = player inventory

        Tile Entity:
            0 -> tileEntity.getInputSize() - 1 = inputs
            getInputSize() -> getCombinedSize() - 1 = outputs
            getCombinedSize() = dischargeable slot

        Overall:
            0 -> 8 = hotbar
            9 -> 35 = player inventory
            36 -> 36 + (getInputSize() - 1) = te inputs
            36 + getInputSize() -> (you get the idea)
    */

    public BaseMachineContainer(IInventory playerInventory, TileItemMachine te) {
        this.tileEntity = te;

        addPlayerSlots(playerInventory);
        IItemHandler tileItemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addOwnSlots(tileItemHandler);
        if(this instanceof IDischargeSlot) {
            int[] pos = ((IDischargeSlot) this).getChargeSlotPos();
            addSlotToContainer(new SlotDischarge(tileItemHandler, te.getCombinedSize() - 1, pos[0], pos[1]));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (sourceSlotIndex >= 0 && sourceSlotIndex < TOTAL_PLAYER_INVENTORY_SIZE) {
            if (!mergeItemStack(sourceStack, TOTAL_PLAYER_INVENTORY_SIZE, TOTAL_PLAYER_INVENTORY_SIZE + tileEntity.getCombinedSize(), false)){
                return ItemStack.EMPTY;
            }
        } else if (sourceSlotIndex >= TOTAL_PLAYER_INVENTORY_SIZE && sourceSlotIndex < TOTAL_PLAYER_INVENTORY_SIZE + tileEntity.getCombinedSize()) {
            if (!mergeItemStack(sourceStack, 0, TOTAL_PLAYER_INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.err.print("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.canInteractWith(playerIn);
    }

    public abstract void addOwnSlots(IItemHandler itemHandler);

    // Override this to change inventory slot locations - but for all standard size machine guis this works as is
    // Key: If editing the number of player slots make sure to reflect that in TOTAL_PLAYER_INVENTORY_SIZE
    public void addPlayerSlots(IInventory playerInventory) {
        // Slots for hotbar
        for (int row = 0; row < 9; row ++) {
            int x = 8 + row * 18;
            int y = 141;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }

        // Slots for main inv
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 9; col ++) {
                int x = 8 + col * 18;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }
    }
}
