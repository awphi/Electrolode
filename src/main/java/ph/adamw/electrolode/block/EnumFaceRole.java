package ph.adamw.electrolode.block;

import net.minecraft.client.resources.I18n;

import java.util.HashMap;

public enum EnumFaceRole {
    NONE (0, "enum.electrolode.facerole.none"),
    INPUT(1, "enum.electrolode.facerole.input"),
    OUTPUT(2, "enum.electrolode.facerole.output");

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

    public static EnumFaceRole next(EnumFaceRole e) {
        return step(e, 1);
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
