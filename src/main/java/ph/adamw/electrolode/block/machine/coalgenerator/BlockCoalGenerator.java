package ph.adamw.electrolode.block.machine.coalgenerator;

import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileMachine;

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
