package ph.adamw.electrolode.gui.element;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.util.GuiUtils;

import javax.annotation.Nonnull;

public class GuiTank extends GuiElement {
    private int X_POS;
    private int Y_POS;

    private int WIDTH;
    private int HEIGHT;
    private FluidTank tank;

    public GuiTank(@Nonnull FluidTank tank, GuiBaseContainer gui, int x, int y, int width, int height) {
        super(gui, new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/commonfluidtanks.png"));
        this.tank = tank;
        X_POS = x;
        Y_POS = y;
        WIDTH = width;
        HEIGHT = height;
    }

    private double getTankPercentage() {
        if(tank.getFluid() == null) {
            return 0;
        } else {
            return ((double) tank.getFluidAmount() / (double) tank.getCapacity());
        }
    }

    public String getTooltip() {
        String e = "0";
        if(tank.getFluid() != null) {
            e = String.valueOf(tank.getFluidAmount());
        }
        return e + " / " + tank.getCapacity() + " mB";
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {
    }


    public void mouseClicked(int xAxis, int yAxis, int button) {

    }

    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if(tank.getFluid() != null) {
            TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(tank.getFluid().getFluid().getStill().toString());
            int full = (int) (HEIGHT * getTankPercentage());
            int empty = HEIGHT - full;
            GuiUtils.fillAreaWithSprite(mc, guiObj, sprite, TextureMap.LOCATION_BLOCKS_TEXTURE, guiWidth + X_POS, guiHeight + Y_POS + empty, WIDTH, full);
        }

        // Then here we draw the notches on top of the fluid display
    }

    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, X_POS, Y_POS, HEIGHT, WIDTH) && getTooltip() != null) {
            displayTooltip(getTooltip(), xAxis, yAxis);
        }
    }
}
