package ph.adamw.electrolode.block.machine.purifier;

import ph.adamw.electrolode.block.machine.TileItemMachine;


public class TilePurifier extends TileItemMachine {
    public int getInputSize() {
        return 1;
    }

    public int getOutputSize() {
        return 1;
    }

    public int getBaseProcTime() {
        return 80;
    }

    public int getBaseEnergyUsage() {
        return 40;
    }

    public int getMaxEnergyStored() {
        return 25000;
    }
}
