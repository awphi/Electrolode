package ph.adamw.electrolode.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    /**
     *
     * @param itemStacks - Array of ItemStacks to convert to an array of MachineRecipeComponent
     * @return - List of newly instantiated MachineRecipeComponents wrapping the passed ItemStacks
     */
    public static MachineRecipeComponent[] toMachineRecipeArray(ItemStack[] itemStacks) {
        MachineRecipeComponent[] ret = new MachineRecipeComponent[itemStacks.length];
        for(int i = 0; i < itemStacks.length; i ++) {
            ret[i] = new MachineRecipeComponent(itemStacks[i]);
        }
        return ret;
    }

    /**
     *
     * @param machineRecipeComponents - Array to convert to ItemStack array
     *                                - This will remove any non-ItemStack ingredients from the array
     * @return - Array of the ItemStacks within the given recipe
     */
    public static ItemStack[] toItemStackArray(MachineRecipeComponent[] machineRecipeComponents) {

        List<ItemStack> ret = new ArrayList<>();
        for (MachineRecipeComponent machineRecipeComponent : machineRecipeComponents) {
            if (machineRecipeComponent.getType() == MachineRecipeComponent.Type.ITEM) {
                ret.add(machineRecipeComponent.getItemStack());
            }
        }
        return ret.toArray(new ItemStack[machineRecipeComponents.length]);
    }

    /**
     *
     * @param a - ItemStack array #1
     * @param b - ItemStack array #2
     * @return - Returns true or false as these arrays can stack in their current order
     */
    public static boolean canItemStackArraysStack(ItemStack[] a, ItemStack[] b) {
        if(a.length != b.length) return false;

        for(int i = 0; i < a.length; i ++) {
            if(!ItemHandlerHelper.canItemStacksStack(a[i], b[i])) return false;
        }

        return true;
    }

    /**
     *
     * @param i - ItemStack to be duplicated in returning array
     * @param dupes - Length of the returning array
     * @return - Returns ItemStack array of length @dupes containing only @i
     */
    public static ItemStack[] makeItemStackArray(ItemStack i, int dupes) {
        ItemStack[] ing = new ItemStack[dupes];
        for(int j = 0; j < ing.length; j ++) {
            ing[j] = i;
        }
        return ing;
    }

    /**
     *
     * @param i - ItemStackHandler that contains the base of the recipe
     * @param stack - ItemStack that will be simulated into the existing recipe
     * @param slot - Slot at which @stack will be inserted
     * @return - Returns ItemStack array of the edited ItemStackHandler contents
     */
    public static ItemStack[] makeSimulateRecipe(ItemStackHandler i, ItemStack stack, int slot) {
        ItemStack[] recipe = new ItemStack[i.getSlots()];
        for(int j = 0; j < i.getSlots(); j ++) {
            recipe[j] = i.getStackInSlot(j);
        }
        recipe[slot] = stack;
        return recipe;
    }
}
