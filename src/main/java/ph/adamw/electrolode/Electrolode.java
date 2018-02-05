package ph.adamw.electrolode;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ph.adamw.electrolode.proxy.CommonProxy;
import ph.adamw.electrolode.manager.MachineRecipeManager;
import ph.adamw.electrolode.manager.OreDictManager;

@net.minecraftforge.fml.common.Mod(modid = Electrolode.MODID, name = Electrolode.NAME, version = Electrolode.VERSION)
public class Electrolode {
    public static final String MODID = "electrolode";
    public static final String NAME = "Electrolode";
    public static final String VERSION = "0.1";

    @SidedProxy(clientSide = "ph.adamw.electrolode.proxy.ClientProxy", serverSide = "ph.adamw.electrolode.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Electrolode instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
        OreDictManager.registerEntries();
        OreDictManager.registerRecipes();
        MachineRecipeManager.registerMachineRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
