package ph.adamw.electrolode.block.machine.purifier;

import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class BlockPurifier extends BlockMachine {
    public String getDescription() {
        return "An electric purifying machine that can be used to#s clean dirty pulverized matter.";
    }

    @Override
    public Class<? extends TileBaseMachine> getTileClass() {
        return TilePurifier.class;
    }

    public String getBlockName() {
        return "purifier";
    }
}
