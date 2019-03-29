package ph.adamw.electrolode.block;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumFaceRole {
    NONE ("enum.electrolode.facerole.none"),
    INPUT_ITEM("enum.electrolode.facerole.input"),
    OUTPUT_ITEM("enum.electrolode.facerole.output"),
    INPUT_FLUID("enum.electrolode.facerole.inputfluid"),
    OUTPUT_FLUID("enum.electrolode.facerole.outputfluid");

    private String unlocalizedName;

    private static final Map<EnumFaceRole, ResourceLocation> resourceLocationCache = new HashMap<>();

    static EnumFaceRole[] roles = EnumFaceRole.values();
    private static HashMap<Integer, EnumFaceRole> map = new HashMap<>();

    static {
        for (EnumFaceRole eenum : EnumFaceRole.values()) {
            map.put(eenum.ordinal(), eenum);
        }
    }

    EnumFaceRole(String u) {
        unlocalizedName = u;
    }

    private static EnumFaceRole step(EnumFaceRole e, int step) {
        if(e == null) {
            return roles[0];
        }

        int c = e.ordinal() + step;

        if(c < 0) {
            c = roles.length - 1;
        } else {
            c %= roles.length;
        }

        return roles[c];
    }

    public static EnumFaceRole step(EnumFaceRole e, @Nonnull List<EnumFaceRole> pool, int step) {
        e = EnumFaceRole.step(e, step);

        while(!pool.contains(e)) {
            e = EnumFaceRole.step(e, step);
        }

        return e;
    }

    public static EnumFaceRole getRole(int i) {
        return map.get(i);
    }

    public String getLocalizedName() {
        return I18n.format(unlocalizedName);
    }

    public ResourceLocation resolveResourceLocation() {
        if(!resourceLocationCache.containsKey(this)) {
            resourceLocationCache.put(this, new ResourceLocation(Electrolode.MODID,"blocks/machine/" + name().toLowerCase()));
        }

        return resourceLocationCache.get(this);
    }
}
