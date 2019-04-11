package ph.adamw.electrolode.energy;

import ph.adamw.electrolode.tile.TileUpdatable;

public class ElectroEnergyConsumer extends ElectroEnergyStorage {
	public ElectroEnergyConsumer(TileUpdatable tile, int capacity) {
		super(tile, capacity);
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public boolean canExtract() {
		return false;
	}
}
