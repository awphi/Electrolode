package ph.adamw.electrolode.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.tile.TileUpdatable;

public class ElectroEnergyProducer extends ElectroEnergyStorage {
	public ElectroEnergyProducer(TileUpdatable tile, int capacity) {
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

	public void attemptEnergyDump(int toDump) {
		if(!canExtract() || toDump > getEnergyStored() || getEnergyStored() <= 0) {
			return;
		}

		for(EnumFacing i : EnumFacing.VALUES) {
			// If all the energy we had to dump has been dumped to neighbours
			if(toDump <= 0) {
				break;
			}

			// Attempts to dump what's left of maxReceive to neighbours or at least, as much as the neighbour can take.
			final TileEntity neighbour = tile.getWorld().getTileEntity(tile.getPos().offset(i));
			if(neighbour != null && neighbour.hasCapability(CapabilityEnergy.ENERGY, i.getOpposite())) {
				final IEnergyStorage ies = neighbour.getCapability(CapabilityEnergy.ENERGY, i.getOpposite());

				if(!ies.canReceive()) {
					continue;
				}

				int attempt = ies.receiveEnergy(toDump, true);

				if(attempt > 0) {
					ies.receiveEnergy(attempt, false);
					toDump -= attempt;
					energy -= attempt;
				}
			}
		}

		tile.markForUpdate();
	}
}
