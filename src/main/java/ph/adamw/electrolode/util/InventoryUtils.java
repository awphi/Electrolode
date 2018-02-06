package ph.adamw.electrolode.util;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtils {
    public static void dropInventoryItems(World world, BlockPos pos, IItemHandler inv) {
        for(int i = 0; i < inv.getSlots(); i ++) {
            if(!inv.getStackInSlot(i).isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i));
            }
        }
    }
}
