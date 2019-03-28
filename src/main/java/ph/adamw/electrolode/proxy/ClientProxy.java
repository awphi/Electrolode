package ph.adamw.electrolode.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ph.adamw.electrolode.manager.BlockManager;
import ph.adamw.electrolode.manager.ItemManager;
import ph.adamw.electrolode.rendering.machine.MachineModelLoader;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        MachineModelLoader.registerMachineTextures();
        ModelLoaderRegistry.registerLoader(new MachineModelLoader());
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ItemManager.initModels();
        BlockManager.initModels();
        BlockManager.initItemModels();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}