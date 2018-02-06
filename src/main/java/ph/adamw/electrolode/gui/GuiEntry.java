package ph.adamw.electrolode.gui;

import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.inventory.BaseMachineContainer;

public class GuiEntry {
    public Class<? extends GuiMachineBasic> guiMachineBasic;
    public Class<? extends BaseMachineContainer> container;
    public Class<? extends TileBaseMachine> tileEntity;

    public GuiEntry(Class<? extends GuiMachineBasic> gui, Class<? extends BaseMachineContainer> container, Class<? extends TileBaseMachine> tile) {
        guiMachineBasic = gui;
        this.container = container;
        tileEntity = tile;
    }
}
