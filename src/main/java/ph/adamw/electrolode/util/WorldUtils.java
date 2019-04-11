package ph.adamw.electrolode.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class WorldUtils {
	public static EnumFacing getFacingFrom(BlockPos from, BlockPos to) {
		return EnumFacing.getFacingFromVector(
				(float) (from.getX() - to.getX()),
				(float) (from.getY() - to.getY()),
				(float) (from.getZ() - to.getZ()));
	}
}
