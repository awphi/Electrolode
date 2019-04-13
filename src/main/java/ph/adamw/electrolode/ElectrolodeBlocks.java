package ph.adamw.electrolode;

import net.minecraft.block.Block;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.BlockCable;
import ph.adamw.electrolode.block.machine.BlockPress;
import ph.adamw.electrolode.block.machine.BlockPurifier;
import ph.adamw.electrolode.block.machine.BlockSoftener;
import ph.adamw.electrolode.manager.BlockManager;

@SuppressWarnings("WeakerAccess")

/*
 * Simple little wrapper class to make referencing blocks neater and easier.
 */
public class ElectrolodeBlocks {
    public static final Block PURIFIER = getRegisteredBlock(BlockPurifier.class);
    public static final Block PRESS = getRegisteredBlock(BlockPress.class);
    public static final Block SOFTENER = getRegisteredBlock(BlockSoftener.class);
    public static final Block CABLE = getRegisteredBlock(BlockCable.class);

    private static Block getRegisteredBlock(Class<? extends BlockBase> x) {
        return BlockManager.get(x);
    }
}
