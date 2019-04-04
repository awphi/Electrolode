package ph.adamw.electrolode.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ph.adamw.electrolode.tile.machine.core.TileMachine;
import ph.adamw.electrolode.tile.machine.core.TileInventoriedMachine;
import ph.adamw.electrolode.manager.GuiManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GuiProxy implements IGuiHandler {
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        TileMachine te = (TileMachine) world.getTileEntity(pos);
        GuiEntry entry = GuiManager.getGuiEntry(ID);
        if(entry == null) {
            return null;
        }

        try {
            Constructor constructor = entry.container.getDeclaredConstructor(IInventory.class, TileInventoriedMachine.class);
            constructor.setAccessible(true);
            return constructor.newInstance(player.inventory, te);
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        TileMachine te = (TileMachine) world.getTileEntity(pos);
        GuiEntry entry = GuiManager.getGuiEntry(ID);
        if(entry == null) {
            return null;
        }

        try {
            Constructor constructor = entry.guiMachineBasic.getDeclaredConstructor(TileMachine.class, Container.class);
            constructor.setAccessible(true);
            Constructor containerConstructor = entry.container.getDeclaredConstructor(IInventory.class, TileInventoriedMachine.class);
            containerConstructor.setAccessible(true);
            return constructor.newInstance(te, containerConstructor.newInstance(player.inventory, te));
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
