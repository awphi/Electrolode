package ph.adamw.electrolode.rendering.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.opengl.GL11;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import java.util.function.Function;

import static net.minecraft.client.Minecraft.*;

public class MachineTESR extends FastTESR<TileBaseMachine> {
	@Override
	public void renderTileEntityFast(TileBaseMachine te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		BlockPos pos = te.getPos();
		buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());
		BlockRendererDispatcher dispatcher = getMinecraft().getBlockRendererDispatcher();
		IBakedModel model = dispatcher.getModelForState(state);
		dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, state, te.getPos(), buffer, true);
	}
}
