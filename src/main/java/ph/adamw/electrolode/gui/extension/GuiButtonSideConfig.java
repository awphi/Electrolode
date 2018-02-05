package ph.adamw.electrolode.gui.extension;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.networking.PacketHandler;
import ph.adamw.electrolode.networking.PacketSideConfigUpdate;

public class GuiButtonSideConfig extends GuiExtensionButton {
    private EnumFaceRole current;
    private EnumFacing direction;

    public GuiButtonSideConfig(GuiBaseContainer gui, int x, int y, EnumFacing direction, EnumFaceRole current) {
        super(gui, -1, new ResourceLocation(Electrolode.MODID,"textures/gui/extensions/sideconfigbutton.png") , x, y, 16, 16);
        if(current == null) {
            current = EnumFaceRole.next(current);
        }
        this.current = current;
        this.direction = direction;
    }

    private void incrementRole() {
        current = EnumFaceRole.next(current);
        PacketHandler.INSTANCE.sendToServer(new PacketSideConfigUpdate(guiObj.tileEntity.getPos(), direction, current));
    }

    @Override
    public String getTooltip() {
        return current.getUnlocalizedName();
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if(!isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
            guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, 0, current.getValue() * HEIGHT, WIDTH, HEIGHT);
        } else {
            guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, WIDTH , current.getValue()* HEIGHT, WIDTH, HEIGHT);
        }
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
            incrementRole();
        }
    }
}
