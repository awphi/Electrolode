package ph.adamw.electrolode.block;

import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

public enum EnumFaceRole {
    NONE (0, "enum.electrolode.facerole.none"),
    INPUT_ITEM(1, "enum.electrolode.facerole.input"),
    OUTPUT_ITEM(2, "enum.electrolode.facerole.output"),
    INPUT_FLUID(3, "enum.electrolode.facerole.inputfluid"),
    OUTPUT_FLUID(4, "enum.electrolode.facerole.outputfluid");

    int phase;
    private String unlocalizedName;

    static EnumFaceRole[] roles = EnumFaceRole.values();
    private static HashMap<Integer, EnumFaceRole> map = new HashMap<>();

    static {
        for (EnumFaceRole eenum : EnumFaceRole.values()) {
            map.put(eenum.phase, eenum);
        }
    }

    EnumFaceRole(int x, String u) {
        this.phase = x;
        unlocalizedName = u;
    }

    public int getValue() {
        return this.phase;
    }

    private static EnumFaceRole step(EnumFaceRole e, int step) {
        if(e == null) {
            return roles[0];
        }
        int nextIndex = e.ordinal() + step;
        nextIndex = nextIndex % roles.length;
        return roles[nextIndex];
    }

    public static EnumFaceRole next(EnumFaceRole e, @Nonnull List<EnumFaceRole> pool) {
        e = EnumFaceRole.step(e, 1);
        while(!pool.contains(e)) {
            e = EnumFaceRole.step(e, 1);
        }
        return e;
    }

    public static EnumFaceRole previous(EnumFaceRole e) {
        return step(e, -1);
    }

    public static EnumFaceRole getRole(int i) {
        return map.get(i);
    }

    public String getLocalizedName() {
        return I18n.format(unlocalizedName);
    }
}
