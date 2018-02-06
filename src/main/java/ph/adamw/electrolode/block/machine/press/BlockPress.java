package ph.adamw.electrolode.block.machine.press;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;

public class BlockPress extends BlockBaseMachine {
    public String getDescription() {
        return "An electric machine that can be used to press#s clean#n powders and plates into other item and components.";
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TilePress();
    }

    public String getBlockName() {
        return "press";
    }
}
