package ph.adamw.electrolode.block.machine.coalgenerator;

import ph.adamw.electrolode.block.machine.TileItemGenerator;

public class TileCoalGenerator extends TileItemGenerator {
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
