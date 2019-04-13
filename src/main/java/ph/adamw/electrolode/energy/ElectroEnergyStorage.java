package ph.adamw.electrolode.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;
import ph.adamw.electrolode.tile.TileUpdatable;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;

public class ElectroEnergyStorage extends EnergyStorage {
	protected final TileUpdatable tile;

	public ElectroEnergyStorage(TileUpdatable tile, int capacity) {
		super(capacity);
		this.tile = tile;
	}

	public ElectroEnergyStorage(TileUpdatable tile, int capacity, int maxTransfer) {
		super(capacity, maxTransfer, maxTransfer, 0);
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

	public NBTTagCompound writeToNbt(NBTTagCompound compound) {
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxExtract", maxExtract);
		compound.setInteger("maxReceive", maxReceive);
		compound.setString("class", getClass().getSimpleName());
		return compound;
	}

	public static ElectroEnergyStorage readFromNBT(TileUpdatable tile, NBTTagCompound compound) {
		final ElectroEnergyStorage e;

		switch(compound.getString("class")) {
			case "ElectroEnergyStorage": e = new ElectroEnergyStorage(tile, 0); break;
			case "ElectroEnergyConsumer": e = new ElectroEnergyConsumer(tile, 0); break;
			case "ElectroEnergyProducer": e = new ElectroEnergyProducer(tile, 0); break;
			case "ElectroEnergyChannel": e = new ElectroEnergyChannel((TileEnergyChannel) tile, 0); break;
			default: return null;
		}

		e.energy = compound.getInteger("energy");
		e.maxExtract = compound.getInteger("maxExtract");
		e.maxReceive = compound.getInteger("maxReceive");
		e.capacity = compound.getInteger("capacity");
		return e;
	}
}
