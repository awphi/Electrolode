package ph.adamw.electrolode.tile.channel;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.energy.ElectroEnergyCable;
import ph.adamw.electrolode.energy.network.EnergyNetwork;
import ph.adamw.electrolode.energy.network.EnergyRequest;
import ph.adamw.electrolode.tile.TileTickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Stack;

public class TileCable extends TileTickable implements ICapabilityProvider {
	@Getter
	private final ElectroEnergyCable energy = new ElectroEnergyCable(this, 1000);

	//TODO serialization of energy networks
	@Getter
	@Setter
	private EnergyNetwork network;

	private Stack<EnergyRequest> requestStack = new Stack<>();

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

	public void routeEnergy(EnergyRequest request) {
		this.requestStack.add(request);
	}

	@Override
	public void tick() {
		while(!requestStack.isEmpty()) {
			energy.routeEnergy(requestStack.pop());
		}
	}
}

