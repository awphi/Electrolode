package ph.adamw.electrolode.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHorizontalDirectional extends BlockDirectional {
	public BlockHorizontalDirectional(Material m, boolean addToCreativeTab) {
		super(m, addToCreativeTab);
	}

	@Override
	public EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				0f,
				(float) (entity.posZ - clickedBlock.getZ()));
	}
}
