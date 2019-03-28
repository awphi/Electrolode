package ph.adamw.electrolode.rendering;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.opengl.GL11;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import java.util.function.Function;

import static net.minecraft.client.Minecraft.*;

public class MachineTESR extends FastTESR<TileBaseMachine> {
	@Override
	public void renderTileEntityFast(TileBaseMachine te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		buffer.setTranslation(x, y, z);

		IBlockState state = te.getWorld().getBlockState(te.getPos());
		IBlockState ext = te.getWorld().getBlockState(te.getPos()).getBlock().getExtendedState(state, te.getWorld(), te.getPos());

		BlockRendererDispatcher dispatcher = getMinecraft().getBlockRendererDispatcher();
		IBakedModel model = dispatcher.getModelForState(state);
		dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, ext, te.getPos(), buffer, true);
	}
}
