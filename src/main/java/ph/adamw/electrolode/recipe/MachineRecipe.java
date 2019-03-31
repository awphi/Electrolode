package ph.adamw.electrolode.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MachineRecipe {
	private final RecipeComponent[] input;
	private final RecipeComponent[] output;
	private final int energy;

	public MachineRecipe(RecipeComponent[] input, RecipeComponent output, int energy) {
		this(input, new RecipeComponent[] {output}, energy);
	}

	public MachineRecipe(RecipeComponent input, RecipeComponent output, int energy) {
		this(new RecipeComponent[] {input}, new RecipeComponent[] {output}, energy);
	}
}
