package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.item.core.EnumPlate;
import ph.adamw.electrolode.item.core.IMetadataItem;
import ph.adamw.electrolode.item.core.ItemBase;

public class ItemPlate extends ItemBase implements IMetadataItem {
    public ItemPlate() {
        super(true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public int getVariants() {
        return EnumPlate.values().length;
    }

    public String getTexture(int meta) {
        return EnumPlate.values()[meta].getName() + "plate";
    }

    public String getItemName() {
        return "plate";
    }

    @Override
    public String getUnlocalizedName(ItemStack i) {
        if(i.getItemDamage() <= EnumPlate.values().length-1) {
            return "item." + Electrolode.MODID + "." + EnumPlate.values()[i.getItemDamage()].getName().toLowerCase() + "plate";
        }

        return null;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
        if(!isInCreativeTab(tabs)) return;
        for(int counter = 0; counter < EnumPlate.values().length; counter++) {
            itemList.add(new ItemStack(this, 1, counter));
        }
    }
}
