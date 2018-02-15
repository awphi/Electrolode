package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.block.machine.TileTankedMachine;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.util.FluidUtils;
import ph.adamw.electrolode.util.ItemUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlotRecipeInput extends SlotItemHandler {
    private TileItemMachine machine;

    public SlotRecipeInput(IItemHandler itemHandler, int index, int xPosition, int yPosition, TileItemMachine te) {
        super(itemHandler, index, xPosition, yPosition);
        machine = te;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        List<MachineRecipeComponent> components = new ArrayList<>();
        components.addAll(Arrays.asList(machine.getInputContents()));
        components.set(getSlotIndex(), new MachineRecipeComponent(stack));

        return RecipeHandler.hasRecipeSoft(machine.getClass(), components.toArray(new MachineRecipeComponent[components.size()]));
    }
}
