package ph.adamw.electrolode.block.machine.coalgenerator;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.inventory.item.SlotRecipeInput;

public class ContainerCoalGenerator extends ElectrolodeContainer {
	public ContainerCoalGenerator(IInventory playerInventory, TileInventoriedMachine te) {
		super(playerInventory, te);
	}

	@Override
	public void addOwnSlots(IItemHandler itemHandler) {
		addSlotToContainer(new SlotRecipeInput(itemHandler, 0, 20, 35, tileEntity));
	}
}
