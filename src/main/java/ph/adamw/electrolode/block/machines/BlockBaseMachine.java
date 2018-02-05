package ph.adamw.electrolode.block.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.items.IExtendedDescription;
import ph.adamw.electrolode.util.BlockUtils;

public abstract class BlockBaseMachine extends BlockBase implements ITileEntityProvider, IExtendedDescription {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBaseMachine() {
        super(Material.ROCK, true);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
        if(tileentity == null) return;
        IItemHandler inventory = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(inventory == null) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            EntityItem entityIn;
            if (stack != ItemStack.EMPTY) {
                entityIn = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                entityIn.setDefaultPickupDelay();
                world.spawnEntity(entityIn);
            }
        }
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
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
