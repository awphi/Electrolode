package ph.adamw.electrolode.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

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

	public NBTTagCompound toNBT() {
		final NBTTagCompound compound = new NBTTagCompound();

		for(RecipeComponent i : input) {
			compound.setTag("input" + i, i.asNBT());
		}

		for(RecipeComponent i : output) {
			compound.setTag("output" + i, i.asNBT());
		}

		compound.setInteger("energy", energy);

		return compound;
	}

	public static ElectrolodeRecipe fromNBT(NBTTagCompound compound) {
		final List<RecipeComponent> inputs = new ArrayList<>();
		final List<RecipeComponent> outputs = new ArrayList<>();
		final int energy = compound.getInteger("energy");

		int i = 0;

		while(compound.hasKey("input" + i)) {
			inputs.add(RecipeComponent.fromNBT(compound.getCompoundTag("input" + i)));
			i ++;
		}

		i = 0;
		while(compound.hasKey("output" + i)) {
			outputs.add(RecipeComponent.fromNBT(compound.getCompoundTag("output" + i)));
			i ++;
		}

		return new ElectrolodeRecipe(inputs.toArray(new RecipeComponent[0]), outputs.toArray(new RecipeComponent[0]), energy);
	}
}
