package ph.adamw.electrolode;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.items.ItemPowder;
import ph.adamw.electrolode.items.ItemPurifiedPowder;
import ph.adamw.electrolode.items.tools.ItemPestleMortar;
import ph.adamw.electrolode.items.tools.ItemChipper;
import ph.adamw.electrolode.rendering.RenderClient;

public class ModItems {
    public static final CreativeTabs CREATIVETAB = new CreativeTabs("electrolode") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.chipper);
        }
    };

    // Registration stage 1
    public static final ItemChipper chipper = new ItemChipper();
    public static final ItemPestleMortar pestleMortar = new ItemPestleMortar();

    //Variant items
    public static final ItemPowder powder = new ItemPowder();
    public static final ItemPurifiedPowder purifiedPowder = new ItemPurifiedPowder();

    //Registration stage 4
    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(nameItem(chipper, "chipper"));
        registry.register(nameItem(pestleMortar, "pestlemortar"));
        registry.register(nameItem(powder,"powder"));
        registry.register(nameItem(purifiedPowder, "purifiedpowder"));
    }

    //Registration stage 3
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderClient.registerItemRenderer(chipper);
        RenderClient.registerItemRenderer(pestleMortar);
        RenderClient.registerItemRenderer(powder);
        RenderClient.registerItemRenderer(purifiedPowder);
    }

    private static Item nameItem(Item i, String name) {
        return i.setUnlocalizedName(Electrolode.MODID + "." + name).setRegistryName(Electrolode.MODID + ":" + name);
    }
}
