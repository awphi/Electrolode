package ph.adamw.electrolode.block.machine;

import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.ElectrolodeRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;

public abstract class TileItemMachine extends TileInventoriedMachine {
    protected void addPotentialFaceRoles() {
        potentialRoles.add(EnumFaceRole.INPUT_ITEM);
        potentialRoles.add(EnumFaceRole.OUTPUT_ITEM);
    }

    public boolean canProcess() {
        if (RecipeHandler.hasRecipe(this.getClass(), getInputContents())) {
            return RecipeUtils.canComponentArraysStack(getCurrentRecipe().getOutput(), getOutputContents());
        }

        return false;
    }

    public void processingComplete() {
        final ElectrolodeRecipe recipe = getCurrentRecipe();

        int c = 0;
        for(RecipeComponent i : recipe.getOutput()) {
            if(i instanceof ItemStackRecipeComponent) {
                outputOnlySlotsWrapper.insertItemInternally(c, ((ItemStackRecipeComponent) i).copyOf(), false);
            }
            c ++;
        }

        c = 0;
        for(RecipeComponent i : recipe.getInput()) {
            if(i instanceof ItemStackRecipeComponent) {
                inputOnlySlotsWrapper.extractItemInternally(c, ((ItemStackRecipeComponent) i).copyOf().getCount(), false);
            }
            c ++;
        }
    }
}
