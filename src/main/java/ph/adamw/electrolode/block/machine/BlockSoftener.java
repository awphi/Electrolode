package ph.adamw.electrolode.block.machine;

import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.tile.machine.TileSoftener;

public class BlockSoftener extends BlockMachine {
    public String getDescription() {
        return "An electric machine that can be used to#s soften water to be used in industrial processes.";
    }

    @Override
    public Class<? extends TileMachine> getTileClass() {
        return TileSoftener.class;
    }

    public String getBlockName() {
        return "softener";
    }
}
