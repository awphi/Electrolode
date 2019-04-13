package ph.adamw.electrolode.rendering.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.property.IExtendedBlockState;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.tile.machine.core.TileMachine;

public class MachineTESR extends FastTESR<TileMachine> {
	private static final BakedMachineModel MODEL = new BakedMachineModel();

	@Override
	public void renderTileEntityFast(TileMachine te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		final BlockPos pos = te.getPos();
		final IExtendedBlockState state = ((IExtendedBlockState) getWorld().getBlockState(pos)).withProperty(BlockProperties.FACE_MAP, te.faceMap);

		buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());

		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), MODEL, state, pos, buffer, true);
	}
}
