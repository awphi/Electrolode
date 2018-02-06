package ph.adamw.electrolode.gui.extension;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;

public class GuiTabEnergy extends GuiTab {
    public GuiTabEnergy(GuiBaseContainer gui, TileBaseMachine e) {
        //Default width, height, ext width, ext height & left aligned, y offset = +5 from top
        super(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/energytab.png"), gui, e, 5, false);
    }

    public String getTooltip() {
        return I18n.format("tooltip.electrolode.maxenergyusage")  + " " + tileEntity.getEnergyUsage() + " RF/t#n"
                + I18n.format("tooltip.electrolode.totalenergy") + " " + tileEntity.getEnergyStored() + " / " + tileEntity.getMaxEnergyStored() + " RF";
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {}
}
