package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.EnumPurifiableMineral;

public class ItemPurifiedPowder extends ItemBase implements IMetadataItem {
    public ItemPurifiedPowder() {
        super(true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int getVariants() {
        return EnumPurifiableMineral.values().length;
    }

    public String getTexture(int meta) {
        return EnumPurifiableMineral.values()[meta].getName() + "purifiedpowder";
    }

    public String getItemName() {
        return "purifiedpowder";
    }

    @Override
    public String getUnlocalizedName(ItemStack i) {
        if(i.getItemDamage() <= EnumPurifiableMineral.values().length-1) {
            return "item." + Electrolode.MODID + "." + EnumPurifiableMineral.values()[i.getItemDamage()].getName().toLowerCase() + "purifiedpowder";
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
