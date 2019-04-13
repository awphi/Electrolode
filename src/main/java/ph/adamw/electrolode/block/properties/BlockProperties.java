package ph.adamw.electrolode.block.properties;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import ph.adamw.electrolode.block.machine.core.FaceMapProperty;
import ph.adamw.electrolode.channel.ChannelTier;

public class BlockProperties {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final FaceMapProperty FACE_MAP = new FaceMapProperty();
	public static final ConnectionsProperty CONNECTIONS = new ConnectionsProperty();
	public static final PropertyEnum<ChannelTier> CHANNEL_TIER = PropertyEnum.create("channeltier", ChannelTier.class);
}
