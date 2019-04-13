package ph.adamw.electrolode.block.machine.core;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;

public class ConnectionsProperty implements IUnlistedProperty<Connections> {
	@Override
	public String getName() {
		return "connections";
	}

	@Override
	public boolean isValid(Connections value) {
		for(EnumFacing i : EnumFacing.VALUES) {
			if(value.getConnected(i) == null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Class<Connections> getType() {
		return Connections.class;
	}

	@Override
	public String valueToString(Connections value) {
		return value.toString();
	}
}
