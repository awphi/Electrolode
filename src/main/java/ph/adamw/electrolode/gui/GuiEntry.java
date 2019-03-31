package ph.adamw.electrolode.gui;

import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.gui.machine.GuiMachineBasic;
import ph.adamw.electrolode.inventory.BaseMachineContainer;

public class GuiEntry {
    public Class<? extends GuiMachineBasic> guiMachineBasic;
    public Class<? extends BaseMachineContainer> container;
    public Class<? extends TileMachine> tileEntity;

    public GuiEntry(Class<? extends GuiMachineBasic> gui, Class<? extends BaseMachineContainer> container, Class<? extends TileMachine> tile) {
        guiMachineBasic = gui;
        this.container = container;
        tileEntity = tile;
    }
}
