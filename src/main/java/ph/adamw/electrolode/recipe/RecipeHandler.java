package ph.adamw.electrolode.recipe;

import ph.adamw.electrolode.tile.machine.core.TileMachine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RecipeHandler {
    private static HashMap<Class<? extends TileMachine>, List<MachineRecipe>> recipeMap = new HashMap<>();

    public static void addRecipe(Class<? extends TileMachine> machine, MachineRecipe recipe) {
        recipeMap.computeIfAbsent(machine, k -> new LinkedList<>());
        recipeMap.get(machine).add(recipe);
    }

    @SuppressWarnings("unchecked")
    private static MachineRecipe findRecipe(Class<? extends TileMachine> machine, RecipeComponent[] userInput, boolean soft) {
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

    public static MachineRecipe findRecipe(Class<? extends TileMachine> machine, RecipeComponent[] input) {
        return findRecipe(machine, input, false);
    }

    public static boolean hasRecipe(Class<? extends TileMachine> machine, RecipeComponent[] component) {
        return findRecipe(machine, component) != null;
    }

    public static boolean hasRecipeSoft(Class<? extends TileMachine> machine, RecipeComponent[] component) {
        return findRecipe(machine, component,true) != null;
    }
}
