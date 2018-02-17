package ph.adamw.electrolode.block.machine;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.item.core.IExtendedDescription;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.InventoryUtils;

public abstract class BlockBaseMachine extends BlockBase implements ITileEntityProvider, IExtendedDescription {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBaseMachine() {
        super(Material.ROCK, true);
        setHardness(4.0f);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        BlockManager.registerTileEntity(createNewTileEntity(null, 0).getClass(), getBlockName());
    }

    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, BlockUtils.getFacingFromEntity(pos, placer, false)), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        /*
        IBlockState state = world.getBlockState(pos);
        for (IProperty<?> prop : state.getProperties().keySet()) {
            if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class) {
                Block block = state.getBlock();
                IBlockState newState;
                //noinspection unchecked
                IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
                EnumFacing facing = state.getValue(facingProperty);
                Collection<EnumFacing> validFacings = new ArrayList<>();
                for(EnumFacing i : facingProperty.getAllowedValues()) {
                    if(!(i == EnumFacing.UP || i == EnumFacing.DOWN)) validFacings.add(i);
                }

                // rotate horizontal facings clockwise
                if (validFacings.size() == 4 && !validFacings.contains(EnumFacing.UP) && !validFacings.contains(EnumFacing.DOWN)) {
                    newState = state.withProperty(facingProperty, facing.rotateY());
                } else {
                    EnumFacing rotatedFacing = facing.rotateAround(axis.getAxis());
                    if (validFacings.contains(rotatedFacing)) {
                        newState = state.withProperty(facingProperty, rotatedFacing);
                    } else {
                        newState = state.cycleProperty(facingProperty);
                    }
                }

                world.setBlockState(pos, newState);
                return true;
            }
        }
        */
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileentity = world.getTileEntity(pos);
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
        if(te != null) {
            playerIn.openGui(Electrolode.instance, te.getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
