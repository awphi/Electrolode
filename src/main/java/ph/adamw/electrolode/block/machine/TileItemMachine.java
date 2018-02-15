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
    public MachineRecipeComponent[] getInputContents() {
        MachineRecipeComponent[] ret = new MachineRecipeComponent[getInputSlots()];
        for(int i = 0; i < getInputSlots(); i ++) {
            ret[i] = new MachineRecipeComponent(inputOnlySlotsWrapper.getStackInSlot(i));
        }
        return ret;
    }

    public MachineRecipeComponent[] getOutputContents() {
        MachineRecipeComponent[] ret = new MachineRecipeComponent[getOutputSlots()];
        for(int i = 0; i < getOutputSlots(); i ++) {
            ret[i] = new MachineRecipeComponent(outputOnlySlotsWrapper.getStackInSlot(i));
        }
        return ret;
    }

    protected void addPotentialFaceRoles() {
        potentialRoles.add(EnumFaceRole.INPUT_ITEM);
        potentialRoles.add(EnumFaceRole.OUTPUT_ITEM);
    }

    public void ejectOutput() {
        int count = 0;
        for(MachineRecipeComponent k : getOutputContents()) {
            ItemStack j = k.getItemStack();
            for(EnumFacing i : faceMap.keySet()) {
                if(faceMap.getRole(i) == EnumFaceRole.OUTPUT_ITEM) {
                    TileEntity neighbour = world.getTileEntity(BlockUtils.getNeighbourPos(pos, i));
                    if(neighbour == null) continue;
                    IItemHandler x = neighbour.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, i);
                    if(x == null) continue;
                    ItemStack attempt = ItemHandlerHelper.insertItem(x, j, true);
                    if(attempt != j) {
                        ItemStack removed = outputOnlySlotsWrapper.extractItem(count, j.getCount() - attempt.getCount(), false);
                        if(removed != ItemStack.EMPTY) {
                            ItemHandlerHelper.insertItem(x, j.copy(), false);
                            break;
                        }
                    }
                }
            }
            count ++;
        }
    }


    protected MachineRecipeComponent[] getCurrentRecipeOutput() {
        return RecipeHandler.getOutput(this.getClass(), getInputContents());
    }


    protected MachineRecipeComponent[] getCurrentRecipeInput() {
        return RecipeHandler.getInput(this.getClass(), getInputContents());
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
