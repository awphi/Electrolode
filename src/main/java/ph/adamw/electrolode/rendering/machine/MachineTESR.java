package ph.adamw.electrolode.rendering.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.property.IExtendedBlockState;
import ph.adamw.electrolode.block.machine.BlockMachine;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class MachineTESR extends FastTESR<TileBaseMachine> {
	@Override
	public void renderTileEntityFast(TileBaseMachine te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		final BlockPos pos = te.getPos();
		final IExtendedBlockState state = ((IExtendedBlockState) getWorld().getBlockState(pos)).withProperty(BlockMachine.FACEMAP_PROP, te.faceMap);
		final IBakedModel model =
				MachineModel.MODEL.bake(MachineModel.MODEL.getDefaultState(), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());

		buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());

		//TODO fix the particles having no texture
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), model, state, pos, buffer, true);
	}
}
