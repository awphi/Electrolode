package ph.adamw.electrolode;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import ph.adamw.electrolode.proxy.CommonProxy;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_MACHINE = "machines";

    public static boolean autoEjectDefault = true;

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initMachineConfig(cfg);
        } catch (Exception e1) {
            Electrolode.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
    }

    private static void initMachineConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_MACHINE, "Machine configuration");
        autoEjectDefault = cfg.getBoolean("autoEjectDefault", CATEGORY_MACHINE, autoEjectDefault, "Will machines eject automatically be default");
    }
}