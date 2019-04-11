package ph.adamw.electrolode.tile;

import net.minecraft.util.ITickable;

public abstract class TileTickable extends TileUpdatable implements ITickable {
	private boolean toUpdate = false;

	public abstract void tick();

	@Override
	public void update() {
		tick();

		if(toUpdate) {
			super.markForUpdate();
			toUpdate = false;
		}
	}

	@Override
	public void markForUpdate() {
		toUpdate = true;
	}

}
