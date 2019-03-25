package ph.adamw.electrolode.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.gui.GuiEntry;
import ph.adamw.electrolode.gui.machine.GuiMachineBasic;
import ph.adamw.electrolode.inventory.BaseMachineContainer;

import java.util.HashMap;

public class GuiUtils {
    public enum CommonExtensions {
        BACK(I18n.format("gui.electrolode.back.name"), new TextureHelper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 16, 0, 16, 16)),
        AUTO_EJECT(I18n.format("gui.electrolode.eject.name"), new TextureHelper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 32, 0, 16, 16));

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

    public static int step(int start, int max, int min, int step) {
        start += step;
        if(start == max) {
            start =  min;
        } else if(start < min) {
            start = (max - 1);
        }

        if((max - 1) - min == 1) {
            start = min;
        }
        return start;
    }

    public static void fillAreaWithSprite(Minecraft mc, GuiBaseContainer gui, TextureAtlasSprite sprite, ResourceLocation spriteMap, int x, int y, int width, int height) {
        mc.renderEngine.bindTexture(spriteMap);
        double yRatio = (double) height / (double) sprite.getIconHeight();

        int xCoord = x;
        int yCoord = y;

        while(yRatio > 0) {
            double v = Math.min(1, yRatio);
            yRatio -= v;

            gui.drawTexturedModalRect(xCoord, yCoord, sprite, width, (int) (v * sprite.getIconHeight()));
            yCoord += (int) (v * sprite.getIconHeight());
        }
    }
}
