package ph.adamw.electrolode.tile.machine;

import ph.adamw.electrolode.inventory.machine.ContainerCoalGenerator;
import ph.adamw.electrolode.gui.machine.GuiCoalGenerator;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.tile.machine.core.TileItemGenerator;

public class TileCoalGenerator extends TileItemGenerator {
	@Override
	public Class<? extends ElectrolodeContainer> getContainerClass() {
		return ContainerCoalGenerator.class;
	}

	@Override
	public Class<? extends GuiMachine> getGuiClass() {
		return GuiCoalGenerator.class;
	}

	@Override
	public int getInputSlots() {
		return 1;
	}

	@Override
	public int getOutputSlots() {
		return 0;
	}

	@Override
	public int getBaseMaxEnergy() {
		return 50000;
	}
}
