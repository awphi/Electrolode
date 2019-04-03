package ph.adamw.electrolode.recipe;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackRecipeComponent extends RecipeComponent<FluidStackRecipeComponent, FluidStack> {
	private final FluidStack fluidStack;

	public FluidStackRecipeComponent(FluidStack fluidStack) {
		this.fluidStack = fluidStack;
	}

	@Override
	public boolean compare(FluidStackRecipeComponent other) {
		final FluidStack th = this.copyOf();
		final FluidStack oth = other.copyOf();

		if (th == null) {
			return false;
		} else {
			return th.getFluid() == oth.getFluid() && th.amount >= oth.amount && th.tag == oth.tag;
		}
	}

	@Override
	public boolean isEmpty() {
		return fluidStack == null || fluidStack.amount == 0;
	}

	@Override
	public boolean canStack(FluidStackRecipeComponent other) {
		return fluidStack.getFluid().equals(other.copyOf().getFluid()) && FluidStack.areFluidStackTagsEqual(copyOf(), other.copyOf());
	}

	@Override
	public FluidStack copyOf() {
		return fluidStack == null ? null : fluidStack.copy();
	}

	@Override
	protected NBTTagCompound addNBT(NBTTagCompound compound) {
		compound.setTag(COMPONENT_TAG, fluidStack.writeToNBT(new NBTTagCompound()));
		return compound;
	}
}
