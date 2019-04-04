package ph.adamw.electrolode.energy;

import ph.adamw.electrolode.tile.machine.core.TileMachine;

public class ElectroEnergyReceiver extends ElectroEnergyStorage {
	public ElectroEnergyReceiver(TileMachine tile, int capacity) {
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
