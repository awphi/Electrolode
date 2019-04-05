package ph.adamw.electrolode.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.tile.machine.core.TileMachine;

public abstract class BlockTileProvider extends BlockBase implements ITileEntityProvider {
	public BlockTileProvider(Material m, boolean addToCreative) {
		super(m, addToCreative);
		BlockManager.registerTileEntity(getTileClass(), getBlockName());
	}

	public abstract Class<? extends TileMachine> getTileClass();

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return getTileClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Could not instantiate " + getTileClass().getSimpleName() + " it needs to have an accessible no-args constructor!");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
