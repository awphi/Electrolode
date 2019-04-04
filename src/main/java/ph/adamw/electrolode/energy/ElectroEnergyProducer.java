package ph.adamw.electrolode.energy;

import ph.adamw.electrolode.tile.machine.core.TileMachine;

public class ElectroEnergyProducer extends ElectroEnergyStorage {
	public ElectroEnergyProducer(TileMachine tile, int capacity) {
		super(tile, capacity);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
}
