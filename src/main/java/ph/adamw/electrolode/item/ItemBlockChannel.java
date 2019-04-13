package ph.adamw.electrolode.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.item.core.IMetadataItem;

public class ItemBlockChannel extends ItemBlock implements IMetadataItem {
	public ItemBlockChannel(BlockBase block) {
		super(block);
	}

	@Override
	public String getTexture(int m) {
		return ChannelTier.VALUES[m].name().toLowerCase() + getBlockName();
	}

	@Override
	public int getVariants() {
		return ChannelTier.VALUES.length;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if(stack.getItemDamage() <= ChannelTier.VALUES.length) {
			return "item." + Electrolode.MODID + "." + getBlockName() + ChannelTier.VALUES[stack.getItemDamage()].name().toLowerCase();
		}

		return null;
	}

	private String getBlockName() {
		return ((BlockBase) block).getBlockName();
	}

	@Override
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> items) {
		if(!isInCreativeTab(tabs)) return;
		for(int i = 0; i < ChannelTier.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}
}
