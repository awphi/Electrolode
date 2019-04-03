package ph.adamw.electrolode.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;

import javax.crypto.Mac;
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

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(RecipeComponent i : getInput()) {
			compound.setTag("input" + i, i.writeToNBT(compound));
		}

		for(RecipeComponent i : getOutput()) {
			compound.setTag("output" + i, i.writeToNBT(compound));
		}

		compound.setInteger("energy", energy);

		return compound;
	}

	public static MachineRecipe fromNBT(NBTTagCompound compound) {
		if(compound == null) {
			return null;
		}

		final List<RecipeComponent> inputs = new ArrayList<>();
		final List<RecipeComponent> outputs = new ArrayList<>();
		final int energy = compound.getInteger("energy");

		int i = 0;

		while(compound.hasKey("input" + i)) {
			inputs.add(RecipeComponent.fromNBT(compound.getCompoundTag("input" + i)));
			i ++;
		}

		while(compound.hasKey("output" + i)) {
			inputs.add(RecipeComponent.fromNBT(compound.getCompoundTag("output" + i)));
			i ++;
		}

		return new MachineRecipe(inputs.toArray(new RecipeComponent[0]), outputs.toArray(new RecipeComponent[0]), energy);
	}
}
