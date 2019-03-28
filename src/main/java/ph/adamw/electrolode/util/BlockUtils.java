package ph.adamw.electrolode.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockUtils {
    public static EnumFacing getNextEnumFacing(EnumFacing in) {
        switch(in) {
            case NORTH: return EnumFacing.EAST;
            case EAST: return EnumFacing.SOUTH;
            case SOUTH: return EnumFacing.WEST;
            case WEST: return EnumFacing.NORTH;
        }

        throw new RuntimeException("getNextEnumFacing only supports EnumFacing.N/S/E/W! Expected one of these, got: " + in);
    }

    public static BlockPos getNeighbourPos(BlockPos e, EnumFacing facing) {
        int x = e.getX();
        int y = e.getY();
        int z = e.getZ();
        switch(facing) {
            case WEST: x--;
            case EAST: x++;
            case NORTH: z--;
            case SOUTH: z++;
            case UP: y++;
            case DOWN: y--;
            default: break;
        }
        return new BlockPos(x, y, z);
    }
}
