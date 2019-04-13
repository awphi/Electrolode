package ph.adamw.electrolode.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.item.core.IMetadataItem;
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
        return Item.getItemFromBlock(BlockManager.get(getClass()));
    }

    public abstract String getBlockName();

    public int getVariants() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {}

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        final Item item = Item.getItemFromBlock(this);
        ResourceLocation resource = getRegistryName();

        for(int i = 0; i < getVariants(); i ++) {
            if(item instanceof IMetadataItem) {
                resource = new ResourceLocation(Electrolode.MODID + ":" + ((IMetadataItem) item).getTexture(i));
            }

            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(resource, "inventory"));
        }
    }

    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }
}
