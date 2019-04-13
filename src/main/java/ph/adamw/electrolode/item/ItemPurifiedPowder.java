package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.item.core.IMetadataItem;
import ph.adamw.electrolode.item.core.ItemBase;

public class ItemPurifiedPowder extends ItemBase implements IMetadataItem {
    public ItemPurifiedPowder() {
        super(true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int getVariants() {
        return EnumMineral.values().length;
    }

    public String getTexture(int meta) {
        return EnumMineral.values()[meta].getName() + "powderpurified";
    }

    public String getItemName() {
        return "powderpurified";
    }

    @Override
    public String getTranslationKey(ItemStack i) {
        if(i.getItemDamage() <= EnumMineral.values().length-1) {
            return "item." + Electrolode.MODID + "." + EnumMineral.values()[i.getItemDamage()].getName().toLowerCase() + "powderpurified";
        }

        return null;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
        if(!isInCreativeTab(tabs)) return;
        for(int i = 0; i < EnumMineral.values().length; i++) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }
}
