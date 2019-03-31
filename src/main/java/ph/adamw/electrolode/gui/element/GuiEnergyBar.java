package ph.adamw.electrolode.gui.element;

import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.util.TextureWrapper;

public class GuiEnergyBar extends GuiElement {
    private TileMachine tileEntity;

    private int X_POS;
    private int Y_POS;
    private int WIDTH;
    private int HEIGHT;
    private boolean vertical;

    private int TEXTURE_X = 0;
    private int TEXTURE_Y = 0;

    public GuiEnergyBar(TileMachine te, GuiBaseContainer gui, ResourceLocation res, int x, int y, int width, int height, boolean vertical) {
        super(gui, res);
        tileEntity = te;
        X_POS = x;
        Y_POS = y;
        WIDTH = width;
        HEIGHT = height;
        this.vertical = vertical;
    }

    public GuiEnergyBar(TileMachine te, GuiBaseContainer gui, TextureWrapper helper, int x, int y, boolean vertical) {
        this(te, gui, helper.resource, x, y, helper.WIDTH, helper.HEIGHT, vertical);
        TEXTURE_X = helper.X_ORIGIN;
        TEXTURE_Y = helper.Y_ORIGIN;
    }

    public GuiEnergyBar(TileMachine te, GuiBaseContainer gui, int x, int y, int width, int height, boolean vertical) {
        this(te, gui, new TextureWrapper(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonenergybars.png"), 0, 0, width, height), x, y, vertical);
    }

    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);
        if(vertical) {
            int fullHeight = (int) (tileEntity.getEnergyPercentage() * HEIGHT);
            int emptyHeight = (HEIGHT - fullHeight);
            guiObj.drawTexturedModalRect(guiWidth + X_POS, guiHeight + emptyHeight + Y_POS, TEXTURE_X, emptyHeight + TEXTURE_Y, WIDTH, fullHeight);
        } else {
            guiObj.drawTexturedModalRect(guiWidth + X_POS, guiHeight + Y_POS, TEXTURE_X, TEXTURE_Y, (int) (tileEntity.getEnergyPercentage() * WIDTH), HEIGHT);
        }
    }

    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, X_POS, Y_POS, HEIGHT, WIDTH) && getTooltip() != null) {
            displayTooltip(getTooltip(), xAxis, yAxis);
        }
    }

    public String getTooltip() {
        return tileEntity.getEnergyStored() + " / " + tileEntity.getMaxEnergyStored() + " RF";
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {}
}
