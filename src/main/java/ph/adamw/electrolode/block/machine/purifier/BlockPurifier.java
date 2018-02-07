package ph.adamw.electrolode.block.machine.purifier;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;

public class BlockPurifier extends BlockBaseMachine {
    public String getDescription() {
        return "An electric machine that can be used to#s purify dirty samples of certain elements.";
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePurifier();
    }

    public String getBlockName() {
        return "purifier";
    }
}
