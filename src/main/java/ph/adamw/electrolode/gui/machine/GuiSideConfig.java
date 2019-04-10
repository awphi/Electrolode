package ph.adamw.electrolode.gui.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockProperties;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.gui.GuiBaseContainer;
import ph.adamw.electrolode.gui.element.GuiButtonEject;
import ph.adamw.electrolode.gui.element.GuiButtonSideConfig;
import ph.adamw.electrolode.gui.element.GuiButtonElement;
import ph.adamw.electrolode.networking.PacketGuiRequest;
import ph.adamw.electrolode.networking.PacketHandler;
import ph.adamw.electrolode.util.GuiUtils;

public class GuiSideConfig extends GuiBaseContainer {
    public GuiSideConfig(TileMachine te) {
        super(te, new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn) {
                return false;
            }
        });
        EnumFacing facing = tileEntity.getState().getValue(BlockProperties.FACING);

        //Top
        guiElements.add(new GuiButtonSideConfig(this, 64, 22, EnumFacing.UP));
        //Bottom
        guiElements.add(new GuiButtonSideConfig(this, 64, 58, EnumFacing.DOWN));
        //Front
        guiElements.add(new GuiButtonSideConfig(this, 64, 40, facing));
        facing = facing.rotateY();
        //Left
        guiElements.add(new GuiButtonSideConfig(this, 46, 40, facing));
        facing = facing.rotateY();
        //Back
        guiElements.add(new GuiButtonSideConfig(this, 82, 58, facing));
        facing = facing.rotateY();
        //Right
        guiElements.add(new GuiButtonSideConfig(this, 82, 40, facing));

        //Back button
        guiElements.add(new GuiButtonElement(this, 0, GuiUtils.CommonExtensions.BACK, 6, 6));

        //Auto eject button
        guiElements.add(new GuiButtonEject(this, 122, 6, tileEntity.autoEject));
    }

    public int getHeight() {
        return 96;
    }

    public int getWidth() {
        return 144;
    }

    public String getUnlocalizedTitle() {
        return "gui.electrolode.sideconfig.name";
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(Electrolode.MODID, "textures/gui/sideconfig.png");
    }

    public void onAction(int id, int mbp) {
        if(mbp == 0) {
            BlockPos pos = tileEntity.getPos();
            switch (id) {
                case 0: PacketHandler.INSTANCE.sendToServer(new PacketGuiRequest(tileEntity.getGuiId(), mc.player, pos));
            }
        }
    }
}
