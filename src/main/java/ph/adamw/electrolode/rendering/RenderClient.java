package ph.adamw.electrolode.rendering;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.item.core.IMetadataItem;

@SideOnly(Side.CLIENT)
public class RenderClient {
    public static void registerItemRenderer(Item item) {
        if(item instanceof IMetadataItem) {
            IMetadataItem md = (IMetadataItem) item;
            for(int i = 0; i < md.getVariants(); i ++) {
                if(md.getTexture(i) == null) continue;

                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Electrolode.MODID + ":" + md.getTexture(i), "inventory"));
                ModelBakery.registerItemVariants(item, new ResourceLocation(Electrolode.MODID + ":" + md.getTexture(i)));
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
