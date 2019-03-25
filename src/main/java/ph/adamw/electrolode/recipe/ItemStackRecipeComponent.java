package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemStackRecipeComponent extends RecipeComponent<ItemStackRecipeComponent> {
	public final ItemStack itemStack;

	public ItemStackRecipeComponent(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	public boolean compare(ItemStackRecipeComponent other) {
		final ItemStack th = itemStack;
		final ItemStack oth = other.itemStack;
		return th.getItem().equals(oth.getItem()) && th.getItemDamage() == oth.getItemDamage() && th.getMetadata() == oth.getMetadata();

	}

	@Override
	public boolean isEmpty() {
		return itemStack.isEmpty();
	}

	@Override
	public boolean canStack(ItemStackRecipeComponent other) {
		return ItemHandlerHelper.canItemStacksStack(itemStack, other.itemStack);
	}


}
