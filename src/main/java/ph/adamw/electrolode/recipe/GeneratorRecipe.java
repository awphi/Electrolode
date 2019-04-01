package ph.adamw.electrolode.recipe;

public class GeneratorRecipe extends ElectrolodeRecipe {
	public GeneratorRecipe(RecipeComponent[] input, int energy) {
		super(input, new RecipeComponent[0], energy);
	}

	public GeneratorRecipe(RecipeComponent input, int energy) {
		super(new RecipeComponent[] {input}, new RecipeComponent[0], energy);
	}
}
