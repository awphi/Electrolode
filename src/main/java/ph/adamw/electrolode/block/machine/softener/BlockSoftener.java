package ph.adamw.electrolode.block.machine.softener;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;

public class BlockSoftener extends BlockBaseMachine {
    public String getDescription() {
        return "An electric machine that can be used to#s soften water to be used in industrial processes.";
    }

    @Override
    public Class<? extends TileEntity> getTileClass() {
        return TileSoftener.class;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSoftener();
    }

    public String getBlockName() {
        return "softener";
    }
}
