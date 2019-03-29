package ph.adamw.electrolode.util;

import net.minecraft.util.EnumFacing;
import ph.adamw.electrolode.block.EnumFaceRole;

import java.util.HashMap;

public class SidedHashMap extends HashMap<EnumFacing, SidedHashMap.SidedMapValue> {

    public SidedMapValue get(EnumFacing key) {
        if(this.containsKey(key)) {
            return super.get(key);
        } else {
            return new SidedMapValue(EnumFaceRole.NONE, -1);
        }
    }

    public EnumFaceRole getRole(EnumFacing key) {
        if(this.containsKey(key)) {
            return super.get(key).role;
        } else {
            return EnumFaceRole.NONE;
        }
    }

    public int getContainerIndex(EnumFacing key) {
        if(this.containsKey(key)) {
            return super.get(key).containerIndex;
        } else {
            return -1;
        }
    }

    public void put(EnumFacing key, EnumFaceRole role, int index) {
        super.put(key, new SidedMapValue(role, index));
    }

    /**
     * Sided map value
     *  - containerIndex = the slot this face has access to
     *  Wildcards:
     *      -1 = All slots of that EnumFaceRole
     */
    public class SidedMapValue {
        private int containerIndex;
        private EnumFaceRole role;

        SidedMapValue(EnumFaceRole role, int index) {
            this.role = role;
            this.containerIndex = index;
        }

        public EnumFaceRole getRole() {
            return role;
        }
    }
}
