package ph.adamw.electrolode.tile.channel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.energy.network.EnergyNetwork;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.tile.TileTickable;

import java.util.UUID;

public abstract class TileChannel extends TileTickable implements ICapabilityProvider {
	protected UUID networkUuid;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		networkUuid = compound.getUniqueId("networkUuid");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setUniqueId("networkUuid", networkUuid);
		return super.writeToNBT(compound);
	}

	public void setNetwork(EnergyNetwork network) {
		this.networkUuid = network.getUuid();
	}

	public EnergyNetwork getNetwork() {
		return EnergyNetworkManager.getNetwork(networkUuid);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return !oldState.getBlock().equals(newState.getBlock());
	}

	public void setTier(ChannelTier value) {
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockProperties.CHANNEL_TIER, value));
	}
}
