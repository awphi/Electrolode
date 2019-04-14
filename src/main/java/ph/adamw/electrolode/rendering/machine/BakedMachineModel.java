package ph.adamw.electrolode.rendering.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.block.machine.core.BlockMachine;
import ph.adamw.electrolode.rendering.BakedModelUtils;
import ph.adamw.electrolode.util.SidedHashMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BakedMachineModel implements IBakedModel {
	private static final Function<ResourceLocation, TextureAtlasSprite> TEXTURE_GETTER = ModelLoader.defaultTextureGetter();
	private static final VertexFormat VERTEX_FORMAT = DefaultVertexFormats.ITEM;

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if(state == null) {
			return Collections.emptyList();
		}

		final List<BakedQuad> quads = new ArrayList<>();
		ResourceLocation faceLocation = null;
		SidedHashMap faceMap = null;

		if(state.getBlock() instanceof BlockMachine) {
			faceLocation = new ResourceLocation(Electrolode.MODID, "blocks/machine/" + ((BlockMachine) state.getBlock()).getBlockName() + "_front");

			if(state instanceof IExtendedBlockState) {
				faceMap = ((IExtendedBlockState) state).getValue(BlockProperties.FACE_MAP);
			}
		}

		for(EnumFacing i : EnumFacing.VALUES) {
			TextureAtlasSprite sprite = TEXTURE_GETTER.apply(EnumFaceRole.NO_ROLE.resolveResourceLocation());

			if(faceMap != null) {
				sprite = TEXTURE_GETTER.apply(faceMap.get(i).getRole().resolveResourceLocation());
			}

			//Overwrite it if it's the face since we don't draw IO ports on the face
			if(i.equals(state.getValue(BlockProperties.FACING))) {
				sprite = TEXTURE_GETTER.apply(faceLocation);
			}

			quads.add(BakedModelUtils.generateDirectionalQuad(i, sprite, VERTEX_FORMAT));
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
		return TEXTURE_GETTER.apply(EnumFaceRole.NO_ROLE.resolveResourceLocation());
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}
}
