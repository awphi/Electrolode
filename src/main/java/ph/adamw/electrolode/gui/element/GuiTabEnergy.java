package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.tile.machine.core.TileInventoriedGenerator;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.energy.ElectroEnergyProducer;
import ph.adamw.electrolode.gui.GuiBaseContainer;

public class GuiTabEnergy extends GuiTab {
    public GuiTabEnergy(GuiBaseContainer gui, TileMachine e) {
        //Default width, height, ext width, ext height & left aligned, y offset = +5 from top
        super(new ResourceLocation(Electrolode.MODID, "textures/gui/extensions/energytab.png"), gui, e, 5, false);
    }

    public String getTooltip() {
        String top = I18n.format("tooltip.electrolode.maxenergyusage")  + " " + tileEntity.getEnergyUsage() + " RF/t";
        if(tileEntity.getEnergy() instanceof ElectroEnergyProducer) {
            top = I18n.format("tooltip.electrolode.energyoutput") + " " + ((TileInventoriedGenerator) tileEntity).getEnergyOutput() + " RF/t";
        }

        return top + "#n" + I18n.format("tooltip.electrolode.totalenergy") + " " + tileEntity.getEnergy().getEnergyStored() + " / " + tileEntity.getEnergy().getMaxEnergyStored() + " RF";
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {}

    public void mouseClicked(int xAxis, int yAxis, int button) {}
}
