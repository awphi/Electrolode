package ph.adamw.electrolode.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.gui.GuiBaseContainer;

public class GuiUtils {
    public enum CommonExtensions {
        BACK(I18n.format("gui.electrolode.back.name"), new TextureWrapper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 16, 0, 16, 16)),
        AUTO_EJECT(I18n.format("gui.electrolode.eject.name"), new TextureWrapper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonbuttons.png"), 32, 0, 16, 16));

        @Getter
        private String toolTip;

        @Getter
        private TextureWrapper textureWrapper;

        CommonExtensions(String x, TextureWrapper tx) {
            toolTip = x;
            textureWrapper = tx;
        }
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
