package ph.adamw.electrolode.block.machine.purifier;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.inventory.IDischargeSlot;
import ph.adamw.electrolode.inventory.SlotOutput;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.inventory.SlotRecipeInput;

public class ContainerPurifier extends BaseMachineContainer implements IDischargeSlot {
    private final int INPUT = 0, OUTPUT = 1;

    public ContainerPurifier(IInventory playerInv, TileItemMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT, 63, 19, tileEntity));
        addSlotToContainer(new SlotOutput(itemHandler, OUTPUT, 123, 36));
    }

    public int[] getChargeSlotPos() {
        return new int[] {63,51};
    }
}
