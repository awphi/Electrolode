package ph.adamw.electrolode.block.machine.core;

import net.minecraftforge.common.property.IUnlistedProperty;
import ph.adamw.electrolode.util.SidedHashMap;

public class FaceMapProperty implements IUnlistedProperty<SidedHashMap> {
	@Override
	public String getName() {
		return "facemap";
	}

	@Override
	public boolean isValid(SidedHashMap value) {
		return true;
	}

	@Override
	public Class<SidedHashMap> getType() {
		return SidedHashMap.class;
	}

	@Override
	public String valueToString(SidedHashMap value) {
		return value.toString();
	}
}
