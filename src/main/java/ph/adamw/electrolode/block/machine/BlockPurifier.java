package ph.adamw.electrolode.block.machine;

import net.minecraft.tileentity.TileEntity;
import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.tile.machine.TilePurifier;

public class BlockPurifier extends BlockMachine {
    public String getDescription() {
        return "An electric purifying machine that can be used to#s clean dirty pulverized matter.";
    }

    @Override
    public Class<? extends TileEntity> getTileClass() {
        return TilePurifier.class;
    }

    public String getBlockName() {
        return "purifier";
    }
}
