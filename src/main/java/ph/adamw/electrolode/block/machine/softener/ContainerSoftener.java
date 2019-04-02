package ph.adamw.electrolode.block.machine.softener;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.gui.GuiPoint;
import ph.adamw.electrolode.inventory.MachineContainer;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerSoftener extends MachineContainer {
    private final int INPUT = 0;

    public ContainerSoftener(IInventory playerInv, TileInventoriedMachine e) {
        super(playerInv, e);
    }

    public void addOwnSlots(IItemHandler itemHandler) {
        addSlotToContainer(new SlotRecipeInput(itemHandler, INPUT, 55, 20, tileEntity));
    }

    public GuiPoint getChargeSlotPos() {
        return new GuiPoint(55,50);
    }
}
