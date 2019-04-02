package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemStackRecipeComponent extends RecipeComponent<ItemStackRecipeComponent, ItemStack> {
	private final ItemStack itemStack;

	public ItemStackRecipeComponent(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	public boolean compare(ItemStackRecipeComponent other) {
		final ItemStack thisStack = copyOf();
		final ItemStack otherStack = other.copyOf();

		return thisStack.getItem().equals(otherStack.getItem())
				&& thisStack.getItemDamage() == otherStack.getItemDamage()
				&& thisStack.getMetadata() == otherStack.getMetadata();
	}

	@Override
	public boolean isEmpty() {
		return itemStack.isEmpty();
	}

	@Override
	public boolean canStack(ItemStackRecipeComponent other) {
		final ItemStack th = copyOf();
		final ItemStack oth = other.copyOf();
		return ItemHandlerHelper.canItemStacksStack(copyOf(), other.copyOf()) && th.getCount() + oth.getCount() <= th.getMaxStackSize();
	}

	@Override
	public ItemStack copyOf() {
		return itemStack.copy();
	}

	@Override
	protected NBTTagCompound addNBT(NBTTagCompound compound) {
		compound.setTag(COMPONENT_TAG, itemStack.serializeNBT());
		return compound;
	}

	public static ItemStackRecipeComponent fromNBT(NBTTagCompound compound) {
		return new ItemStackRecipeComponent(new ItemStack(compound.getCompoundTag(COMPONENT_TAG)));
	}
}
