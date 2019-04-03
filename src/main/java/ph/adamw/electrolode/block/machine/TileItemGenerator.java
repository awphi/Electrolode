package ph.adamw.electrolode.block.machine;

import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.GeneratorRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;

public abstract class TileItemGenerator extends TileInventoriedGenerator {
	@Override
	protected void addPotentialFaceRoles() {
		potentialRoles.add(EnumFaceRole.INPUT_ITEM);
	}

	@Override
	public void extractInput(GeneratorRecipe recipe) {
		int c = 0;
		for(RecipeComponent i : recipe.getInput()) {
			if(i instanceof ItemStackRecipeComponent) {
				inputOnlySlotsWrapper.extractItemInternally(c, ((ItemStackRecipeComponent) i).copyOf().getCount(), false);
				c ++;
			}
		}
	}

	@Override
	public void depositOutput(GeneratorRecipe recipe) {
		int c = 0;
		for(RecipeComponent i : recipe.getOutput()) {
			if(i instanceof ItemStackRecipeComponent) {
				outputOnlySlotsWrapper.insertItemInternally(c, ((ItemStackRecipeComponent) i).copyOf(), false);
				c ++;
			}
		}
	}
}
