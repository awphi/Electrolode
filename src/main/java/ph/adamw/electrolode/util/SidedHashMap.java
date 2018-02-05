package ph.adamw.electrolode.util;

import net.minecraft.util.EnumFacing;
import ph.adamw.electrolode.block.EnumFaceRole;

import java.util.HashMap;

public class SidedHashMap extends HashMap<EnumFacing, EnumFaceRole> {

    public EnumFaceRole get(EnumFacing key) {
        if(this.containsKey(key)) {
            return super.get(key);
        } else {
            return EnumFaceRole.NONE;
        }
    }
}
