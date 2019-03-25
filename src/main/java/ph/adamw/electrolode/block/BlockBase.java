package ph.adamw.electrolode.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.manager.ItemManager;

import java.util.Random;

public abstract class BlockBase extends Block {
    public BlockBase(Material m, boolean addToCreativeTab) {
        super(m);
        if(addToCreativeTab) {
            setCreativeTab(ItemManager.CREATIVE_TAB);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockManager.get(this.getClass()));
    }

    public abstract String getBlockName();


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
