package ph.adamw.electrolode.manager;

import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.block.machine.coalgenerator.ContainerCoalGenerator;
import ph.adamw.electrolode.block.machine.coalgenerator.TileCoalGenerator;
import ph.adamw.electrolode.block.machine.press.ContainerPress;
import ph.adamw.electrolode.block.machine.press.TilePress;
import ph.adamw.electrolode.block.machine.purifier.ContainerPurifier;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.block.machine.softener.ContainerSoftener;
import ph.adamw.electrolode.block.machine.softener.TileSoftener;
import ph.adamw.electrolode.gui.GuiEntry;
import ph.adamw.electrolode.gui.machine.GuiCoalGenerator;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.gui.machine.GuiPress;
import ph.adamw.electrolode.gui.machine.GuiPurifier;
import ph.adamw.electrolode.gui.machine.GuiSoftener;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.inventory.MachineContainer;

import java.util.HashMap;

public class GuiManager {
    private static int currentID = 1;

    private static int nextID() {
        return currentID ++;
    }

    private static HashMap<Integer, GuiEntry> guiMap = new HashMap<>();

    public static int getGuiId(TileMachine e) {
        return getGuiId(e.getClass());
    }

    public static void registerGui(Class<? extends GuiMachine> gui, Class<? extends ElectrolodeContainer> cont, Class<? extends TileMachine> tile) {
        guiMap.put(nextID(), new GuiEntry(gui, cont, tile));
    }

    public static GuiEntry getGuiEntry(int x) {
        if(guiMap.containsKey(x)) {
            return guiMap.get(x);
        }

        return null;
    }

    public static int getGuiId(Class<? extends TileMachine> e) {
        for(Integer i : guiMap.keySet()) {
            if(guiMap.get(i).tileEntity == e) return i;
        }

        return -1;
    }

    public static void registerGuis() {
        registerGui(GuiPurifier.class, ContainerPurifier.class, TilePurifier.class);
        registerGui(GuiPress.class, ContainerPress.class, TilePress.class);
        registerGui(GuiSoftener.class, ContainerSoftener.class, TileSoftener.class);
        registerGui(GuiCoalGenerator.class, ContainerCoalGenerator.class, TileCoalGenerator.class);
    }
}
