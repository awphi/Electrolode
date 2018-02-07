package ph.adamw.electrolode.block.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.inventory.DummyItemStackHandler;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.ItemUtils;

public abstract class TileItemMachine extends TileInventoriedMachine {
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(allSlotsWrapper);
            } else if(faceMap.get(facing) == EnumFaceRole.INPUT) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputOnlySlotsWrapper);
            } else if(faceMap.get(facing) == EnumFaceRole.OUTPUT) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputOnlySlotsWrapper);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new DummyItemStackHandler());
            }
        }

        return super.getCapability(capability, facing);
    }

    public ItemStack[] getInputContents() {
        ItemStack[] ret = new ItemStack[getInputSize()];
        for(int i = 0; i < getInputSize(); i ++) {
            ret[i] = inputOnlySlotsWrapper.getStackInSlot(i);
        }
        return ret;
    }

    public ItemStack[] getOutputContents() {
        ItemStack[] ret = new ItemStack[getOutputSize()];
        for(int i = 0; i < getOutputSize(); i ++) {
            ret[i] = outputOnlySlotsWrapper.getStackInSlot(i);
        }
        return ret;
    }

    public void ejectOutput() {
        if(!faceMap.containsValue(EnumFaceRole.OUTPUT)) return;

        int count = 0;
        for(ItemStack j : getOutputContents()) {
            for(EnumFacing i : faceMap.keySet()) {
                if(faceMap.get(i) == EnumFaceRole.OUTPUT) {
                    TileEntity neighbour = world.getTileEntity(BlockUtils.getNeighbourPos(pos, i));
                    if(neighbour == null) continue;
                    IItemHandler x = neighbour.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, i);
                    if(x == null) continue;
                    ItemStack attempt = ItemHandlerHelper.insertItem(x, j, true);
                    if(attempt != j) {
                        ItemHandlerHelper.insertItem(x, j.copy(), false);
                        outputOnlySlotsWrapper.extractItem(count, j.getCount() - attempt.getCount(), false);
                        break;
                    }
                }
            }
            count ++;
        }
    }


    protected ItemStack[] getCurrentRecipeOutput() {
        MachineRecipeComponent[] x = RecipeHandler.getOutput(this.getClass(), ItemUtils.toMachineRecipeArray(getInputContents()));
        // Botched this but until it causes me real stress it remains
        if(x != null) {
            return ItemUtils.toItemStackArray(x);
        } else {
            return ItemUtils.makeItemStackArray(ItemStack.EMPTY, getOutputSize());
        }
    }


    protected ItemStack[] getCurrentRecipeInput() {
        MachineRecipeComponent[] x = RecipeHandler.getInput(this.getClass(), ItemUtils.toMachineRecipeArray(getInputContents()));
        // See above method explanation for this hack
        if(x != null) {
            return ItemUtils.toItemStackArray(x);
        } else {
            return ItemUtils.makeItemStackArray(ItemStack.EMPTY, getInputSize());
        }
    }

    public boolean canProcess() {
        if (RecipeHandler.hasRecipe(this.getClass(), getInputContents())) {
            return ItemUtils.canItemStackArraysStack(getOutputContents(), getCurrentRecipeOutput());
        }
        return false;
    }

    public void processingComplete() {
        ItemStack[] output = getCurrentRecipeOutput();
        for(int i = 0; i < getOutputSize(); i ++) {
            outputOnlySlotsWrapper.insertItemInternally(i, output[i], false);
        }

        ItemStack[] input = getCurrentRecipeInput();
        for(int i = 0; i < getInputSize(); i ++) {
            inputOnlySlotsWrapper.extractItemInternally(i, input[i].getCount(), false);
        }
    }
}
