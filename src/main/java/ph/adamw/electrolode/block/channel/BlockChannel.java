package ph.adamw.electrolode.block.channel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.property.ExtendedBlockState;
import ph.adamw.electrolode.block.BlockTileProvider;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.item.ItemBlockChannel;

public abstract class BlockChannel extends BlockTileProvider {
	public BlockChannel() {
		super(Material.CLOTH, true);
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockChannel(this);
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
	public int getVariants() {
		return ChannelTier.VALUES.length;
	}
}
