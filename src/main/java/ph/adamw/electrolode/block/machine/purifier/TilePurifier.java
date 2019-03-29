package ph.adamw.electrolode.block.machine.purifier;

import ph.adamw.electrolode.block.machine.TileItemMachine;


public class TilePurifier extends TileItemMachine {
    public int getInputSlots() {
        return 1;
    }

    public int getOutputSlots() {
        return 1;
    }

    public int getBaseEnergyUsage() {
        return 40;
    }

    public int getBaseMaxEnergy() {
        return 25000;
    }
}
