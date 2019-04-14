package ph.adamw.electrolode.block.channel;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.block.BlockTileProvider;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.item.ItemBlockChannel;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;

public class BlockEnergyChannel extends BlockTileProvider {
	public BlockEnergyChannel() {
		super(Material.CLOTH, true);
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockChannel(this);
	}

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
	public BlockStateContainer createBlockState() {
		return new ExtendedBlockState.Builder(this).add(BlockProperties.CHANNEL_TIER).add(BlockProperties.CONNECTIONS).build();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockProperties.CHANNEL_TIER, ChannelTier.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockProperties.CHANNEL_TIER).ordinal();
	}

	@Override
	public Class<? extends TileEntity> getTileClass() {
		return TileEnergyChannel.class;
	}

	@Override
	public String getBlockName() {
		return "energychannel";
	}

	@Override
	public int getVariants() {
		return ChannelTier.VALUES.length;
	}
}

