package ph.adamw.electrolode.block.machines.purifier;

import ph.adamw.electrolode.block.machines.TileSimpleMachine;


public class TilePurifier extends TileSimpleMachine {
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
