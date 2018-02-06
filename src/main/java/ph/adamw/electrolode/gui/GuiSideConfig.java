package ph.adamw.electrolode.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.gui.extension.GuiButtonEject;
import ph.adamw.electrolode.gui.extension.GuiButtonSideConfig;
import ph.adamw.electrolode.gui.extension.GuiExtensionButton;
import ph.adamw.electrolode.util.BlockUtils;
import ph.adamw.electrolode.util.GuiUtils;

public class GuiSideConfig extends GuiBaseContainer {
    public GuiSideConfig(TileBaseMachine te) {
        super(te, new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn) {
                return false;
            }
        });
        EnumFacing facing = tileEntity.getState().getValue(BlockBaseMachine.FACING);

        //Top
        guiExtensions.add(new GuiButtonSideConfig(this, 64, 22, EnumFacing.UP, tileEntity.faceMap.get(EnumFacing.UP), tileEntity.isFaceDisabled(EnumFacing.UP)));
        //Bottom
        guiExtensions.add(new GuiButtonSideConfig(this, 64, 58, EnumFacing.DOWN, tileEntity.faceMap.get(EnumFacing.DOWN), tileEntity.isFaceDisabled(EnumFacing.DOWN)));
        //Front
        guiExtensions.add(new GuiButtonSideConfig(this, 64, 40, facing, tileEntity.faceMap.get(facing), tileEntity.isFaceDisabled(facing)));
        facing = BlockUtils.getNextEnumFacing(facing);
        //Left
        guiExtensions.add(new GuiButtonSideConfig(this, 46, 40, facing, tileEntity.faceMap.get(facing), tileEntity.isFaceDisabled(facing)));
        facing = BlockUtils.getNextEnumFacing(facing);
        //Back
        guiExtensions.add(new GuiButtonSideConfig(this, 82, 58, facing, tileEntity.faceMap.get(facing), tileEntity.isFaceDisabled(facing)));
        facing = BlockUtils.getNextEnumFacing(facing);
        //Right
        guiExtensions.add(new GuiButtonSideConfig(this, 82, 40, facing, tileEntity.faceMap.get(facing), tileEntity.isFaceDisabled(facing)));

        //Back button
        guiExtensions.add(new GuiExtensionButton(this, 0, GuiUtils.CommonExtensions.BACK, 6, 6));

        //Auto eject button
        guiExtensions.add(new GuiButtonEject(this, 122, 6, tileEntity.autoEject));
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

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public void onAction(int id, int mbp) {
        if(mbp == 0) {
            BlockPos pos = tileEntity.getPos();
            switch (id) {
                case 0:
                    mc.player.openGui(Electrolode.MODID, tileEntity.getGuiId(), mc.player.world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
