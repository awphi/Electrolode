package ph.adamw.electrolode.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ph.adamw.electrolode.block.machine.TileBaseMachine;
import ph.adamw.electrolode.block.machine.TileInventoriedMachine;
import ph.adamw.electrolode.inventory.BaseMachineContainer;
import ph.adamw.electrolode.manager.GuiManager;
import ph.adamw.electrolode.util.GuiUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GuiProxy implements IGuiHandler {
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        TileBaseMachine te = (TileBaseMachine) world.getTileEntity(pos);
        GuiEntry entry = GuiManager.getGuiEntry(ID);
        if(entry == null) {
            return null;
        }

        try {
            Constructor constructor = entry.container.getDeclaredConstructor(IInventory.class, TileInventoriedMachine.class);
            return constructor.newInstance(player.inventory, te);
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        TileBaseMachine te = (TileBaseMachine) world.getTileEntity(pos);
        GuiEntry entry = GuiManager.getGuiEntry(ID);
        if(entry == null) {
            return null;
        }

        try {
            Constructor constructor = entry.guiMachineBasic.getDeclaredConstructor(TileBaseMachine.class, BaseMachineContainer.class);
            Constructor cs = entry.container.getDeclaredConstructor(IInventory.class, TileInventoriedMachine.class);
            return constructor.newInstance(te, cs.newInstance(player.inventory, te));
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
