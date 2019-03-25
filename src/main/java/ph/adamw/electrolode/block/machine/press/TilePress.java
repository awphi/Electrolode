package ph.adamw.electrolode.block.machine.press;

import ph.adamw.electrolode.block.machine.TileItemMachine;

public class TilePress extends TileItemMachine {
    public int getInputSlots() {
        return 3;
    }

    public int getOutputSlots() {
        return 1;
    }

    public int getBaseEnergyUsage() {
        return 240;
    }

    public int getBaseMaxEnergy() {
        return 25000;
    }
}
