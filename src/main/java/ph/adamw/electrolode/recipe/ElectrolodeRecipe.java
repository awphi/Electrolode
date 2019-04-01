package ph.adamw.electrolode.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ElectrolodeRecipe {
	private final RecipeComponent[] input;
	private final RecipeComponent[] output;
	private final int energy;

	public ElectrolodeRecipe(RecipeComponent[] input, RecipeComponent output, int energy) {
		this(input, new RecipeComponent[] {output}, energy);
	}

	public ElectrolodeRecipe(RecipeComponent input, RecipeComponent output, int energy) {
		this(new RecipeComponent[] {input}, new RecipeComponent[] {output}, energy);
	}
}
