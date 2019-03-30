package ph.adamw.electrolode.gui.element;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.block.machine.TileTankedMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.networking.PacketHandler;
import ph.adamw.electrolode.networking.PacketSideConfigUpdate;
import ph.adamw.electrolode.util.GuiUtils;

import java.util.Arrays;
import java.util.List;

public class GuiButtonSideConfig extends GuiButtonElement {
    private EnumFaceRole current;
    private EnumFacing direction;
    private int containerIndex;

    public GuiButtonSideConfig(GuiBaseContainer gui, int x, int y, EnumFacing direction) {
        super(gui, -1, new ResourceLocation(Electrolode.MODID,"textures/gui/extensions/sideconfigbutton.png") , x, y, 16, 16);

        this.current = guiObj.tileEntity.faceMap.getRole(direction);
        this.direction = direction;
        this.containerIndex = guiObj.tileEntity.faceMap.getContainerIndex(direction);
        if (guiObj.tileEntity.isFaceDisabled(direction)) {
            disabled = true;
        }
    }

    private String getTooltipSpecific() {
        if(guiObj.tileEntity.isFaceDisabled(direction)) {
            return current.getLocalizedName() + " §l(" + I18n.format("tooltip.electrolode.locked") + ")";
        } else {
            return current.getLocalizedName();
        }
    }

    private void stepRole(int y) {
        current = EnumFaceRole.step(current, guiObj.tileEntity.potentialRoles, y);
        if(current == EnumFaceRole.INPUT_FLUID || current == EnumFaceRole.OUTPUT_FLUID) {
            containerIndex = 0;
        } else {
            containerIndex = -1;
        }
        sendState();
    }

    private void sendState() {
        PacketHandler.INSTANCE.sendToServer(new PacketSideConfigUpdate(guiObj.tileEntity.getPos(), direction, current, containerIndex));
    }

    private void stepIndex(int y) {
        int cap = -1;
        int min = -1;

        if(current == EnumFaceRole.INPUT_ITEM) {
            cap = ((TileInventoriedMachine) guiObj.tileEntity).getInputSlots();
        } else if(current == EnumFaceRole.OUTPUT_ITEM) {
            cap = ((TileInventoriedMachine) guiObj.tileEntity).getOutputSlots();
        } else if(current == EnumFaceRole.INPUT_FLUID) {
            cap = ((TileTankedMachine) guiObj.tileEntity).getInputTanks();
            min = 0;
        } else if(current == EnumFaceRole.OUTPUT_FLUID) {
            cap = ((TileTankedMachine) guiObj.tileEntity).getOutputTanks();
            min = 0;
        }

        containerIndex = GuiUtils.step(containerIndex, cap, min, y);
        sendState();
    }

    @Override
    public String getTooltip() {
        if(current != EnumFaceRole.NO_ROLE) {
            String unlocalized = "tooltip.electrolode." + current.name().toLowerCase().split("_")[1] + "index";
            if(containerIndex == -1) {
                return getTooltipSpecific() + "#n" + I18n.format(unlocalized) + " " + I18n.format("tooltip.electrolode.allindex");
            } else {
                return getTooltipSpecific() + "#n" + I18n.format(unlocalized) + " " + (containerIndex + 1);
            }
        } else {
            return getTooltipSpecific();
        }
    }

    @Override
    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(resource);

        if(!disabled) {
            if (!isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH)) {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, 0, current.ordinal() * HEIGHT, WIDTH, HEIGHT);
            } else {
                guiObj.drawTexturedModalRect(guiWidth + REL_X, guiHeight + REL_Y, WIDTH, current.ordinal() * HEIGHT, WIDTH, HEIGHT);
            }
        } else {
            guiObj.drawTexturedModalRect(guiWidth + REL_X * 2, guiHeight + REL_Y, WIDTH, current.ordinal() * HEIGHT, WIDTH, HEIGHT);
        }
    }

    @Override
    public void renderForeground(int xAxis, int yAxis) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && getTooltip() != null) {
            if(getTooltip().contains("#n")) {
                List<String> x = Arrays.asList(getTooltip().split("#n"));
                displayTooltips(x, xAxis, yAxis);
            } else {
                displayTooltip(getTooltip(), xAxis, yAxis);
            }
        }
    }

    @Override
    public void mouseClicked(int xAxis, int yAxis, int button) {
        if(isInRect(xAxis, yAxis, REL_X, REL_Y, HEIGHT, WIDTH) && !disabled) {
            if(button == 0) {
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    stepIndex(1);
                } else {
                    stepRole(1);
                }
            } else if(button == 1) {
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    stepIndex(-1);
                } else {
                    stepRole(-1);
                }
            }
        }
    }
}
