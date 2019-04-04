package ph.adamw.electrolode.tile.machine.core;

import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;

public abstract class TileItemMachine extends TileInventoriedMachine {
    protected void addPotentialFaceRoles() {
        potentialRoles.add(EnumFaceRole.INPUT_ITEM);
        potentialRoles.add(EnumFaceRole.OUTPUT_ITEM);
    }

    public void processingComplete() {
        final MachineRecipe recipe = getNextRecipe();

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
