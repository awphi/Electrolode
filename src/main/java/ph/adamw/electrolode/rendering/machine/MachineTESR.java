package ph.adamw.electrolode.rendering.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.property.IExtendedBlockState;
import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.tile.machine.core.TileMachine;

public class MachineTESR extends FastTESR<TileMachine> {
	@Override
	public void renderTileEntityFast(TileMachine te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		final BlockPos pos = te.getPos();
		final IExtendedBlockState state = ((IExtendedBlockState) getWorld().getBlockState(pos))
				.withProperty(BlockMachine.FACEMAP_PROP, te.faceMap);

		BakedMachineModel.MODEL.setBakedTextureGetter(ModelLoader.defaultTextureGetter());
		BakedMachineModel.MODEL.setFormat(DefaultVertexFormats.ITEM);
		final IBakedModel model = BakedMachineModel.MODEL;

		buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());

		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), model, state, pos, buffer, true);
	}
}
