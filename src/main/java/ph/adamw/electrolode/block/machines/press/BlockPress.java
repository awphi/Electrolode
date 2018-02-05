package ph.adamw.electrolode.block.machines.press;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machines.BlockBaseMachine;

public class BlockPress extends BlockBaseMachine {
    public String getDescription() {
        return "An electric machine that can be used to press#s clean powders and plates into other items and components.";
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TilePress();
    }
}
