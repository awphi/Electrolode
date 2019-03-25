package ph.adamw.electrolode.recipe;

import net.minecraftforge.fluids.FluidStack;

public class FluidStackRecipeComponent extends RecipeComponent<FluidStackRecipeComponent> {
	public final FluidStack fluidStack;

	public FluidStackRecipeComponent(FluidStack fluidStack) {
		this.fluidStack = fluidStack;
	}

	@Override
	public boolean compare(FluidStackRecipeComponent other) {
		FluidStack th = this.fluidStack;
		FluidStack oth = other.fluidStack;
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
		return fluidStack.getFluid().equals(other.fluidStack.getFluid()) && FluidStack.areFluidStackTagsEqual(fluidStack, other.fluidStack);
	}
}
