package ph.adamw.electrolode;

import net.minecraft.block.Block;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.machine.press.BlockPress;
import ph.adamw.electrolode.block.machine.purifier.BlockPurifier;
import ph.adamw.electrolode.block.machine.softener.BlockSoftener;
import ph.adamw.electrolode.manager.BlockManager;

@SuppressWarnings("WeakerAccess")

/*
 * Simple little wrapper class to make referencing blocks neater and easier.
 */
public class ModBlocks {
    public static final Block PURIFIER = getRegisteredBlock(BlockPurifier.class);
    public static final Block PRESS = getRegisteredBlock(BlockPress.class);
    public static final Block SOFTENER = getRegisteredBlock(BlockSoftener.class);

    private static Block getRegisteredBlock(Class<? extends BlockBase> x) {
        return BlockManager.get(x);
    }
}
