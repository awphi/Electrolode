package ph.adamw.electrolode.tile.machine.core;

import net.minecraft.nbt.NBTTagCompound;
import ph.adamw.electrolode.energy.ElectroEnergyProducer;
import ph.adamw.electrolode.recipe.GeneratorRecipe;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeHandler;

/**
 * A generator base - effectively a machine that will 'absorb' the input to produce power and and, if given, outputs.
 */
public abstract class TileInventoriedGenerator extends TileInventoriedMachine {
	private GeneratorRecipe currentRecipe;

	public TileInventoriedGenerator() {
		setEnergy(new ElectroEnergyProducer(this, getBaseMaxEnergy()));
	}

	@Override
	public void tick() {
		if(!world.isRemote) {
			if(currentRecipe != null) {
				processedTime ++;
				getEnergy().receiveEnergyInternal((int) ((double) currentRecipe.getEnergy() / (double) currentRecipe.getTime()), false);
			}

			((ElectroEnergyProducer) getEnergy()).attemptEnergyDump(getEnergy().getEnergyStored());

			if(getProcTime() <= processedTime) {
				depositOutput(currentRecipe);
				resetProcess();
			}

			if(currentRecipe == null && canProcess()) {
				final MachineRecipe recipe = RecipeHandler.findRecipe(getClass(), getInputContents());
				if(recipe instanceof GeneratorRecipe) {
					currentRecipe = (GeneratorRecipe) recipe;
					extractInput(currentRecipe);
				} else {
					System.err.println("Resolved a MachineRecipe (not a GeneratorRecipe) for a generator with last input contents - please check your registered generator recipes!");
				}
			}
		}
	}

	@Override
	public void processingComplete() {}

	@Override
	public void resetProcess() {
		currentRecipe = null;
		super.resetProcess();
	}

	@Override
	public int getProcTime() {
		if(currentRecipe == null) {
			return Integer.MAX_VALUE;
		}

		return currentRecipe.getTime();
	}

	@Override
	public int getBaseEnergyUsage() {
		return 0;
	}

	public int getEnergyOutput() {
		if(currentRecipe == null) {
			return 0;
		}

		return (int) ((double) currentRecipe.getEnergy() / (double) currentRecipe.getTime());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		if(currentRecipe != null) {
			compound.setTag("currentRecipe", currentRecipe.writeToNBT(new NBTTagCompound()));
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		currentRecipe = GeneratorRecipe.fromNBT(compound.getCompoundTag("currentRecipe"));
	}

	@Override
	public void ejectOutput() {}

	public abstract void extractInput(GeneratorRecipe recipe);

	public abstract void depositOutput(GeneratorRecipe recipe);
}
