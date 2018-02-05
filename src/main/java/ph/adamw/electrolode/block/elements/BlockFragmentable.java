package ph.adamw.electrolode.block.elements;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.items.tools.ItemChipper;

import java.util.Random;

public abstract class BlockFragmentable extends BlockBase implements ITileEntityProvider {

    public BlockFragmentable(Material m, boolean addToCreativeTab) {
        super(m, addToCreativeTab);
        this.setBlockUnbreakable();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileFragmentable();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }

    protected abstract Item getFragment(int level);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        TileEntity te = world.getTileEntity(pos);
        return playerFragmenting(world, this, pos, playerIn, hand);
    }

    public boolean playerFragmenting(World world, BlockFragmentable block, BlockPos pos, EntityPlayer playerIn, EnumHand hand) {
        if(world.isRemote) {
            return false;
        }

        int level;

        if(playerIn.getHeldItem(hand).getItem() instanceof ItemChipper) {
            level = 1;
        } else {
            return false;
        }

        //Todo reduce durability of rock here and break if if nec.
        ItemStack drops = new ItemStack(getFragment(level),new Random().nextInt(3) + 2);

        if(playerIn.inventory.getFirstEmptyStack() == -1) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), drops);
            world.spawnEntity(item);
        } else {
            playerIn.addItemStackToInventory(drops);
        }

        return true;
    }
}
