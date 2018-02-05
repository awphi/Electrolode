package ph.adamw.electrolode.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import ph.adamw.electrolode.block.machines.TileBaseMachine;
import ph.adamw.electrolode.recipe.RecipeHandler;

public class InputRecipeItemStackHandler extends InputItemStackHandler {
    private Class<? extends TileBaseMachine> machineType;

    public InputRecipeItemStackHandler(ItemStackHandler e, Class<? extends TileBaseMachine> te) {
        super(e);
        machineType = te;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if(RecipeHandler.hasRecipe(machineType, stack)) {
            return internalSlot.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }
    }
}
