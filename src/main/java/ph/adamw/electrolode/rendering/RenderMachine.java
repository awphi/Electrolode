package ph.adamw.electrolode.rendering;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

// Base block is not drawn by this class, this just manages the IO overlays
public class RenderMachine extends TileEntitySpecialRenderer<TileBaseMachine> {
	@Override
	public boolean isGlobalRenderer(TileBaseMachine tileEntity) {
		return false;
	}

	@Override
	public void render(TileBaseMachine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//TODO render the face overlays based on the face map
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
