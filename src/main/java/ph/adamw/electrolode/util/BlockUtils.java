package ph.adamw.electrolode.util;

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
        switch(facing) {
            case WEST: e.west();
            case EAST: e.east();
            case NORTH: e.north();
            case SOUTH: e.south();
            case UP: e.up();
            case DOWN: e.down();
            default: return e;
        }
    }
}
