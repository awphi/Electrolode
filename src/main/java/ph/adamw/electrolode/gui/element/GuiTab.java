package ph.adamw.electrolode.gui.element;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.gui.machine.GuiSideConfig;

import java.util.Arrays;
import java.util.List;

public abstract class GuiTab extends GuiElement {
    public TileBaseMachine tileEntity;
    private boolean rightAligned;

    int normalHeight;
    int normalWidth;

    private int extendedHeight;
    private int extendedWidth;

    int yOffset;

    private GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int normalWidth, int normalHeight, int extendedWidth, int extendedHeight, int yOffset, boolean rightAligned) {
        super(gui, resource);
        tileEntity = e;

        this.rightAligned = rightAligned;

        this.normalHeight = normalHeight;
        this.normalWidth = normalWidth;

        this.extendedHeight = extendedHeight;
        this.extendedWidth = extendedWidth;

        this.yOffset = yOffset;
    }

    public GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int normalWidth, int normalHeight, int extendedWidth, int extendedHeight, int yOffset) {
        this(resource, gui, e, normalWidth, normalHeight, extendedWidth, extendedHeight, yOffset, true);
    }

    GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int yOffset) {
        this(resource, gui, e, 16, 24, 24, 24, yOffset, true);
    }

    GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int yOffset, boolean rightAligned) {
        this(resource, gui, e, 16, 24, 24, 24, yOffset, rightAligned);
    }

    public void renderForeground(int xAxis, int yAxis) {
        if(isInTab(xAxis, yAxis)) {
            if(getTooltip().contains("#n")) {
                List<String> x = Arrays.asList(getTooltip().split("#n"));
                displayTooltips(x, xAxis, yAxis);
            } else {
                displayTooltip(getTooltip(), xAxis, yAxis);
            }
        }
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(!isInTab(xAxis, yAxis)) {
            if(rightAligned) {
                guiObj.drawTexturedModalRect(guiWidth + guiObj.getWidth(), guiHeight + yOffset, 0, 0, normalWidth, normalHeight);
            } else {
                guiObj.drawTexturedModalRect(guiWidth - normalWidth, guiHeight + yOffset, extendedWidth - normalWidth, 0, normalWidth, normalHeight);
            }
        } else {
            if(rightAligned) {
                guiObj.drawTexturedModalRect(guiWidth + guiObj.getWidth(), guiHeight + yOffset, 0, normalHeight, extendedWidth, extendedHeight);
            } else {
                guiObj.drawTexturedModalRect(guiWidth - extendedWidth, guiHeight + yOffset, 0, normalHeight, extendedWidth, extendedHeight);
            }
        }
    }

    public void mouseClicked(int xAxis, int yAxis, int button) {
        if(button == 0) {
            if(isInTab(xAxis, yAxis)) {
                mc.displayGuiScreen(new GuiSideConfig(tileEntity));
            }
        }
    }

    private boolean isInTab(int xAxis, int yAxis) {
        if(rightAligned) {
            return isInRect(xAxis, yAxis, guiObj.getWidth(), yOffset, normalHeight, normalWidth);
        } else {
            return isInRect(xAxis, yAxis, -normalWidth, yOffset, normalHeight, normalWidth);
        }
    }
}
