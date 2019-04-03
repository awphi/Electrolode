package ph.adamw.electrolode.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.block.machine.TileMachine;

public class ElectroEnergyStorage extends EnergyStorage {
	protected final TileMachine tile;

	public ElectroEnergyStorage(TileMachine tile, int capacity) {
		super(capacity);
		this.tile = tile;
	}

	public int receiveEnergyInternal(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(getMaxEnergyStored() - energy, maxReceive);

		if (!simulate && energyReceived > 0) {
			energy += energyReceived;
			tile.markForUpdate();
		}

		return energyReceived;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive()) return 0;

		return receiveEnergyInternal(maxReceive, simulate);
	}

	public int extractEnergyInternal(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, maxExtract);

		if (!simulate && energyExtracted > 0) {
			energy -= energyExtracted;
			tile.markForUpdate();
		}

		return energyExtracted;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract()) return 0;

		return extractEnergyInternal(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public boolean canExtract() {
		return getEnergyStored() > 0;
	}

	@Override
	public boolean canReceive() {
		return getMaxEnergyStored() > getEnergyStored();
	}

	public NBTTagCompound toNBT() {
		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("energy", energy);
		nbt.setInteger("capacity", capacity);
		nbt.setInteger("maxExtract", maxExtract);
		nbt.setInteger("maxReceive", maxReceive);
		nbt.setString("class", getClass().getSimpleName());
		return nbt;
	}

	public static ElectroEnergyStorage fromNBT(TileMachine tile, NBTTagCompound compound) {
		final ElectroEnergyStorage e;

		switch(compound.getString("class")) {
			case "ElectroEnergyStorage": e = new ElectroEnergyStorage(tile, 0); break;
			case "ElectroEnergyReceiver": e = new ElectroEnergyReceiver(tile, 0); break;
			case "ElectroEnergyProducer": e = new ElectroEnergyProducer(tile, 0); break;
			default: return null;
		}

		e.energy = compound.getInteger("energy");
		e.maxExtract = compound.getInteger("maxExtract");
		e.maxReceive = compound.getInteger("maxReceive");
		e.capacity = compound.getInteger("capacity");
		return e;
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
