package ph.adamw.electrolode.block.machine.press;

import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class BlockPress extends BlockMachine {
    public String getDescription() {
        return "An electric machine that can be used to press#s clean powders and plates into other item and components.";
    }

    @Override
    public Class<? extends TileBaseMachine> getTileClass() {
        return TilePress.class;
    }

    public String getBlockName() {
        return "press";
    }
}
