package ph.adamw.electrolode.rendering.machine;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import ph.adamw.electrolode.block.EnumFaceRole;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class MachineModel implements IModel {
	public static final MachineModel MODEL = new MachineModel();
	public static final Set<ResourceLocation> machineTextures = new HashSet<>();

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new BakedMachineModel(format, bakedTextureGetter);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return new ImmutableSet.Builder<ResourceLocation>().addAll(machineTextures).build();
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	private static void registerBlocklessMachineTexture(ResourceLocation loc) {
		machineTextures.add(loc);
		System.out.println(Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(loc).toString());
	}

	public static void registerBlocklessMachineTextures() {
		for(EnumFaceRole role : EnumFaceRole.values()) {
			registerBlocklessMachineTexture(role.resolveResourceLocation());
		}
	}
}
