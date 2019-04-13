package ph.adamw.electrolode.tile.channel;

import lombok.Getter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.energy.ElectroEnergyChannel;
import ph.adamw.electrolode.energy.ElectroEnergyStorage;
import ph.adamw.electrolode.energy.network.EnergyNetwork;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.energy.network.EnergyRequest;
import ph.adamw.electrolode.tile.TileTickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Stack;
import java.util.UUID;

public class TileEnergyChannel extends TileTickable implements ICapabilityProvider {
	@Getter
	private ElectroEnergyChannel energy = new ElectroEnergyChannel(this, 1000);

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
		energy = (ElectroEnergyChannel) ElectroEnergyStorage.readFromNBT(this, compound.getCompoundTag("energy"));
		networkUuid = compound.getUniqueId("networkUuid");
		super.readFromNBT(compound);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return !oldState.getBlock().equals(newState.getBlock());
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
		requestStack.add(request);
	}

	@Override
	public void tick() {
		while(!requestStack.isEmpty()) {
			energy.routeEnergy(requestStack.pop());
		}
	}

	public void setTier(ChannelTier value) {
		energy.setTier(value);
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockProperties.CHANNEL_TIER, value), 2);
	}
}

