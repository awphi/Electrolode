package ph.adamw.electrolode.gui;

import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;

public class GuiEntry {
    public Class<? extends GuiMachine> guiMachineBasic;
    public Class<? extends ElectrolodeContainer> container;
    public Class<? extends TileMachine> tileEntity;

    public GuiEntry(Class<? extends GuiMachine> gui, Class<? extends ElectrolodeContainer> container, Class<? extends TileMachine> tile) {
        guiMachineBasic = gui;
        this.container = container;
        tileEntity = tile;
    }
}
