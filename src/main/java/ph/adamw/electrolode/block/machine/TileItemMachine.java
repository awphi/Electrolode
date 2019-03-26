package ph.adamw.electrolode.block.machine;

import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.MachineRecipe;
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
            return RecipeUtils.canComponentArraysStack(getCurrentRecipe().output, getOutputContents());
        }
        return false;
    }

    public void processingComplete() {
        final MachineRecipe recipe = getCurrentRecipe();

        int c = 0;
        for(RecipeComponent i : recipe.output) {
            outputOnlySlotsWrapper.insertItemInternally(c, ((ItemStackRecipeComponent) i).itemStack, false);
            c ++;
        }

        c = 0;
        for(RecipeComponent i : recipe.input) {
            inputOnlySlotsWrapper.extractItemInternally(c, ((ItemStackRecipeComponent) i).itemStack.getCount(), false);
            c ++;
        }
    }
}
