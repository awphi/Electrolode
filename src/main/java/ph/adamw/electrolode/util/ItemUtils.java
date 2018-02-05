package ph.adamw.electrolode.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;

public class ItemUtils {
    public static boolean isStackArrayEmpty(ItemStack[] itemStacks) {
        for(ItemStack i : itemStacks) {
            if (!i.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static MachineRecipeComponent[] toMachineRecipeArray(ItemStack[] itemStacks) {
        MachineRecipeComponent[] ret = new MachineRecipeComponent[itemStacks.length];
        for(int i = 0; i < itemStacks.length; i ++) {
            ret[i] = new MachineRecipeComponent(itemStacks[i]);
        }
        return ret;
    }

    public static ItemStack[] toItemStackArray(MachineRecipeComponent[] machineRecipeComponents) {
        ItemStack[] ret = new ItemStack[machineRecipeComponents.length];
        for(int i = 0; i < machineRecipeComponents.length; i ++) {
            ret[i] = machineRecipeComponents[i].getItemStack();
        }
        return ret;
    }

    public static boolean canItemStacksStack(ItemStack a, ItemStack b) {
        if(a == ItemStack.EMPTY || b == ItemStack.EMPTY) return true;

        if(ItemHandlerHelper.canItemStacksStack(a, b)) {
            return a.getCount() + b.getCount() <= Math.min(a.getMaxStackSize(), b.getMaxStackSize());
        }

        return false;
    }

    public static boolean canItemStackArraysStack(ItemStack[] a, ItemStack[] b) {
        if(a.length != b.length) return false;

        for(int i = 0; i < a.length; i ++) {
            if(!canItemStacksStack(a[i], b[i])) return false;
        }

        return true;
    }
}
