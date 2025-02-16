package ph.adamw.electrolode.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ph.adamw.electrolode.Config;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.manager.FluidManager;
import ph.adamw.electrolode.manager.ItemManager;
import ph.adamw.electrolode.gui.GuiProxy;
import ph.adamw.electrolode.networking.PacketHandler;

import java.io.File;

import static ph.adamw.electrolode.Electrolode.instance;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "electrolode.cfg"));
        Config.readConfig();

        FluidManager.registerFluids();

        PacketHandler.registerMessages(Electrolode.MODID.toUpperCase());
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        // Register blocks also registers their associated TEs
        BlockManager.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemManager.registerItems(event.getRegistry());
        BlockManager.registerItemBlocks(event.getRegistry());
    }
}