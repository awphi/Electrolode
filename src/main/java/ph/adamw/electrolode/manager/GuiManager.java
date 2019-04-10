package ph.adamw.electrolode.manager;

import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.gui.GuiEntry;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;

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
}
