package ph.adamw.electrolode.block.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.inventory.fluid.FluidTankBase;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;
import ph.adamw.electrolode.util.FluidUtils;
import ph.adamw.electrolode.util.ItemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TileTankedMachine extends TileItemMachine {
    protected List<FluidTankBase> inputTanks = new ArrayList<>();
    protected List<FluidTankBase> outputTanks = new ArrayList<>();

    public TileTankedMachine() {
        addInputTanks();
        addOutputTanks();

        for(FluidTank i : inputTanks) {
            i.setCanDrain(false);
            i.setTileEntity(this);
        }

        for(FluidTank i : outputTanks) {
            i.setCanFill(false);
            i.setTileEntity(this);
        }

        markForUpdate();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            List<FluidTankBase> value = null;
            if(faceMap.getRole(facing) == EnumFaceRole.INPUT_FLUID) {
                value = inputTanks;
            } else if(faceMap.getRole(facing) == EnumFaceRole.OUTPUT_ITEM) {
                value = outputTanks;
            }

            if(value != null) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(value.get(faceMap.getContainerIndex(facing)));
            }
        }

        return super.getCapability(capability, facing);
    }

    protected abstract void addInputTanks();

    protected abstract void addOutputTanks();

    @Override
    protected void addPotentialFaceRoles() {
        super.addPotentialFaceRoles();
        potentialRoles.add(EnumFaceRole.INPUT_FLUID);
        potentialRoles.add(EnumFaceRole.OUTPUT_FLUID);
    }

    public FluidTank getFluidTank(int i, EnumFaceRole e) {
        if(e == EnumFaceRole.INPUT_FLUID) {
            return inputTanks.get(i);
        } else if(e == EnumFaceRole.OUTPUT_FLUID) {
            return outputTanks.get(i);
        }

        return null;
    }

    public int getInputTanks() {
        return inputTanks.size();
    }

    public int getOutputTanks() {
        return outputTanks.size();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        for(int i = 0; i < inputTanks.size(); i ++) {
            compound.setTag("inputTank" + i, inputTanks.get(i).writeToNBT(new NBTTagCompound()));
        }

        for(int i = 0; i < outputTanks.size(); i ++) {
            compound.setTag("outputTank" + i, outputTanks.get(i).writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for(int i = 0; i < inputTanks.size(); i ++) {
            inputTanks.get(i).readFromNBT(compound.getCompoundTag("inputTank" + i));
        }

        for(int i = 0; i < outputTanks.size(); i ++) {
            outputTanks.get(i).readFromNBT(compound.getCompoundTag("outputTank" + i));
        }
    }

    protected MachineRecipeComponent[] getInputContents() {
        MachineRecipeComponent[] recipe = new MachineRecipeComponent[getInputSlots() + getInputTanks()];
        ItemStack[] inSlot = getInputSlotContents();
        for(int i = 0; i < getInputSlots(); i ++) {
            recipe[i] = new MachineRecipeComponent(inSlot[i]);
        }
        FluidStack[] inTank = getInputTankContents();
        for(int i = 0; i < getInputTanks(); i ++) {
            System.out.println(inTank[i]);
            recipe[i + getInputSlots()] = new MachineRecipeComponent(inTank[i]);
        }
        return recipe;
    }

    protected MachineRecipeComponent[] getOutputContents() {
        MachineRecipeComponent[] recipe = new MachineRecipeComponent[getOutputSlots() + getOutputTanks()];
        ItemStack[] outSlot = getOutputSlotContents();
        for(int i = 0; i < getOutputSlots(); i ++) {
            recipe[i] = new MachineRecipeComponent(outSlot[i]);
        }
        FluidStack[] outTank = getOutputTankContents();
        for(int i = 0; i < getOutputTanks(); i ++) {
            recipe[i + getOutputSlots()] = new MachineRecipeComponent(outTank[i]);
        }
        return recipe;
    }

    public FluidStack[] getInputTankContents() {
        FluidStack[] x = new FluidStack[getInputTanks()];
        for(int i = 0; i < getInputTanks(); i ++) {
            x[i] = inputTanks.get(i).getFluid();
        }
        return x;
    }

    private FluidStack[] getOutputTankContents() {
        FluidStack[] x = new FluidStack[getOutputTanks()];
        for(int i = 0; i < getOutputTanks(); i ++) {
            x[i] = outputTanks.get(i).getFluid();
        }
        return x;
    }

    protected MachineRecipeComponent[] getCurrentRecipeInput() {
        List<MachineRecipeComponent> contents = new ArrayList<>();
        contents.addAll(Arrays.asList(ItemUtils.toMachineRecipeArray(getInputSlotContents())));
        contents.addAll(Arrays.asList(FluidUtils.toMachineRecipeArray(getInputTankContents())));
        return RecipeHandler.getInput(this.getClass(), contents.toArray(new MachineRecipeComponent[contents.size()]));
    }

    protected MachineRecipeComponent[] getCurrentRecipeOutput() {
        List<MachineRecipeComponent> contents = new ArrayList<>();
        contents.addAll(Arrays.asList(ItemUtils.toMachineRecipeArray(getInputSlotContents())));
        contents.addAll(Arrays.asList(FluidUtils.toMachineRecipeArray(getInputTankContents())));
        return RecipeHandler.getOutput(this.getClass(), contents.toArray(new MachineRecipeComponent[contents.size()]));
    }

    protected boolean canTanksHoldRecipe(MachineRecipeComponent[] recipeOutput) {
        int tankCount = 0;
        for(MachineRecipeComponent i : recipeOutput) {
            if(i.getType() == MachineRecipeComponent.Type.FLUID) {
                FluidTankBase tank = outputTanks.get(tankCount);
                if(i.getFluidStack().amount <= (tank.getCapacity() - tank.getFluidAmount())) {
                    continue;
                } else {
                    return false;
                }
            }
            tankCount ++;
        }

        return true;
    }

    @Override
    public boolean canProcess() {
        if(RecipeHandler.hasRecipe(this.getClass(), getInputContents())) {
            return RecipeUtils.canComponentArraysStack(getOutputContents(), getCurrentRecipeOutput()) && canTanksHoldRecipe(getCurrentRecipeOutput());
        }

        return false;
    }

    @Override
    public void ejectOutput() {

    }

    @Override
    public void processingComplete() {
        MachineRecipeComponent[] output = getCurrentRecipeOutput();
        MachineRecipeComponent[] input = getCurrentRecipeInput();

        int tankCount = 0;
        int slotCount = 0;

        for(int i = 0; i < output.length; i ++) {
            if(output[i].getType() == MachineRecipeComponent.Type.ITEM) {
                outputOnlySlotsWrapper.insertItemInternally(slotCount, output[i].getItemStack(), false);
                slotCount ++;
            } else if(output[i].getType() == MachineRecipeComponent.Type.FLUID) {
                outputTanks.get(tankCount).fillInternal(output[i].getFluidStack(), true);
                tankCount ++;
            }
        }

        slotCount = 0;
        tankCount = 0;

        for(int i = 0; i < input.length; i ++) {
            if(input[i].getType() == MachineRecipeComponent.Type.ITEM) {
                inputOnlySlotsWrapper.extractItemInternally(slotCount, input[i].getItemStack().getCount(), false);
                slotCount ++;
            } else if(input[i].getType() == MachineRecipeComponent.Type.FLUID) {
                inputTanks.get(tankCount).drainInternal(input[i].getFluidStack(), true);
                tankCount ++;
            }
        }
    }
}
