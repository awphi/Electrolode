package ph.adamw.electrolode.block.machine;

import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.tile.machine.TileCoalGenerator;

public class BlockCoalGenerator extends BlockMachine {
	@Override
	public Class<? extends TileMachine> getTileClass() {
		return TileCoalGenerator.class;
	}

	@Override
	public String getBlockName() {
		return "coalgenerator";
	}

	@Override
	public String getDescription() {
		return "A coal-burning generator capable of#s converting coal and charcoal to redstone flux.";
	}
}
