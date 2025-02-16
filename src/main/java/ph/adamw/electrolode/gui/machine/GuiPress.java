package ph.adamw.electrolode.gui.machine;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.gui.element.GuiEnergyBar;
import ph.adamw.electrolode.gui.element.GuiProgressBar;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureWrapper;

public class GuiPress extends GuiMachine {
    public GuiPress(TileMachine tileEntity, Container container) {
        super(tileEntity, container);
        guiElements.add(new GuiProgressBar(tileEntity, this, new TextureWrapper(getBackground(), 16, 165, 16, 16), 61, 19, EnumGuiDirection.RIGHT));
        guiElements.add(new GuiProgressBar(tileEntity, this, new TextureWrapper(getBackground(), 0, 165, 16, 16), 137, 19, EnumGuiDirection.LEFT));
        guiElements.add(new GuiEnergyBar(tileEntity, this, 20, 20, 22, 46, true));
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(Electrolode.MODID, "textures/gui/press.png");
    }

    public void onAction(int id, int mbp) {}
}
