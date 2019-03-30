package ph.adamw.electrolode.manager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.ElectrolodeItems;
import ph.adamw.electrolode.item.ItemSalt;
import ph.adamw.electrolode.item.core.ItemBase;
import ph.adamw.electrolode.item.ItemPlate;
import ph.adamw.electrolode.item.ItemPowder;
import ph.adamw.electrolode.item.ItemPurifiedPowder;
import ph.adamw.electrolode.rendering.RenderClient;

import java.util.HashMap;

public class ItemManager {
    private static final HashMap<Class<? extends ItemBase>, ItemBase> itemMap = new HashMap<>();

    public static Item get(Class<? extends ItemBase> e) {
        return itemMap.get(e);
    }

    private static String getItemName(Class<? extends ItemBase> e) {
        return itemMap.get(e).getItemName();
    }

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs("electrolode") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ElectrolodeItems.POWDER, 1, 0);
        }
    };

    private static void registerItem(final IForgeRegistry<Item> registry, Class<? extends ItemBase> item) {
        if(registry == null) {
            System.err.println("Attempted to register item at invalid time: " + item.getName());
            return;
        }
        try {
            itemMap.put(item, item.newInstance());
            registry.register(nameItem(get(item), getItemName(item)));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for(Item i : itemMap.values()) {
            RenderClient.registerItemRenderer(i);
        }
    }

    private static Item nameItem(Item i, String name) {
        return i.setTranslationKey(Electrolode.MODID + "." + name).setRegistryName(Electrolode.MODID + ":" + name);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        registerItem(registry, ItemPowder.class);
        registerItem(registry, ItemPurifiedPowder.class);
        registerItem(registry, ItemPlate.class);
        registerItem(registry, ItemSalt.class);
    }
}
