package ph.adamw.electrolode.tile.machine;

import ph.adamw.electrolode.inventory.machine.ContainerPurifier;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.gui.machine.GuiPurifier;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.tile.machine.core.TileItemMachine;


public class TilePurifier extends TileItemMachine {
    @Override
    public Class<? extends ElectrolodeContainer> getContainerClass() {
        return ContainerPurifier.class;
    }

    @Override
    public Class<? extends GuiMachine> getGuiClass() {
        return GuiPurifier.class;
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
