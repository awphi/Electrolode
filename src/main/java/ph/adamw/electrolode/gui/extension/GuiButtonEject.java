package ph.adamw.electrolode.gui.extension;

import net.minecraft.client.resources.I18n;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.networking.PacketAutoEjectUpdate;
import ph.adamw.electrolode.networking.PacketHandler;
import ph.adamw.electrolode.util.GuiUtils;
import ph.adamw.electrolode.util.StringUtils;

public class GuiButtonEject extends GuiExtensionButton {
    private boolean current;

    public GuiButtonEject(GuiBaseContainer gui, int x, int y, boolean current) {
        super(gui, -1, GuiUtils.CommonExtensions.AUTO_EJECT, x, y);
        this.current = current;
        disabled = !current;
    }

    private void sendUpdate() {
        current = !current;
        disabled = !current;
        PacketHandler.INSTANCE.sendToServer(new PacketAutoEjectUpdate(guiObj.tileEntity.getPos(), current));
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if (isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
            guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y + HEIGHT, WIDTH, HEIGHT);
        } else {
            if(!disabled) {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y, WIDTH, HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, ORIGIN_X, ORIGIN_Y + HEIGHT * 2, WIDTH, HEIGHT);
            }
        }
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && getTooltip() != null) {
            displayTooltip(getTooltip(), xAxis, yAxis);
        }
    }

    @Override
    public String getTooltip() {
        return I18n.format("tooltip.electrolode.autoeject") + " " + StringUtils.getOnOffString(current);
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if (isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
            sendUpdate();
        }
    }
}
