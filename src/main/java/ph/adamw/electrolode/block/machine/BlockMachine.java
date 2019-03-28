package ph.adamw.electrolode.block.machine;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockHorizontalDirectional;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.item.core.IExtendedDescription;
import ph.adamw.electrolode.rendering.machine.BakedMachineModel;
import ph.adamw.electrolode.rendering.machine.MachineTESR;
import ph.adamw.electrolode.util.InventoryUtils;

public abstract class BlockMachine extends BlockHorizontalDirectional implements ITileEntityProvider, IExtendedDescription {
    public BlockMachine() {
        super(Material.IRON, true);
        setHardness(4.0f);
        BlockManager.registerTileEntity(getTileClass(), getBlockName());
    }

    public abstract Class<? extends TileBaseMachine> getTileClass();

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }


    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileentity = world.getTileEntity(pos);
        if(tileentity == null) {
            return;
        }

        IItemHandler inv = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inv != null) {
            InventoryUtils.dropInventoryItems(world, pos, inv);
            world.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileBaseMachine te = (TileBaseMachine) world.getTileEntity(pos);

        if (te != null) {
            playerIn.openGui(Electrolode.instance, te.getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public void initModel() {
        final ModelResourceLocation resourceLocation = BakedMachineModel.getModelResourceLocation(this);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return resourceLocation;
            }
        };

        ModelLoader.setCustomStateMapper(this, ignoreState);

        ClientRegistry.bindTileEntitySpecialRenderer(getTileClass(), new MachineTESR());
    }
}
