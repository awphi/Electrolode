package ph.adamw.electrolode.gui.machine;

import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.gui.element.GuiEnergyBar;
import ph.adamw.electrolode.gui.element.GuiProgressBar;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureWrapper;

public class GuiPurifier extends GuiMachineBasic {
    public GuiPurifier(TileMachine tileEntity, BaseMachineContainer container) {
        super(tileEntity, container);
        guiElements.add(new GuiProgressBar(tileEntity, this, new TextureWrapper(getBackground(), 0, 165, 23, 23), 87, 31, EnumGuiDirection.RIGHT));
        guiElements.add(new GuiEnergyBar(tileEntity, this, 20, 20, 22, 46, true));
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(Electrolode.MODID, "textures/gui/purifier.png");
    }

    public void onAction(int id, int mbp) {}
}
