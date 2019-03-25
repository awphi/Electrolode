package ph.adamw.electrolode.recipe;

public class MachineRecipe {
	public final RecipeComponent[] input;
	public final RecipeComponent[] output;
	public final int energy;

	public MachineRecipe(RecipeComponent[] input, RecipeComponent[] output, int energy) {
		this.input = input;
		this.output = output;
		this.energy = energy;
	}

	public MachineRecipe(RecipeComponent[] input, RecipeComponent output, int energy) {
		this(input, new RecipeComponent[] {output}, energy);
	}

	public MachineRecipe(RecipeComponent input, RecipeComponent output, int energy) {
		this(new RecipeComponent[] {input}, new RecipeComponent[] {output}, energy);
	}
}
