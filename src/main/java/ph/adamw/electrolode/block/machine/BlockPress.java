package ph.adamw.electrolode.block.machine;

import net.minecraft.tileentity.TileEntity;
import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.tile.machine.TilePress;

public class BlockPress extends BlockMachine {
    public String getDescription() {
        return "An electric machine that can be used to press#s clean powders and plates into other item and components.";
    }

    @Override
    public Class<? extends TileEntity> getTileClass() {
        return TilePress.class;
    }

    public String getBlockName() {
        return "press";
    }
}
