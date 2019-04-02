package ph.adamw.electrolode.recipe;

import net.minecraft.nbt.NBTTagCompound;

public abstract class RecipeComponent<T extends RecipeComponent, E> {
	public static final String COMPONENT_TAG = "component";

	public abstract boolean compare(T other);

	public abstract boolean isEmpty();

	public boolean isSameType(RecipeComponent other) {
		return other.getClass().equals(getClass());
	}

	public abstract boolean canStack(T other);

	public abstract E copyOf();

	protected abstract NBTTagCompound addNBT(NBTTagCompound compound);

	public NBTTagCompound asNBT() {
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setString("class", getClass().getName());
		return addNBT(compound);
	}

	public static RecipeComponent fromNBT(NBTTagCompound compound) {
		//TODO resolve a recipe component from the nbt tag compound - use the class name from the compound
		// to access an expected static method w/ the compound in the RecipeComponent implementation that will return an
		// instance of itself built w/ the component.
	}
}
