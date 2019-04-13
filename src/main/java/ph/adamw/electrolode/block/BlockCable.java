package ph.adamw.electrolode.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.tile.channel.TileCable;

public class BlockCable extends BlockTileProvider {
	public BlockCable() {
		super(Material.CLOTH, true);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);

		final TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof TileCable) {
			EnergyNetworkManager.neighborChanged(world, (TileCable) tile, neighbor);
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileCable && !world.isRemote) {
			EnergyNetworkManager.nodeRemoved((TileCable) te);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		final TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileCable && !world.isRemote) {
			EnergyNetworkManager.nodeCreated(world, (TileCable) te);
		}

		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new ExtendedBlockState.Builder(this).add(BlockProperties.CONNECTIONS).build();
	}

	@Override
	public Class<? extends TileEntity> getTileClass() {
		return TileCable.class;
	}

	@Override
	public String getBlockName() {
		return "cable";
	}
}

