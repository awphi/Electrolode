package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureHelper;

public class GuiProgressBar extends GuiElement {
    private int PLACEHOLDER_X;
    private int PLACEHOLDER_Y;

    private int BAR_U = 0;
    private int BAR_V = 0;

    private int WIDTH;
    private int HEIGHT;

    private EnumGuiDirection direction;
    private TileBaseMachine tileEntity;
    private String tooltipSpecific;

    public GuiProgressBar(TileBaseMachine te, GuiBaseContainer gui, ResourceLocation res, int x, int y, int u, int v, int width, int height, EnumGuiDirection dir) {
        super(gui, res);
        PLACEHOLDER_X = x;
        PLACEHOLDER_Y = y;
        BAR_U = u;
        BAR_V = v;
        WIDTH = width;
        HEIGHT = height;
        direction = dir;
        tileEntity = te;
        tooltipSpecific = ((BlockBase) tileEntity.getWorld().getBlockState(tileEntity.getPos()).getBlock()).getBlockName();
        tooltipSpecific = I18n.format("tooltip.electrolode." + tooltipSpecific + "progress");
    }

    public GuiProgressBar(TileBaseMachine te, GuiBaseContainer gui, ResourceLocation res, int x, int y, int width, int height, EnumGuiDirection dir) {
        this(te, gui, res, x, y, 0, 0, width, height, dir);
    }

    public GuiProgressBar(TileBaseMachine te, GuiBaseContainer gui, TextureHelper helper, int x, int y, EnumGuiDirection dir) {
        this(te, gui, helper.resource, x, y, helper.X_ORIGIN, helper.Y_ORIGIN, helper.WIDTH, helper.HEIGHT, dir);
    }

    public String getTooltip() {
        return tooltip;
    }

    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        double progress = tileEntity.getCompletedPercentage();
        tooltip = Math.round(tileEntity.getCompletedPercentage() * 100) + "% " + tooltipSpecific;
        // For some reason this doesn't render properly when in a switch?
        if(direction == EnumGuiDirection.RIGHT) {
            guiObj.drawTexturedModalRect(guiWidth + PLACEHOLDER_X, guiHeight + PLACEHOLDER_Y, BAR_U, BAR_V, (int) (progress * WIDTH), HEIGHT);
        } else if(direction == EnumGuiDirection.DOWN) {
            guiObj.drawTexturedModalRect(guiWidth + PLACEHOLDER_X, guiHeight + PLACEHOLDER_Y, BAR_U, BAR_V, WIDTH, (int) (progress * HEIGHT));
        } else if(direction == EnumGuiDirection.LEFT) {
            int full = (int) (progress * WIDTH);
            int empty = (WIDTH - full);
            guiObj.drawTexturedModalRect(guiWidth + empty + PLACEHOLDER_X, guiHeight + PLACEHOLDER_Y, empty + BAR_U, BAR_V, full, HEIGHT);
        } else if(direction == EnumGuiDirection.UP) {
            int full = (int) (progress * HEIGHT);
            int empty = (HEIGHT - full);
            guiObj.drawTexturedModalRect(guiWidth + PLACEHOLDER_X, guiHeight + empty + PLACEHOLDER_Y, BAR_U, empty + BAR_V, WIDTH, full);
        }
    }

    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, PLACEHOLDER_X, PLACEHOLDER_Y, HEIGHT, WIDTH) && getTooltip() != null) {
            displayTooltip(getTooltip(), xAxis, yAxis);
        }
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {}
}
