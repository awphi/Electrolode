package ph.adamw.electrolode.block.machine.press;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.inventory.IDischargeSlot;
import ph.adamw.electrolode.inventory.SlotOutput;
import ph.adamw.electrolode.inventory.SlotRecipeInput;

public class ContainerPress extends BaseMachineContainer implements IDischargeSlot {
    private final int INPUT_0 = 0, INPUT_1 = 1, INPUT_2 = 2, OUTPUT = 3;

    public ContainerPress(IInventory playerInv, TileItemMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_0, 81, 19, tileEntity));
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_1, 99, 19, tileEntity));
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_2, 117, 19, tileEntity));
        addSlotToContainer(new SlotOutput(itemHandler, OUTPUT, 99, 47));
    }

    public int[] getChargeSlotPos() {
        return new int[] {55, 50};
    }
}
