package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.EnumPurifiableMineral;

public class ItemPowder extends ItemBase implements IMetadataItem {
    public ItemPowder() {
        super(true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int getVariants() {
        return EnumPurifiableMineral.values().length;
    }

    public String getTexture(int meta) {
        return EnumPurifiableMineral.values()[meta].getName() + "powder";
    }

    @Override
    public String getUnlocalizedName(ItemStack i) {
        if(i.getItemDamage() <= EnumPurifiableMineral.values().length-1) {
            return "item." + Electrolode.MODID + "." + EnumPurifiableMineral.values()[i.getItemDamage()].getName().toLowerCase() + "powder";
        }

        return null;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
        if(!isInCreativeTab(tabs)) return;
        for(int counter = 0; counter < EnumPurifiableMineral.values().length; counter++) {
            itemList.add(new ItemStack(this, 1, counter));
        }
    }
}
