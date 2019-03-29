package ph.adamw.electrolode.recipe;

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

    private static MachineRecipe findRecipe(Class<? extends TileBaseMachine> machine, RecipeComponent[] userInput, boolean soft) {
        final List<MachineRecipe> recipes = recipeMap.get(machine);

        if(recipes == null || recipes.size() == 0) {
            return null;
        }

        for(MachineRecipe recipe : recipes) {
            final RecipeComponent[] desiredInput = recipe.getInput();

            if(desiredInput.length != userInput.length) {
                continue;
            }

            int validCount = 0;

            for(int i = 0; i < desiredInput.length; i ++) {
                if(!userInput[i].isSameType(desiredInput[i])) {
                    break;
                }

                if (soft && (userInput[i].compare(desiredInput[i]) || userInput[i].isEmpty())) {
                    validCount++;
                } else if (!soft && userInput[i].compare(desiredInput[i])) {
                    validCount++;
                }
            }

            if(validCount == desiredInput.length) {
                return recipe;
            }
        }

        return null;
    }

    public static MachineRecipe findRecipe(Class<? extends TileBaseMachine> machine, RecipeComponent[] input) {
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
