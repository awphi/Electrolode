package ph.adamw.electrolode.block.machine.press;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.gui.GuiPoint;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.inventory.item.SlotOutput;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerPress extends BaseMachineContainer {
    private final int INPUT_0 = 0, INPUT_1 = 1, INPUT_2 = 2, OUTPUT = 3;

    public ContainerPress(IInventory playerInv, TileInventoriedMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_0, 81, 19, tileEntity));
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_1, 99, 19, tileEntity));
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT_2, 117, 19, tileEntity));
        addSlotToContainer(new SlotOutput(itemHandler, OUTPUT, 99, 47));
    }

    public GuiPoint getChargeSlotPos() {
        return new GuiPoint(55, 50);
    }
}
