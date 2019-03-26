package ph.adamw.electrolode.block.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.inventory.fluid.DummyFluidTank;
import ph.adamw.electrolode.inventory.fluid.FluidTankBase;
import ph.adamw.electrolode.recipe.FluidStackRecipeComponent;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.recipe.RecipeUtils;
import ph.adamw.electrolode.util.BlockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TileTankedMachine extends TileInventoriedMachine {
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
            List<FluidTankBase> value;
            if(faceMap.getRole(facing) == EnumFaceRole.INPUT_FLUID) {
                value = inputTanks;
            } else if(faceMap.getRole(facing) == EnumFaceRole.OUTPUT_FLUID) {
                value = outputTanks;
            } else {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new DummyFluidTank());
            }

            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(value.get(faceMap.getContainerIndex(facing)));
        }

        return super.getCapability(capability, facing);
    }

    protected abstract void addInputTanks();

    protected abstract void addOutputTanks();

    @Override
    protected void addPotentialFaceRoles() {
        potentialRoles.add(EnumFaceRole.INPUT_ITEM);
        potentialRoles.add(EnumFaceRole.OUTPUT_ITEM);
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

    @Override
    public RecipeComponent[] getInputContents() {
        List<RecipeComponent> ret = new ArrayList<>(Arrays.asList(super.getInputContents()));
        FluidStack[] inTank = getInputTankContents();
        for(int i = 0; i < getInputTanks(); i ++) {
            ret.add(new FluidStackRecipeComponent(inTank[i]));
        }
        return ret.toArray(new RecipeComponent[0]);
    }

    @Override
    public RecipeComponent[] getOutputContents() {
        List<RecipeComponent> ret = new ArrayList<>(Arrays.asList(super.getOutputContents()));
        FluidStack[] outTank = getOutputTankContents();
        for(int i = 0; i < getOutputTanks(); i ++) {
            ret.add(new FluidStackRecipeComponent(outTank[i]));
        }
        return ret.toArray(new RecipeComponent[0]);
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

    private boolean canTanksHoldOutput(RecipeComponent[] output) {
        int tankCount = 0;
        for(RecipeComponent i : output) {
            if(i instanceof FluidStackRecipeComponent) {
                final FluidTankBase tank = outputTanks.get(tankCount);

                if(((FluidStackRecipeComponent) i).copyOf().amount <= (tank.getCapacity() - tank.getFluidAmount())) {
                    // i.e. yes this one can hold the i-th output so test the next tank on the next output
                    tankCount ++;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canProcess() {
        if(RecipeHandler.hasRecipe(this.getClass(), getInputContents())) {
            return RecipeUtils.canComponentArraysStack(
                    getCurrentRecipe().getOutput(), getOutputContents())
                    && canTanksHoldOutput(getCurrentRecipe().getOutput()
            );
        }

        return false;
    }

    @Override
    public void ejectOutput() {
        super.ejectOutput();

        int count = 0;
        for (RecipeComponent k : getOutputContents()) {
            if (!(k instanceof FluidStackRecipeComponent)) {
                continue;
            }

            final FluidStackRecipeComponent component = (FluidStackRecipeComponent) k;

            for (EnumFacing i : faceMap.keySet()) {
                if (faceMap.getRole(i) == EnumFaceRole.OUTPUT_FLUID) {
                    TileEntity neighbour = world.getTileEntity(BlockUtils.getNeighbourPos(pos, i));
                    if (neighbour == null) continue;
                    IFluidHandler x = neighbour.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, i);
                    if (x == null) continue;
                    int attempt = x.fill(component.copyOf(), false);
                    if (attempt != 0) {
                        outputTanks.get(count).drain(attempt, true);
                        x.fill(new FluidStack(component.copyOf().getFluid(), attempt), true);
                    }
                }
            }
            count++;
        }
    }

    public void processingComplete() {
        final MachineRecipe recipe = getCurrentRecipe();
        final RecipeComponent[] output = recipe.getOutput();
        final RecipeComponent[] input = recipe.getInput();

        int tankCount = 0;
        int slotCount = 0;

        for (RecipeComponent component : output) {
            if (component instanceof ItemStackRecipeComponent) {
                outputOnlySlotsWrapper.insertItemInternally(slotCount, ((ItemStackRecipeComponent) component).copyOf(), false);
                slotCount++;
            } else if (component instanceof FluidStackRecipeComponent) {
                outputTanks.get(tankCount).fillInternal(((FluidStackRecipeComponent) component).copyOf(), true);
                tankCount++;
            }
        }

        slotCount = 0;
        tankCount = 0;

        for (RecipeComponent component : input) {
            if (component instanceof ItemStackRecipeComponent) {
                inputOnlySlotsWrapper.extractItemInternally(slotCount, ((ItemStackRecipeComponent) component).copyOf().getCount(), false);
                slotCount++;
            } else if (component instanceof FluidStackRecipeComponent) {
                inputTanks.get(tankCount).drainInternal(((FluidStackRecipeComponent) component).copyOf(), true);
                tankCount++;
            }
        }
    }
}
