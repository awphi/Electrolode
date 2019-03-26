package ph.adamw.electrolode.recipe;

public class MachineRecipe {
	private final RecipeComponent[] input;
	private final RecipeComponent[] output;
	private final int energy;

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

	public RecipeComponent[] getInput() {
		return input;
	}

	public RecipeComponent[] getOutput() {
		return output;
	}

	public int getEnergy() {
		return energy;
	}
}
