package ph.adamw.electrolode.recipe;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import org.lwjgl.Sys;
import ph.adamw.electrolode.ModItems;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RecipeHandler {
    private static HashMap<Class<? extends TileBaseMachine>, List<MachineRecipe>> recipeMap = new HashMap<>();

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipe recipe) {
        recipeMap.computeIfAbsent(machine, k -> new LinkedList<>());
        recipeMap.get(machine).add(recipe);
    }

    private static MachineRecipe findRecipe(Class<? extends TileBaseMachine> machine, RecipeComponent[] input, boolean soft) {
        final List<MachineRecipe> recipes = recipeMap.get(machine);

        if(recipes == null || recipes.size() == 0) {
            return null;
        }

        //TODO look into why this is turning up null
        for(MachineRecipe recipe : recipes) {
            if(recipe.input.length != input.length) {
                continue;
            }

            int validCount = 0;

            for(int i = 0; i < recipe.input.length; i ++) {
                if(!input[i].isSameType(recipe.input[i])) {
                    break;
                }

                if (soft && (input[i].compare(recipe.input[i]) || input[i].isEmpty())) {
                    validCount++;
                } else if (!soft && input[i].compare(recipe.input[i])) {
                    validCount++;
                }
            }

            if(validCount == recipe.input.length) {
                return recipe;
            }
        }

        return null;
    }

    public static MachineRecipe getRecipe(Class<? extends TileBaseMachine> machine, RecipeComponent[] input) {
        return findRecipe(machine, input, false);
    }

    /* hasRecipe & helpers */
    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, RecipeComponent[] component) {
        return findRecipe(machine, component,false) != null;
    }

    public static boolean hasRecipeSoft(Class<? extends TileBaseMachine> machine, RecipeComponent[] component) {
        return findRecipe(machine, component,true) != null;
    }
}
