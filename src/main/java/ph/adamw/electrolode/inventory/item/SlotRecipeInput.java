package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlotRecipeInput extends SlotItemHandler {
    private TileInventoriedMachine machine;

    public SlotRecipeInput(IItemHandler itemHandler, int index, int xPosition, int yPosition, TileInventoriedMachine te) {
        super(itemHandler, index, xPosition, yPosition);
        machine = te;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        final List<RecipeComponent> components = new ArrayList<>(Arrays.asList(machine.getInputContents()));
        components.set(getSlotIndex(), new ItemStackRecipeComponent(stack));

        return RecipeHandler.hasRecipeSoft(machine.getClass(), components.toArray(new RecipeComponent[0]));
    }
}
