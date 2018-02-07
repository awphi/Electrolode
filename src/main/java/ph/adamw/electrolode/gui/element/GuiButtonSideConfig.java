package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.networking.PacketHandler;
import ph.adamw.electrolode.networking.PacketSideConfigUpdate;

public class GuiButtonSideConfig extends GuiButtonElement {
    private EnumFaceRole current;
    private EnumFacing direction;
    private String tooltipSpecific;

    public GuiButtonSideConfig(GuiBaseContainer gui, int x, int y, EnumFacing direction, EnumFaceRole current, boolean isLocked) {
        super(gui, -1, new ResourceLocation(Electrolode.MODID,"textures/gui/extensions/sideconfigbutton.png") , x, y, 16, 16);

        this.current = current;
        this.direction = direction;
        tooltipSpecific = current.getLocalizedName();
        if(isLocked) {
            tooltipSpecific = current.getLocalizedName() + " §l(" + I18n.format("tooltip.electrolode.locked") + ")";
            disabled = true;
        }
    }

    private void stepRole(boolean up) {
        current = up ? EnumFaceRole.next(current) : EnumFaceRole.previous(current);
        PacketHandler.INSTANCE.sendToServer(new PacketSideConfigUpdate(guiObj.tileEntity.getPos(), direction, current));
    }

    @Override
    public String getTooltip() {
        return current.getLocalizedName();
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if(!disabled) {
            if (!isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, 0, current.getValue() * HEIGHT, WIDTH, HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, WIDTH, current.getValue() * HEIGHT, WIDTH, HEIGHT);
            }
        } else {
            guiObj.drawTexturedModalRect(guiWidth + REL_X * 2, guiHeight + REL_Y, WIDTH, current.getValue() * HEIGHT, WIDTH, HEIGHT);
        }
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && getTooltip() != null) {
            displayTooltip(getTooltip(), xAxis, yAxis);
        }
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        System.out.println(button);
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && !disabled) {
            if(button == 0) {
                stepRole(true);
            } else if(button == 1) {
                stepRole(false);
            }
        }
    }
}
