package ph.adamw.electrolode.block.machine.softener;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.inventory.item.IDischargeSlot;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerSoftener extends BaseMachineContainer {
    private final int INPUT = 0;

    public ContainerSoftener(IInventory playerInv, TileInventoriedMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT, 55, 20, tileEntity));
    }

    public int[] getChargeSlotPos() {
        return new int[] {55,50};
    }
}
