package ph.adamw.electrolode.channel;

import net.minecraft.util.IStringSerializable;

public enum ChannelTier implements IStringSerializable {
	WEAK(1000),
	STANDARD(16000),
	ADVANCED(64000),
	QUANTUM(512000);

	public static ChannelTier[] VALUES = values();

	private final int energyTransfer;

	ChannelTier(int energyTransfer) {
		this.energyTransfer = energyTransfer;
	}

	public int getEnergyTransfer() {
		return energyTransfer;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
