package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import java.util.HashMap;

public class RecipeHandler {
    private static HashMap<Class<? extends TileBaseMachine>, HashMap<MachineRecipeComponent[], MachineRecipeComponent[]>> recipeMap = new HashMap<>();

    private static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] i, MachineRecipeComponent[] o) {
        recipeMap.computeIfAbsent(machine, k -> new HashMap<>());
        recipeMap.get(machine).put(i, o);
    }

    public static void addRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent i, MachineRecipeComponent o) {
        addRecipe(machine, new MachineRecipeComponent[] {i}, new MachineRecipeComponent[] {o});
    }

    /* getCurrentRecipeOutput & helpers */
    public static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input, boolean returnInput) {
        HashMap<MachineRecipeComponent[], MachineRecipeComponent[]> ioMap = recipeMap.get(machine);

        for(MachineRecipeComponent[] recipeMapEntry : ioMap.keySet()) {
            if(recipeMapEntry.length != input.length) continue;

            int validCount = 0;
            for(int i = 0; i < recipeMapEntry.length; i ++) {
                if(input[i].isValid(recipeMapEntry[i])) validCount ++;
            }

            if(validCount == recipeMapEntry.length) return returnInput ? recipeMapEntry : ioMap.get(recipeMapEntry);
        }

        return null;
    }

    public static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input) {
        return getOutput(machine, input, false);
    }

    public static MachineRecipeComponent[] getOutput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent input) {
        return getOutput(machine, new MachineRecipeComponent[] {input}, false);
    }

    /* --- */

    public static MachineRecipeComponent[] getInput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] input) {
        return getOutput(machine, input, true);
    }

    public static MachineRecipeComponent[] getInput(Class<? extends TileBaseMachine> machine, MachineRecipeComponent input) {
        return getInput(machine, new MachineRecipeComponent[] {input});
    }


    /* hasRecipe & helpers */
    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent[] component) {
        return getOutput(machine, component, false) != null;
    }

    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, MachineRecipeComponent component) {
        return hasRecipe(machine, new MachineRecipeComponent[] {component});
    }

    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, ItemStack i) {
        return hasRecipe(machine, new MachineRecipeComponent(i));
    }

    public static boolean hasRecipe(Class<? extends TileBaseMachine> machine, ItemStack[] i) {
        MachineRecipeComponent[] x = new MachineRecipeComponent[i.length];
        for(int j = 0; j < i.length; j ++) {
            x[j] = new MachineRecipeComponent(i[j]);
        }
        return hasRecipe(machine, x);
    }
}
