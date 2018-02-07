package ph.adamw.electrolode.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.element.GuiTabEnergy;
import ph.adamw.electrolode.gui.element.GuiTabSideConfig;

@SideOnly(Side.CLIENT)
public abstract class GuiMachineBasic extends GuiBaseContainer {
    GuiMachineBasic(TileBaseMachine tileEntity, Container container) {
        super(tileEntity, container);
        guiElements.add(new GuiTabEnergy(this, tileEntity));
        guiElements.add(new GuiTabSideConfig(this, tileEntity));
    }

    public String getUnlocalizedTitle() {
        return tileEntity.getWorld().getBlockState(tileEntity.getPos()).getBlock().getUnlocalizedName() + ".name";
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        mc.fontRenderer.drawString(I18n.format("gui.electrolode.inventory.name"), guiLeft + 8, guiTop + (ySize - 92), 0x404040);
    }

    public int getHeight() {
        return 165;
    }

    public int getWidth() {
        return 175;
    }
}
