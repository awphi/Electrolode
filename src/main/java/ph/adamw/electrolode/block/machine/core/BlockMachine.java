package ph.adamw.electrolode.block.machine.core;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockProperties;
import ph.adamw.electrolode.block.BlockTileProvider;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.item.core.IExtendedDescription;
import ph.adamw.electrolode.rendering.machine.MachineTESR;
import ph.adamw.electrolode.rendering.particle.ParticleDiggingUnregistered;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.util.InventoryUtils;

public abstract class BlockMachine extends BlockTileProvider implements IExtendedDescription {
    public BlockMachine() {
        super(Material.IRON, true);
        setHardness(4.0f);
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

        TileMachine te = (TileMachine) world.getTileEntity(pos);

        if (te != null) {
            playerIn.openGui(Electrolode.instance, te.getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        if(!(world.getBlockState(pos).getBlock() instanceof BlockMachine)) {
            return false;
        }

        IBlockState state = world.getBlockState(pos);

        for (int j = 0; j < 4; ++j) {
            for (int k = 0; k < 4; ++k) {
                for (int l = 0; l < 4; ++l) {
                    double d0 = ((double)j + 0.5D) / 4.0D;
                    double d1 = ((double)k + 0.5D) / 4.0D;
                    double d2 = ((double)l + 0.5D) / 4.0D;
                    manager.addEffect((new ParticleDiggingUnregistered(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double)pos.getZ() + d2, d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, state, EnumFaceRole.NO_ROLE.resolveResourceLocation())).setBlockPos(pos));
                }
            }
        }

        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer((Class<TileMachine>) getTileClass(), new MachineTESR());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(BlockProperties.FACING, getFacingFromEntity(pos, placer)), 2);
    }

    private EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector(
                (float) (entity.posX - clickedBlock.getX()),
                0f,
                (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockProperties.FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockProperties.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState.Builder(this).add(BlockProperties.FACING).add(BlockProperties.FACEMAP).build();
    }
}
