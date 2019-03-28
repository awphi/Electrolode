package ph.adamw.electrolode.rendering.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockHorizontalDirectional;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BakedMachineModel implements IBakedModel {
	private final static Map<BlockBaseMachine, ModelResourceLocation> locationMap = new HashMap<>();
	private static final ResourceLocation BASE_LOC = new ResourceLocation(Electrolode.MODID, "blocks/machine/base");

	private final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter;
	private final VertexFormat format;

	public BakedMachineModel(VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		this.bakedTextureGetter = bakedTextureGetter;
		this.format = format;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if(state == null) {
			return Collections.emptyList();
		}

		final List<BakedQuad> quads = new ArrayList<>();
		ResourceLocation faceLocation = null;

		if(state.getBlock() instanceof BlockBaseMachine) {
			faceLocation = new ResourceLocation(Electrolode.MODID, "blocks/machine/" + ((BlockBaseMachine) state.getBlock()).getBlockName() + "_front");
		}

		//TODO implement a facemap extended property into the machine block and then use that here to dynamically render io ports.

		TextureAtlasSprite sprite = bakedTextureGetter.apply(BASE_LOC);

		for(EnumFacing i : EnumFacing.VALUES) {
			if(i.equals(state.getValue(BlockHorizontalDirectional.FACING))) {
				sprite = bakedTextureGetter.apply(faceLocation);
			}

			quads.add(BakedModelUtils.generateDirectionalQuad(i, sprite, format));

			if(i.equals(state.getValue(BlockHorizontalDirectional.FACING))) {
				sprite = bakedTextureGetter.apply(BASE_LOC);
			}
		}

		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return bakedTextureGetter.apply(BASE_LOC);
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

	public static ModelResourceLocation getModelResourceLocation(BlockBaseMachine block) {
		if(!locationMap.containsKey(block)) {
			locationMap.put(block, new ModelResourceLocation(Electrolode.MODID + ":machine_" + block.getBlockName()));
		}

		return locationMap.get(block);
	}
}
