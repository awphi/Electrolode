package ph.adamw.electrolode.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.block.machine.TileMachine;

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
