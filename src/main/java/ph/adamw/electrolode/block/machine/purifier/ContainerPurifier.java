package ph.adamw.electrolode.block.machine.purifier;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.gui.GuiPoint;
import ph.adamw.electrolode.inventory.item.SlotOutput;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerPurifier extends BaseMachineContainer {
    private final int INPUT = 0, OUTPUT = 1;

    public ContainerPurifier(IInventory playerInv, TileInventoriedMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT, 63, 20, tileEntity));
        addSlotToContainer(new SlotOutput(itemHandler, OUTPUT, 123, 36));
    }

    public GuiPoint getChargeSlotPos() {
        return new GuiPoint(63,50);
    }
}
