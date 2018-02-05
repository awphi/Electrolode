package ph.adamw.electrolode.util;

import net.minecraft.client.resources.I18n;

public class StringUtils {
    public static String getOnOffString(boolean b) {
        if(b) return I18n.format("tooltip.electrolode.on");
        else return I18n.format("tooltip.electrolode.on");
    }
}
