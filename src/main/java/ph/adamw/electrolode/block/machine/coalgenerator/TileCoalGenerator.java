package ph.adamw.electrolode.block.machine.coalgenerator;

import ph.adamw.electrolode.block.machine.TileItemGenerator;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;

public class TileCoalGenerator extends TileItemGenerator {
	@Override
	public Class<? extends ElectrolodeContainer> getContainerClass() {
		return ContainerCoalGenerator.class;
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
