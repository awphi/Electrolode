package ph.adamw.electrolode.tile;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;
import ph.adamw.electrolode.energy.network.EnergyNetwork;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCable extends TileEntity implements ICapabilityProvider {
	private final ElectroEnergyStorage energy = new ElectroEnergyStorage(this, 1000);

	@Getter
	@Setter
	private EnergyNetwork network;

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

