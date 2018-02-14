package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import java.util.HashMap;

public class RecipeHandler {
    private static HashMap<Class<? extends TileBaseMachine>, HashMap<MachineRecipeComponent[], MachineRecipeComponent[]>> recipeMap = new HashMap<>();

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] i, MachineRecipeComponent[] o) {
        recipeMap.computeIfAbsent(machine, k -> new HashMap<>());
        recipeMap.get(machine).put(i, o);
    }

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent i, MachineRecipeComponent o) {
        addRecipe(machine, new MachineRecipeComponent[] {i}, new MachineRecipeComponent[] {o});
    }

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] i, MachineRecipeComponent o) {
        addRecipe(machine, i, new MachineRecipeComponent[] {o});
    }

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent i, MachineRecipeComponent o[]) {
        addRecipe(machine, new MachineRecipeComponent[] {i}, o);
    }


    /* getCurrentRecipeOutput & helpers */
    private static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input, boolean returnInput, boolean soft) {
        HashMap<MachineRecipeComponent[], MachineRecipeComponent[]> ioMap = recipeMap.get(machine);

        if(ioMap == null) return null;

        for(MachineRecipeComponent[] recipeMapEntry : ioMap.keySet()) {
            if(recipeMapEntry.length != input.length) continue;

            int validCount = 0;
            for(int i = 0; i < recipeMapEntry.length; i ++) {
                if(soft) {
                    if (input[i].isValid(recipeMapEntry[i]) || input[i].isEmpty()) {
                        validCount++;
                    }
                } else {
                    if(input[i].isValid(recipeMapEntry[i])) {
                        validCount++;
                    }
                }
            }

            if(validCount == recipeMapEntry.length) {
                return returnInput ? recipeMapEntry : ioMap.get(recipeMapEntry);
            }
        }

        return null;
    }

    public static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input) {
        return getOutput(machine, input, false, false);
    }

    public static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent input) {
        return getOutput(machine, new MachineRecipeComponent[] {input}, false, false);
    }

    /* --- */

    public static MachineRecipeComponent[] getInput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input) {
        return getOutput(machine, input, true, false);
    }

    public static MachineRecipeComponent[] getInput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent input) {
        return getInput(machine, new MachineRecipeComponent[] {input});
    }


    /* hasRecipe & helpers */
    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] component) {
        return getOutput(machine, component, false, false) != null;
    }

    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent component) {
        return hasRecipe(machine, new MachineRecipeComponent[] {component});
    }

    public static boolean hasRecipeSoft(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] component) {
        return getOutput(machine, component, false, true) != null;
    }
}
