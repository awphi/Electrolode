package ph.adamw.electrolode.block.machine;

import net.minecraft.nbt.NBTTagCompound;
import ph.adamw.electrolode.recipe.ElectrolodeRecipe;
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
			if (canProcess()) {
				processedTime ++;
				//TODO output energy / proc time into the buffer
			} else {
				resetProcess();
			}
		}
	}

	//TODO energy extraction capability

	@Override
	public boolean canProcess() {
		//TODO check if it's already processing
		return currentRecipe == null;
	}

	@Override
	public void resetProcess() {
		currentRecipe = null;
		super.resetProcess();
	}

	@Override
	public ElectrolodeRecipe getCurrentRecipe() {
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
		currentRecipe = (GeneratorRecipe) ElectrolodeRecipe.fromNBT(compound.getCompoundTag("currentRecipe"));
	}

	@Override
	public boolean canExtract() {
		return 0 < getEnergyStored();
	}

	@Override
	public void ejectOutput() {}
}
