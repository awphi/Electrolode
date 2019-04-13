package ph.adamw.electrolode.tile.channel;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.energy.ElectroEnergyCable;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;
import ph.adamw.electrolode.energy.network.EnergyNetwork;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.energy.network.EnergyRequest;
import ph.adamw.electrolode.tile.TileTickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Stack;
import java.util.UUID;

public class TileCable extends TileTickable implements ICapabilityProvider {
	@Getter
	private ElectroEnergyCable energy = new ElectroEnergyCable(this, 1000);

	private UUID networkUuid;

	public void setNetwork(EnergyNetwork network) {
		this.networkUuid = network.getUuid();
	}

	public EnergyNetwork getNetwork() {
		return EnergyNetworkManager.getNetwork(networkUuid);
	}

	private volatile Stack<EnergyRequest> requestStack = new Stack<>();

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		energy = (ElectroEnergyCable) ElectroEnergyStorage.readFromNBT(this, compound.getCompoundTag("energy"));
		networkUuid = compound.getUniqueId("networkUuid");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("energy", energy.writeToNbt(new NBTTagCompound()));
		compound.setUniqueId("networkUuid", networkUuid);
		return super.writeToNBT(compound);
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

