package ph.adamw.electrolode.block.machine.purifier;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class BlockPurifier extends BlockMachine {
    public String getDescription() {
        return "An electric purifying machine that can be used to#s clean dirty pulverized matter.";
    }

    @Override
    public Class<? extends TileBaseMachine> getTileClass() {
        return TilePurifier.class;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePurifier();
    }

    public String getBlockName() {
        return "purifier";
    }
}
