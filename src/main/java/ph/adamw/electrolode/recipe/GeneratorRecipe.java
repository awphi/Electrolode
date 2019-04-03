package ph.adamw.electrolode.recipe;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class GeneratorRecipe extends MachineRecipe {
	@Getter
	private int time;

	public GeneratorRecipe(RecipeComponent[] input, RecipeComponent[] output, int energy, int time) {
		super(input, output, energy);
		this.time = time;
	}

	public GeneratorRecipe(RecipeComponent[] input, int energy, int time) {
		this(input, new RecipeComponent[0], energy, time);
	}

	public GeneratorRecipe(RecipeComponent input, int energy, int time) {
		this(new RecipeComponent[] {input}, energy, time);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("time", time);

		return compound;
	}

	public static GeneratorRecipe fromNBT(NBTTagCompound compound) {
		final MachineRecipe recipe = MachineRecipe.fromNBT(compound);
		return new GeneratorRecipe(recipe.getInput(), recipe.getOutput(), recipe.getEnergy(), compound.getInteger("time"));
	}
}
