package ph.adamw.electrolode.block.properties;

import net.minecraft.util.EnumFacing;

import java.util.HashMap;
import java.util.Map;

public class Connections {
	private final Map<EnumFacing, Boolean> map = new HashMap<EnumFacing, Boolean>() {
		{
			for(EnumFacing i : EnumFacing.VALUES) {
				put(i, false);
			}
		}
	};

	public void setConnected(EnumFacing dir, boolean t) {
		map.put(dir, t);
	}

	public Boolean getConnected(EnumFacing dir) {
		return map.get(dir);
	}

	public Connections with(EnumFacing facing, boolean connected) {
		setConnected(facing, connected);
		return this;
	}
}
