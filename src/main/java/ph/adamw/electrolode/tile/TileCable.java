package ph.adamw.electrolode.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCable extends TileTickable implements ICapabilityProvider {
	//TODO replace these placeholder values with some balanced values and a tier system a la mekanism or thermal etc.
	private final ElectroEnergyStorage energy = new ElectroEnergyStorage(this, 500);

	private void performEnergyFlow() {
		if(!this.energy.canExtract() || energy.getEnergyStored() <= 0) {
			return;
		}

		for(EnumFacing i : EnumFacing.VALUES) {
			final TileEntity neighbour = getWorld().getTileEntity(getPos().offset(i));
			if(neighbour != null && neighbour.hasCapability(CapabilityEnergy.ENERGY, i.getOpposite())) {
				if(neighbour instanceof TileCable) {
					final TileCable cable = (TileCable) neighbour;
					if(cable.energy.getEnergyStored() > energy.getEnergyStored()) {
						continue;
					}

					cable.energy.receiveEnergy(energy.getMaxExtract(), false);
				} else {
					neighbour.getCapability(CapabilityEnergy.ENERGY, i.getOpposite()).receiveEnergy(energy.getMaxExtract(), false);
				}
			}
		}

		markForUpdate();
	}

	@Override
	public void tick() {
		performEnergyFlow();
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}

	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		}

		return super.getCapability(capability, facing);
	}
}
