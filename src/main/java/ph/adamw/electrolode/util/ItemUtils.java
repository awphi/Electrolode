package ph.adamw.electrolode.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;

public class ItemUtils {
    public static ItemStackRecipeComponent[] makeItemStackRecipeArray(ItemStack itemStack, int dupes) {
        ItemStackRecipeComponent[] ret = new ItemStackRecipeComponent[dupes];
        for(int i = 0; i < dupes; i ++) {
            ret[i] = new ItemStackRecipeComponent(new ItemStack(itemStack.getItem(), itemStack.getCount(), itemStack.getMetadata()));
        }
        return ret;
    }

    /**
     *
     * @param i - ItemStackHandler that contains the base of the recipe
     * @param stack - ItemStack that will be simulated into the existing recipe
     * @param slot - Slot at which @stack will be inserted
     * @return - Returns ItemStack array of the edited ItemStackHandler contents
     */
    public static ItemStackRecipeComponent[] makeProposedItemRecipe(ItemStackHandler i, ItemStack stack, int slot) {
        ItemStackRecipeComponent[] recipe = new ItemStackRecipeComponent[i.getSlots()];
        for(int j = 0; j < i.getSlots(); j ++) {
            recipe[j] = new ItemStackRecipeComponent(i.getStackInSlot(j));
        }

        recipe[slot] = new ItemStackRecipeComponent(stack);
        return recipe;
    }
}
