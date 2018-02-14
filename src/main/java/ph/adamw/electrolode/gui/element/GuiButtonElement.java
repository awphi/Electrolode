package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.util.GuiUtils;
import ph.adamw.electrolode.util.TextureHelper;

import java.util.Arrays;
import java.util.List;


// Texture file should consist of 3 buttons of the same height with textures in these order:
// [] Unhovered
// [] Hovered
// [] Disabled

public class GuiButtonElement extends GuiElement {
    int REL_X;
    int REL_Y;
    int WIDTH;
    int HEIGHT;

    int ORIGIN_X = 0;
    int ORIGIN_Y = 0;

    int buttonId;

    public boolean disabled = false;

    public GuiButtonElement(GuiBaseContainer gui, int id, ResourceLocation resource, int x, int y, int width, int height, String tooltip) {
        super(gui, resource);
        buttonId = id;
        REL_X = x;
        REL_Y = y;
        WIDTH = width;
        HEIGHT = height;

        this.tooltip = tooltip;
    }

    public GuiButtonElement(GuiBaseContainer gui, int id, ResourceLocation resource, int x, int y, int width, int height) {
            this(gui, id, resource, x, y, width, height, null);
    }

    public GuiButtonElement(GuiBaseContainer gui, int id, TextureHelper helper, int x, int y, int width, int height, String tooltip) {
        this(gui, id, helper.resource, x, y, width, height, tooltip);
        ORIGIN_X = helper.X_ORIGIN;
        ORIGIN_Y = helper.Y_ORIGIN;
    }

    public GuiButtonElement(GuiBaseContainer gui, int id, TextureHelper helper, int x, int y, String toolTip) {
        this(gui, id, helper, x, y, helper.WIDTH, helper.HEIGHT, toolTip);
    }

    public GuiButtonElement(GuiBaseContainer gui, int id, GuiUtils.CommonExtensions button, int x, int y) {
        this(gui, id, button.getTextureHelper(), x, y, button.getTooltip());
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && !disabled) {
            guiObj.onAction(buttonId, button);
        }
    }

    public String getTooltip() {
        return I18n.format(tooltip);
    }

    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if(!disabled) {
            if (isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y + HEIGHT, WIDTH, HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y, WIDTH, HEIGHT);
            }
        } else {
            guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y + HEIGHT * 2, WIDTH, HEIGHT);
        }
    }

    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && !disabled && getTooltip() != null) {
            if(getTooltip().contains("#n")) {
                List<String> x = Arrays.asList(getTooltip().split("#n"));
                displayTooltips(x, xAxis, yAxis);
            } else {
                displayTooltip(getTooltip(), xAxis, yAxis);
            }
        }
    }
}
