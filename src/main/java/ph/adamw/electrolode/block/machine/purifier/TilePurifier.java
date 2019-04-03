package ph.adamw.electrolode.block.machine.purifier;

import ph.adamw.electrolode.block.machine.TileItemMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;


public class TilePurifier extends TileItemMachine {
    @Override
    public Class<? extends ElectrolodeContainer> getContainerClass() {
        return ContainerPurifier.class;
    }

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
