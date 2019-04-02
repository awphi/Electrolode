package ph.adamw.electrolode.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
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
