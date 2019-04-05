package ph.adamw.electrolode.block;

import net.minecraft.block.properties.PropertyDirection;
import ph.adamw.electrolode.block.machine.core.FaceMapProperty;

public class BlockProperties {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final FaceMapProperty FACEMAP = new FaceMapProperty();
}
