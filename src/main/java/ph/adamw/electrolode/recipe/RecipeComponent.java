package ph.adamw.electrolode.recipe;

public abstract class RecipeComponent<T extends RecipeComponent> {
	public abstract boolean compare(T other);

	public abstract boolean isEmpty();

	public boolean isSameType(RecipeComponent other) {
		return other.getClass().equals(getClass());
	}

	public abstract boolean canStack(T other);
}
