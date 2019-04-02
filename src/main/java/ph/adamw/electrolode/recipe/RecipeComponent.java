package ph.adamw.electrolode.recipe;

import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.InvocationTargetException;

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

	public NBTTagCompound toNBT() {
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setString(CLASS_TAG, getClass().getName());
		return addNBT(compound);
	}

	public static RecipeComponent fromNBT(NBTTagCompound compound) {
		try {
			return (RecipeComponent) Class.forName(compound.getString(CLASS_TAG))
					.getMethod("fromNBT", NBTTagCompound.class)
					.invoke(null, compound);

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
