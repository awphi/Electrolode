package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public abstract class RecipeComponent<T extends RecipeComponent, E> {
	public static final String COMPONENT_TAG = "component";
	public static final String CLASS_TAG = "class";

	public abstract boolean compare(T other);

	public abstract boolean isEmpty();

	public boolean isSameType(RecipeComponent other) {
		return other.getClass().equals(getClass());
	}

	public abstract boolean canStack(T other);

	public abstract E copyOf();

	protected abstract NBTTagCompound addNBT(NBTTagCompound compound);

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setString(CLASS_TAG, getClass().getSimpleName());
		return addNBT(compound);
	}

	public static RecipeComponent fromNBT(NBTTagCompound compound) {
		switch(compound.getString(CLASS_TAG)) {
			case "ItemStackRecipeComponent": return new ItemStackRecipeComponent(new ItemStack(compound.getCompoundTag(COMPONENT_TAG)));
			case "FluidStackRecipeComponent": return new FluidStackRecipeComponent(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(COMPONENT_TAG)));
		}

		return null;
	}
}
