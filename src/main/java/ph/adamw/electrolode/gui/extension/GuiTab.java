package ph.adamw.electrolode.gui.extension;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.gui.GuiSideConfig;

import java.util.Arrays;
import java.util.List;

public abstract class GuiTab extends GuiExtension {
    public TileBaseMachine tileEntity;
    public boolean rightAligned;

    public int NORMAL_HEIGHT;
    public int NORMAL_WIDTH;

    public int EXT_HEIGHT;
    public int EXT_WIDTH;

    public int Y_OFFSET;

    public GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int normalWidth, int normalHeight, int extendedWidth, int extendedHeight, int yOffset, boolean rightAligned) {
        super(gui, resource);
        tileEntity = e;

        this.rightAligned = rightAligned;

        NORMAL_HEIGHT = normalHeight;
        NORMAL_WIDTH = normalWidth;

        EXT_HEIGHT = extendedHeight;
        EXT_WIDTH = extendedWidth;

        Y_OFFSET = yOffset;
    }

    public GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int normalWidth, int normalHeight, int extendedWidth, int extendedHeight, int yOffset) {
        this(resource, gui, e, normalWidth, normalHeight, extendedWidth, extendedHeight, yOffset, true);
    }

    public GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int yOffset) {
        this(resource, gui, e, 16, 24, 24, 24, yOffset, true);
    }

    public GuiTab(ResourceLocation resource, GuiBaseContainer gui, TileBaseMachine e, int yOffset, boolean rightAligned) {
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
        //TODO simplify this logic with some ????
        if(!isInTab(xAxis, yAxis)) {
            if(rightAligned) {
                guiObj.drawTexturedModalRect(guiWidth + guiObj.getWidth(), guiHeight + Y_OFFSET, 0, 0, NORMAL_WIDTH, NORMAL_HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth - NORMAL_WIDTH, guiHeight + Y_OFFSET, EXT_WIDTH - NORMAL_WIDTH, 0, NORMAL_WIDTH, NORMAL_HEIGHT);
            }
        } else {
            if(rightAligned) {
                guiObj.drawTexturedModalRect(guiWidth + guiObj.getWidth(), guiHeight + Y_OFFSET, 0, NORMAL_HEIGHT, EXT_WIDTH, EXT_HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth - EXT_WIDTH, guiHeight + Y_OFFSET, 0, NORMAL_HEIGHT, EXT_WIDTH, EXT_HEIGHT);
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

    protected boolean isInTab(int xAxis, int yAxis) {
        if(rightAligned) {
            return isInRect(xAxis, yAxis, guiObj.getWidth(), Y_OFFSET, NORMAL_HEIGHT, NORMAL_WIDTH);
        } else {
            return isInRect(xAxis, yAxis, -NORMAL_WIDTH, Y_OFFSET, NORMAL_HEIGHT, NORMAL_WIDTH);
        }
    }
}
