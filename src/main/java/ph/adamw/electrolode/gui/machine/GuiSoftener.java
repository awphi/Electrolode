package ph.adamw.electrolode.gui.machine;

import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.block.machine.TileTankedMachine;
import ph.adamw.electrolode.gui.element.GuiEnergyBar;
import ph.adamw.electrolode.gui.element.GuiProgressBar;
import ph.adamw.electrolode.gui.element.GuiTank;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureWrapper;

public class GuiSoftener extends GuiMachineBasic {
    public GuiSoftener(TileMachine tileEntity, BaseMachineContainer container) {
        super(tileEntity, container);
        TileTankedMachine te = (TileTankedMachine) tileEntity;
        guiElements.add(new GuiEnergyBar(tileEntity, this, 20, 21, 22, 45, true));
        guiElements.add(new GuiTank(te.getFluidTank(0, EnumFaceRole.INPUT_FLUID), this, 78, 20, 16, 46));
        guiElements.add(new GuiTank(te.getFluidTank(0, EnumFaceRole.OUTPUT_FLUID), this, 137, 20, 24, 46));
        guiElements.add(new GuiProgressBar(te, this, new TextureWrapper(getBackground(), 0, 165, 33, 44), 99, 21, EnumGuiDirection.RIGHT));
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(Electrolode.MODID, "textures/gui/softener.png");
    }

    public void onAction(int id, int mbp) {}
}
