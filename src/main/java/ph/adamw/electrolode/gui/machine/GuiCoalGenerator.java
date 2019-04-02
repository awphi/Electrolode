package ph.adamw.electrolode.gui.machine;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.TileMachine;
import ph.adamw.electrolode.gui.element.GuiEnergyBar;
import ph.adamw.electrolode.gui.element.GuiProgressBar;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureWrapper;

public class GuiCoalGenerator extends GuiMachine {
	public GuiCoalGenerator(TileMachine tileEntity, Container container) {
		super(tileEntity, container);
		guiElements.add(new GuiEnergyBar(tileEntity, this, 133, 20, 22, 46, true));
		guiElements.add(new GuiProgressBar(tileEntity, this, new TextureWrapper(getBackground(), 0, 165, 89, 24), 40, 31, EnumGuiDirection.RIGHT));
	}

	@Override
	public ResourceLocation getBackground() {
		return new ResourceLocation(Electrolode.MODID, "textures/gui/coalgenerator.png");
	}

	@Override
	public void onAction(int id, int mouseButton) {}
}
