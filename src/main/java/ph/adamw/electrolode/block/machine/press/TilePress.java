package ph.adamw.electrolode.block.machine.press;

import ph.adamw.electrolode.block.machine.TileItemMachine;

public class TilePress extends TileItemMachine {
    public int getInputSize() {
        return 3;
    }

    public int getOutputSize() {
        return 1;
    }

    public int getBaseProcTime() {
        return 120;
    }

    public int getBaseEnergyUsage() {
        return 240;
    }

    public int getMaxEnergyStored() {
        return 25000;
    }

    public void processingComplete() {
        outputOnlySlotsWrapper.insertItemInternally(0, getCurrentRecipeOutput()[0], false);
        for(int i = 0; i < getInputSize(); i ++) {
            inputOnlySlotsWrapper.extractItemInternally(i, getCurrentRecipeInput()[i].getCount(), false);
        }
    }
}
