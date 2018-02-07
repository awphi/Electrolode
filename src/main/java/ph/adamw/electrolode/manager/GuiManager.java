package ph.adamw.electrolode.manager;

import ph.adamw.electrolode.block.machine.press.ContainerPress;
import ph.adamw.electrolode.block.machine.press.TilePress;
import ph.adamw.electrolode.block.machine.purifier.ContainerPurifier;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.gui.GuiPress;
import ph.adamw.electrolode.gui.GuiPurifier;
import ph.adamw.electrolode.util.GuiUtils;

public class GuiManager {
    public static void registerGuis() {
        //Purifier GUI
        GuiUtils.registerGui(GuiPurifier.class, ContainerPurifier.class, TilePurifier.class);

        //Press GUI
        GuiUtils.registerGui(GuiPress.class, ContainerPress.class, TilePress.class);
    }
}
