package ph.adamw.electrolode.items;

import net.minecraft.item.Item;
import ph.adamw.electrolode.ModItems;

public class ItemBase extends Item {
    public ItemBase(int maxStack, boolean addToCreativeTab) {
        if(addToCreativeTab) {
            setCreativeTab(ModItems.CREATIVETAB);
        }

        setMaxStackSize(maxStack);
    }

    public ItemBase(boolean addToCreativeTab) {
        this(64, addToCreativeTab);
    }
}
