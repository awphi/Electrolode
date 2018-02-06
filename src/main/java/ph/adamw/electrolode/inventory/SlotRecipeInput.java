package ph.adamw.electrolode.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.recipe.RecipeHandler;

import javax.annotation.Nullable;

public class SlotRecipeInput extends SlotItemHandler {
    private TileItemMachine machine;

    public SlotRecipeInput(IItemHandler itemHandler, int index, int xPosition, int yPosition, TileItemMachine te) {
        super(itemHandler, index, xPosition, yPosition);
        machine = te;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        ItemStack[] list = machine.getInputContents();
        list[getSlotIndex()] = stack;
        return RecipeHandler.hasRecipe(machine.getClass(), list);
    }
}
