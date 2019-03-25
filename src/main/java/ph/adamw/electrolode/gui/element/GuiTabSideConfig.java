package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.gui.machine.GuiSideConfig;

public class GuiTabSideConfig extends GuiTab {
    public GuiTabSideConfig(GuiBaseContainer gui, TileBaseMachine e) {
        //Default width, height, ext width, ext height & left aligned, y offset = +5 from centre
        super(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/sideconfigtab.png"), gui, e, 5);
    }

    public String getTooltip() {
        return I18n.format("tooltip.electrolode.configtab");
    }

    @Override
    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {
        if(button == 0) {
            if(isInRect(xAxis, yAxis, guiObj.getWidth(), yOffset, normalHeight, normalWidth)) {
                mc.displayGuiScreen(new GuiSideConfig(tileEntity));
            }
        }
    }
}
