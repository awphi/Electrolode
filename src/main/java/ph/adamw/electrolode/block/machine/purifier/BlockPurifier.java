package ph.adamw.electrolode.block.machine.purifier;

import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileMachine;

public class BlockPurifier extends BlockMachine {
    public String getDescription() {
        return "An electric purifying machine that can be used to#s clean dirty pulverized matter.";
    }

    @Override
    public Class<? extends TileMachine> getTileClass() {
        return TilePurifier.class;
    }

    public String getBlockName() {
        return "purifier";
    }
}
