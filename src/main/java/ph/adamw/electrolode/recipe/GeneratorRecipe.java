package ph.adamw.electrolode.recipe;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class GeneratorRecipe extends MachineRecipe {
	@Getter
	private int time;

	public GeneratorRecipe(RecipeComponent[] input, int energy, int time) {
		super(input, new RecipeComponent[0], energy);
		this.time = time;
	}

	public GeneratorRecipe(RecipeComponent input, int energy, int time) {
		this(new RecipeComponent[] {input}, energy, time);
	}

	public NBTTagCompound toNBT() {
		final NBTTagCompound compound = new NBTTagCompound();

		for(RecipeComponent i : getInput()) {
			compound.setTag("input" + i, i.toNBT());
		}

		compound.setInteger("energy", getEnergy());

		return compound;
	}

	public static GeneratorRecipe fromNBT(NBTTagCompound compound) {
		final List<RecipeComponent> inputs = new ArrayList<>();
		final int energy = compound.getInteger("energy");
		final int time = compound.getInteger("time");

		int i = 0;

		while(compound.hasKey("input" + i)) {
			inputs.add(RecipeComponent.fromNBT(compound.getCompoundTag("input" + i)));
			i ++;
		}

		return new GeneratorRecipe(inputs.toArray(new RecipeComponent[0]), energy, time);
	}
}
