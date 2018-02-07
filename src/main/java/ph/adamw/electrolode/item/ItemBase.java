package ph.adamw.electrolode.item;

import net.minecraft.item.Item;
import ph.adamw.electrolode.ModItems;

public abstract class ItemBase extends Item {
    public ItemBase(int maxStack, boolean addToCreativeTab) {
        if(addToCreativeTab) {
            setCreativeTab(ModItems.CREATIVETAB);
        }

        setMaxStackSize(maxStack);
    }

    public abstract String getItemName();

    public ItemBase(boolean addToCreativeTab) {
        this(64, addToCreativeTab);
    }
}
