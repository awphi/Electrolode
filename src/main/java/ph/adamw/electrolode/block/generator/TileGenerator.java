package ph.adamw.electrolode.block.generator;

import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.recipe.ElectrolodeRecipe;
import ph.adamw.electrolode.recipe.GeneratorRecipe;

public abstract class TileGenerator extends TileInventoriedMachine {
	private GeneratorRecipe currentRecipe;

	public TileGenerator() {
		chargeSlotWrapper.setSize(0);
	}

	@Override
	public void tick() {
		if(!world.isRemote) {
			if (canProcess()) {
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
	public void ejectOutput() {}
}
