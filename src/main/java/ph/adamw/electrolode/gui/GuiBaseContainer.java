package ph.adamw.electrolode.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.machines.TileBaseMachine;
import ph.adamw.electrolode.gui.extension.GuiExtension;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GuiBaseContainer extends GuiContainer {
    public TileBaseMachine tileEntity;
    public Set<GuiExtension> guiExtensions = new HashSet<>();

    public GuiBaseContainer(TileBaseMachine tileEntity, Container container) {
        super(container);

        xSize = getWidth();
        ySize = getHeight();
        this.tileEntity = tileEntity;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract ResourceLocation getBackground();

    public abstract void onAction(int id, int mouseButton);

    public abstract String getUnlocalizedTitle();

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(getBackground());

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        String title = I18n.format(getUnlocalizedTitle());
        int x = guiLeft + ((getWidth() / 2) - (mc.fontRenderer.getStringWidth(title) / 2));
        mc.fontRenderer.drawString(title, x, guiTop + 6, 0x404040);

        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;

        for(GuiExtension element : guiExtensions) {
            GlStateManager.color(1f, 1f, 1f, 1f);
            element.renderBackground(xAxis, yAxis, guiLeft, guiTop);
        }
        mc.getTextureManager().bindTexture(getBackground());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;

        for(GuiExtension extension : guiExtensions) {
            extension.renderForeground(xAxis, yAxis);
        }
    }



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    public void displayTooltip(String s, int x, int y) {
        drawHoveringText(s, x, y);
    }

    public void displayTooltips(List<String> list, int xAxis, int yAxis) {
        drawHoveringText(list, xAxis, yAxis);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;

        for(GuiExtension ext : guiExtensions) {
            ext.preMouseClicked(xAxis, yAxis, button);
        }
        super.mouseClicked(mouseX, mouseY, button);

        for(GuiExtension ext : guiExtensions) {
            ext.mouseClicked(xAxis, yAxis, button);
        }
    }
}
