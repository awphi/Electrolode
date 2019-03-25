package ph.adamw.electrolode.block.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.inventory.item.*;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.EnergyUtils;

public abstract class TileInventoriedMachine extends TileBaseMachine {
    InputItemStackHandler inputOnlySlotsWrapper = new InputRecipeItemStackHandler(new ItemStackHandler(getInputSlots()), this.getClass());
    OutputItemStackHandler outputOnlySlotsWrapper = new OutputItemStackHandler(new ItemStackHandler(getOutputSlots()));
    private ItemStackHandler chargeSlotWrapper = new ItemStackHandler(1);
    private CombinedInvWrapper allSlotsWrapper = new CombinedInvWrapper(inputOnlySlotsWrapper.internalSlot, outputOnlySlotsWrapper, chargeSlotWrapper);

    public void ejectOutput() {
        int count = 0;
        for (RecipeComponent k : getOutputContents()) {
            if(!(k instanceof ItemStackRecipeComponent)) {
                continue;
            }

            final ItemStack itemStack = ((ItemStackRecipeComponent) k).itemStack;

            if (itemStack == ItemStack.EMPTY) {
                continue;
            }

            for (EnumFacing i : faceMap.keySet()) {
                if (faceMap.getRole(i) == EnumFaceRole.OUTPUT_ITEM) {
                    TileEntity neighbour = world.getTileEntity(BlockUtils.getNeighbourPos(pos, i));
                    if (neighbour == null) continue;
                    IItemHandler x = neighbour.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, i);
                    if (x == null) continue;
                    ItemStack attempt = ItemHandlerHelper.insertItem(x, itemStack, true);
                    if (attempt != itemStack) {
                        ItemStack removed = outputOnlySlotsWrapper.extractItem(count, itemStack.getCount() - attempt.getCount(), false);
                        if (removed != ItemStack.EMPTY) {
                            ItemHandlerHelper.insertItem(x, itemStack.copy(), false);
                            break;
                        }
                    }
                }
            }
            count++;
        }
    }


    public abstract int getInputSlots();
    public abstract int getOutputSlots();

    public int getCombinedSlots() {
        return getInputSlots() + getOutputSlots() + 1;
    }

    @Override
    public void resetProcess() {
        if(processedTime > 0 && autoEject) {
            ejectOutput();
        }
        super.resetProcess();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            ItemStackHandlerBase value;
            if(facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(allSlotsWrapper);
            } else if(faceMap.getRole(facing) == EnumFaceRole.INPUT_ITEM) {
                value = inputOnlySlotsWrapper;
            } else if(faceMap.getRole(facing) == EnumFaceRole.OUTPUT_ITEM) {
                value = outputOnlySlotsWrapper;
            } else {
                value = new DummyItemStackHandler();
            }

            value.setAllowedSlot(faceMap.getContainerIndex(facing));
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(value);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            EnergyUtils.discharge(chargeSlotWrapper.getStackInSlot(0), this);
        }
        super.tick();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inputItems", inputOnlySlotsWrapper.serializeNBT());
        compound.setTag("outputItems", outputOnlySlotsWrapper.serializeNBT());
        compound.setTag("chargeItem", chargeSlotWrapper.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inputOnlySlotsWrapper.deserializeNBT((NBTTagCompound) compound.getTag("inputItems"));
        outputOnlySlotsWrapper.deserializeNBT((NBTTagCompound) compound.getTag("outputItems"));
        chargeSlotWrapper.deserializeNBT((NBTTagCompound) compound.getTag("chargeItem"));
    }

    public RecipeComponent[] getInputContents() {
        ItemStackRecipeComponent[] ret = new ItemStackRecipeComponent[getInputSlots()];
        for(int i = 0; i < getInputSlots(); i ++) {
            ret[i] = new ItemStackRecipeComponent(inputOnlySlotsWrapper.getStackInSlot(i));
        }
        return ret;
    }

    public RecipeComponent[] getOutputContents() {
        ItemStackRecipeComponent[] ret = new ItemStackRecipeComponent[getOutputSlots()];
        for(int i = 0; i < getOutputSlots(); i ++) {
            ret[i] = new ItemStackRecipeComponent(outputOnlySlotsWrapper.getStackInSlot(i));
        }
        return ret;
    }
}
