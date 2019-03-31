package ph.adamw.electrolode.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.gui.GuiBaseContainer;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiElement {
    public static Minecraft mc = Minecraft.getMinecraft();
    public ResourceLocation resource;
    public GuiBaseContainer guiObj;

    public String tooltip;

    GuiElement(GuiBaseContainer gui, ResourceLocation resource) {
        this.resource = resource;
        guiObj = gui;
    }

    public void mouseClickMove(int mouseX, int mouseY, int button, long ticks) {}

    public void mouseReleased(int x, int y, int type) {}

    public void mouseWheel(int x, int y, int delta) {}

    /* For rendering custom tooltips when mouse is with certain area */
    public abstract void renderForeground(int xAxis, int yAxis);

    /* Renders most everything else */
    public abstract void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight);

    public abstract String getTooltip();

    public abstract void preMouseClicked(int xAxis, int yAxis, int button);

    public abstract void mouseClicked(int xAxis, int yAxis, int button);

    void displayTooltip(String s, int xAxis, int yAxis) {
        guiObj.displayTooltip(s, xAxis, yAxis);
    }

    public void displayTooltips(List<String> list, int xAxis, int yAxis) {
        guiObj.displayTooltips(list, xAxis, yAxis);
    }

    boolean isInRect(int mouseX, int mouseY, int rectX, int rectY, int rectHeight, int rectWidth) {
        return ((mouseX >= rectX && mouseX <= rectX + rectWidth) && (mouseY >= rectY && mouseY <= rectY+rectHeight));
    }
}
