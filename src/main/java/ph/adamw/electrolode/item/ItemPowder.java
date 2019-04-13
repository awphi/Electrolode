package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.item.core.IMetadataItem;
import ph.adamw.electrolode.item.core.ItemBase;

public class ItemPowder extends ItemBase implements IMetadataItem {
    public ItemPowder() {
        super(true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int getVariants() {
        return EnumMineral.values().length;
    }

    public String getTexture(int meta) {
        return EnumMineral.values()[meta].getName() + "powder";
    }

    public String getItemName() {
        return "powder";
    }

    @Override
    public String getTranslationKey(ItemStack i) {
        if(i.getItemDamage() <= EnumMineral.values().length-1) {
            return "item." + Electrolode.MODID + "." + EnumMineral.values()[i.getItemDamage()].getName().toLowerCase() + "powder";
        }

        return null;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
        if(!isInCreativeTab(tabs)) return;
        for(int i = 0; i < EnumMineral.values().length; i ++) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }
}
