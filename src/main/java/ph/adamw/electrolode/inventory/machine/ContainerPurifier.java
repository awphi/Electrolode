package ph.adamw.electrolode.inventory.machine;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.tile.machine.core.TileInventoriedMachine;
import ph.adamw.electrolode.gui.GuiPoint;
import ph.adamw.electrolode.inventory.item.SlotOutput;
import ph.adamw.electrolode.inventory.MachineContainer;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerPurifier extends MachineContainer {
    private final int INPUT = 0, OUTPUT = 1;

    public ContainerPurifier(IInventory playerInv, TileInventoriedMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT, 63, 20, tileEntity));
        addSlotToContainer(new SlotOutput(itemHandler, OUTPUT, 123, 35));
    }

    public GuiPoint getChargeSlotPos() {
        return new GuiPoint(63,50);
    }
}
