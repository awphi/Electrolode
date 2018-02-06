package ph.adamw.electrolode.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiEntry;
import ph.adamw.electrolode.gui.GuiMachineBasic;
import ph.adamw.electrolode.inventory.BaseMachineContainer;

import java.util.HashMap;

public class GuiUtils {
    public enum CommonExtensions {
        BACK(I18n.format("gui.electrolode.back.name"), new TextureHelper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 16, 0, 16, 16)),
        AUTO_EJECT(null, new TextureHelper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 32, 0, 16, 16));

        private String toolTip;
        private TextureHelper textureHelper;

        CommonExtensions(String x, TextureHelper tx) {
            toolTip = x;
            textureHelper = tx;
        }

        public TextureHelper getTextureHelper() {
            return textureHelper;
        }

        public String getTooltip() {
            return toolTip;
        }
    }

    private static int currentID = 1;

    private static int nextID() {
        return currentID ++;
    }

    public static void registerGui(Class<? extends GuiMachineBasic> gui, Class<? extends BaseMachineContainer> cont, Class<? extends TileBaseMachine> tile) {
        guiMap.put(nextID(), new GuiEntry(gui, cont, tile));
    }

    private static HashMap<Integer, GuiEntry> guiMap = new HashMap<>();

    public static GuiEntry getGuiEntry(int x) {
        if(guiMap.containsKey(x)) {
            return guiMap.get(x);
        }

        return null;
    }

    public static int getGuiId(Class<? extends TileBaseMachine> e) {
        for(Integer i : guiMap.keySet()) {
            if(guiMap.get(i).tileEntity == e) return i;
        }

        return -1;
    }

    public static int getGuiId(TileBaseMachine e) {
        return getGuiId(e.getClass());
    }
}
