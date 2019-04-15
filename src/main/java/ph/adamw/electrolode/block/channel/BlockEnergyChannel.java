package ph.adamw.electrolode.block.channel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;

public class BlockEnergyChannel extends BlockChannel {
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);

		final TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof TileEnergyChannel) {
			EnergyNetworkManager.neighborChanged(world, (TileEnergyChannel) tile, neighbor);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEnergyChannel && !world.isRemote) {
			EnergyNetworkManager.nodeRemoved((TileEnergyChannel) te);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		final TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEnergyChannel && !world.isRemote) {
			EnergyNetworkManager.nodeCreated(world, (TileEnergyChannel) te);

			// This must be done after as it causes a block update and the line above sets the network so attempting
			// write to NBT before network is set will cause a nasty and non-descriptive NPE
			((TileEnergyChannel) te).setTier(ChannelTier.VALUES[stack.getItemDamage()]);
		}

		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public Class<? extends TileEntity> getTileClass() {
		return TileEnergyChannel.class;
	}

	@Override
	public String getBlockName() {
		return "energychannel";
	}
}

