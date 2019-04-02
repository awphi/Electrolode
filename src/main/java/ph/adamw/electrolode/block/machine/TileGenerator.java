package ph.adamw.electrolode.block.machine;

import net.minecraft.nbt.NBTTagCompound;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.GeneratorRecipe;

/**
 * The basis 'hijacking' of the machine tile to create a generator.
 */
public abstract class TileGenerator extends TileInventoriedMachine {
	private GeneratorRecipe currentRecipe;

	public TileGenerator() {
		chargeSlotWrapper.setSize(0);
	}

	@Override
	public void tick() {
		if(!world.isRemote) {
			if(currentRecipe != null && currentRecipe.getTime() >= processedTime) {
				resetProcess();
			}

			if(currentRecipe == null) {
				//TODO Look for and accept a new recipe if it's found
			} else {
				processedTime ++;
				receiveEnergy((int) ((float) currentRecipe.getEnergy() / (float) currentRecipe.getTime()), false);
			}
		}
	}

	@Override
	public void resetProcess() {
		currentRecipe = null;
		super.resetProcess();
	}

	@Override
	public MachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	@Override
	public int getBaseEnergyUsage() {
		return 0;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setTag("currentRecipe", currentRecipe.toNBT());

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		currentRecipe = GeneratorRecipe.fromNBT(compound.getCompoundTag("currentRecipe"));
	}

	@Override
	public boolean canExtract() {
		return 0 < getEnergyStored();
	}

	@Override
	public void ejectOutput() {}
}
