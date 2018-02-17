package ph.adamw.electrolode.block.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.ItemUtils;

public abstract class TileItemMachine extends TileInventoriedMachine {
    protected void addPotentialFaceRoles() {
        potentialRoles.add(EnumFaceRole.INPUT_ITEM);
        potentialRoles.add(EnumFaceRole.OUTPUT_ITEM);
    }

    public boolean canProcess() {
        if (RecipeHandler.hasRecipe(this.getClass(), getInputContents())) {
            return RecipeUtils.canComponentArraysStack(getOutputContents(), getCurrentRecipeOutput());
        }
        return false;
    }

    public void processingComplete() {
        ItemStack[] output = ItemUtils.toItemStackArray(getCurrentRecipeOutput());
        ItemStack[] input = ItemUtils.toItemStackArray(getCurrentRecipeInput());

        for(int i = 0; i < getOutputSlots(); i ++) {
            outputOnlySlotsWrapper.insertItemInternally(i, output[i], false);
        }

        for(int i = 0; i < getInputSlots(); i ++) {
            inputOnlySlotsWrapper.extractItemInternally(i, input[i].getCount(), false);
        }
    }
}
