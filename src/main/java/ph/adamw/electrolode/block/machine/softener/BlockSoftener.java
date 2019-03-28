package ph.adamw.electrolode.block.machine.softener;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class BlockSoftener extends BlockMachine {
    public String getDescription() {
        return "An electric machine that can be used to#s soften water to be used in industrial processes.";
    }

    @Override
    public Class<? extends TileBaseMachine> getTileClass() {
        return TileSoftener.class;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSoftener();
    }

    public String getBlockName() {
        return "softener";
    }
}
