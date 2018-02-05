package ph.adamw.electrolode.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.block.machines.TileBaseMachine;
import ph.adamw.electrolode.gui.extension.GuiEnergyBar;
import ph.adamw.electrolode.gui.extension.GuiProgressBar;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.gui.extension.GuiTabSideConfig;
import ph.adamw.electrolode.util.EnumGuiDirection;
import ph.adamw.electrolode.util.TextureHelper;

@SideOnly(Side.CLIENT)
public class GuiPurifier extends GuiMachineBasic {

    public GuiPurifier(TileBaseMachine tileEntity, BaseMachineContainer container) {
        super(tileEntity, container);
        guiExtensions.add(new GuiTabSideConfig(this, tileEntity));
        guiExtensions.add(new GuiProgressBar(tileEntity, this, new TextureHelper(getBackground(), 0, 165, 23, 23), 87, 32, EnumGuiDirection.RIGHT, I18n.format("tooltip.electrolode.purifierprogress")));
        guiExtensions.add(new GuiEnergyBar(tileEntity, this, 20, 20, 22, 46, true));
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(Electrolode.MODID, "textures/gui/purifier.png");
    }

    public void onAction(int id) {}
}
