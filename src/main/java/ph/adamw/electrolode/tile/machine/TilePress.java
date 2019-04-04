package ph.adamw.electrolode.tile.machine;

import ph.adamw.electrolode.inventory.machine.ContainerPress;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.gui.machine.GuiPress;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.tile.machine.core.TileItemMachine;

public class TilePress extends TileItemMachine {
    @Override
    public Class<? extends ElectrolodeContainer> getContainerClass() {
        return ContainerPress.class;
    }

    @Override
    public Class<? extends GuiMachine> getGuiClass() {
        return GuiPress.class;
    }

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
