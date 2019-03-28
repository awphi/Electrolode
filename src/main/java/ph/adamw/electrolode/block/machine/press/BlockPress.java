package ph.adamw.electrolode.block.machine.press;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

    public TileEntity createNewTileEntity(World world, int i) {
        return new TilePress();
    }

    public String getBlockName() {
        return "press";
    }
}
