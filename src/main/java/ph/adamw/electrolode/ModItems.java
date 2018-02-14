package ph.adamw.electrolode;

import net.minecraft.item.Item;
import ph.adamw.electrolode.item.ItemBase;
import ph.adamw.electrolode.item.ItemPlate;
import ph.adamw.electrolode.item.ItemPowder;
import ph.adamw.electrolode.item.ItemPurifiedPowder;
import ph.adamw.electrolode.manager.ItemManager;

@SuppressWarnings("WeakerAccess")

/*
 * Simple little wrapper class to make referencing items neater and easier.
 */
public class ModItems {
    public static final Item POWDER = getRegisteredItem(ItemPowder.class);
    public static final Item POWDER_PURIFIED = getRegisteredItem(ItemPurifiedPowder.class);
    public static final Item PLATE = getRegisteredItem(ItemPlate.class);

    private static Item getRegisteredItem(Class<? extends ItemBase> x) {
        return ItemManager.get(x);
    }
}
