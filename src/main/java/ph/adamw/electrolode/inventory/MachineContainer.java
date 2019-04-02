package ph.adamw.electrolode.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.inventory.item.IDischargeSlot;
import ph.adamw.electrolode.inventory.item.SlotDischarge;

public abstract class MachineContainer extends ElectrolodeContainer implements IDischargeSlot {
    /*
        Player inventory:
            0 -> 8 = hotbar
            9 -> 35 = player inventory

        Tile Entity:
            0 -> tileEntity.getInputSlots() - 1 = inputs
            getInputSlots() -> getCombinedSlots() - 1 = outputs
            getCombinedSlots() = dischargeable slot

        Overall:
            0 -> 8 = hotbar
            9 -> 35 = player inventory
            36 -> 36 + (getInputSlots() - 1) = te inputs
            36 + getInputSlots() -> (you get the idea)
    */

    public MachineContainer(IInventory playerInventory, TileInventoriedMachine te) {
        super(playerInventory, te);
        final IItemHandler tileItemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotDischarge(tileItemHandler, te.getCombinedSlots() - 1, getChargeSlotPos()));
    }
}
