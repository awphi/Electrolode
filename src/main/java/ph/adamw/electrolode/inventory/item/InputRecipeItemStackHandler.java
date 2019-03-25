package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.util.ItemUtils;

public class InputRecipeItemStackHandler extends InputItemStackHandler {
    private Class<? extends TileBaseMachine> machineType;

    public InputRecipeItemStackHandler(ItemStackHandler e, Class<? extends TileBaseMachine> te) {
        super(e);
        machineType = te;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStackRecipeComponent[] recipe = ItemUtils.makeProposedItemRecipe(this, stack, slot);
        if(RecipeHandler.hasRecipeSoft(machineType, recipe) && canAccess(slot)) {
            return internalSlot.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }
    }
}
