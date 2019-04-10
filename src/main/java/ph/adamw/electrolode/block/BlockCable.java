package ph.adamw.electrolode.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.energy.network.EnergyNetworkManager;
import ph.adamw.electrolode.tile.TileCable;

import javax.annotation.Nullable;
import java.util.Random;

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
		super.onBlockPlacedBy(world, pos, state, placer, stack);

		final TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileCable && !world.isRemote) {
			EnergyNetworkManager.nodeCreated(world, (TileCable) te);
		}
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

