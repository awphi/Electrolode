package ph.adamw.electrolode.rendering;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

public class BakedModelUtils {
	public static void putVertex(UnpackedBakedQuad.Builder builder, TextureAtlasSprite sprite, Vec3d normal, double x, double y, double z, float u, float v, VertexFormat format) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
				case POSITION:
					builder.put(e, (float)x, (float)y, (float)z, 1.0f);
					break;
				case COLOR:
					builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
					break;
				case UV:
					if (format.getElement(e).getIndex() == 0) {
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}

	public static BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, VertexFormat format) {
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		putVertex(builder, sprite, normal, v1.x, v1.y, v1.z, 0, 0, format);
		putVertex(builder, sprite, normal, v2.x, v2.y, v2.z, 0, 16, format);
		putVertex(builder, sprite, normal, v3.x, v3.y, v3.z, 16, 16, format);
		putVertex(builder, sprite, normal, v4.x, v4.y, v4.z, 16, 0, format);
		return builder.build();
	}

	public static BakedQuad generateOffsetDirectionalQuad(EnumFacing facing, TextureAtlasSprite sprite, VertexFormat format, double d) {
		switch (facing) {
			// Following comments describe the original quads which describe the exact same faces but with the texture flipped upside down

			//case UP: return BakedModelUtils.createQuad(new Vec3d(0d + d, 1d - d, 0d + d), new Vec3d(0d + d, 1d - d, 1d - d), new Vec3d(1d - d, 1d - d, 1d - d), new Vec3d(1d - d, 1d - d, 0d + d), sprite, format);
			case UP: return BakedModelUtils.createQuad(new Vec3d(1d - d, 1d - d, 1d - d), new Vec3d(1d - d, 1d - d, 0d + d), new Vec3d(0d + d, 1d - d, 0d + d), new Vec3d(0d + d, 1d - d, 1d - d), sprite, format);

			//case DOWN: return BakedModelUtils.createQuad(new Vec3d(1d - d, 0d + d, 0d + d), new Vec3d(1d - d, 0d + d, 1d - d), new Vec3d(0d + d, 0d + d, 1d - d), new Vec3d(0d + d, 0d + d, 0d + d), sprite, format);
			case DOWN: return BakedModelUtils.createQuad(new Vec3d(0d + d, 0d + d, 1d - d), new Vec3d(0d + d, 0d + d, 0d + d), new Vec3d(1d - d, 0d + d, 0d + d), new Vec3d(1d - d, 0d + d, 1d - d), sprite, format);

			//case WEST: return BakedModelUtils.createQuad(new Vec3d(0d + d, 0d + d, 1d - d), new Vec3d(0d + d, 1d - d, 1d - d), new Vec3d(0d + d, 1d - d, 0d + d), new Vec3d(0d + d, 0d + d, 0d + d), sprite, format);
			case WEST: return BakedModelUtils.createQuad(new Vec3d(0d + d, 1d - d, 0d + d), new Vec3d(0d + d, 0d + d, 0d + d), new Vec3d(0d + d, 0d + d, 1d - d), new Vec3d(0d + d, 1d - d, 1d - d), sprite, format);

			//case EAST: return BakedModelUtils.createQuad(new Vec3d(1d - d, 0d + d, 0d + d), new Vec3d(1d - d, 1d - d, 0d + d), new Vec3d(1d - d, 1d - d, 1d - d), new Vec3d(1d - d, 0d + d, 1d - d), sprite, format);
			case EAST: return BakedModelUtils.createQuad( new Vec3d(1d - d, 1d - d, 1d - d), new Vec3d(1d - d, 0d + d, 1d - d), new Vec3d(1d - d, 0d + d, 0d + d), new Vec3d(1d - d, 1d - d, 0d + d), sprite, format);

			//case NORTH: return BakedModelUtils.createQuad(new Vec3d(0d + d, 0d + d, 0d + d), new Vec3d(0d + d, 1d - d, 0d + d), new Vec3d(1d - d, 1d - d, 0d + d), new Vec3d(1d - d, 0d + d, 0d + d), sprite, format);
			case NORTH: return BakedModelUtils.createQuad(new Vec3d(1d - d, 1d - d, 0d + d), new Vec3d(1d - d, 0d + d, 0d + d), new Vec3d(0d + d, 0d + d, 0d + d), new Vec3d(0d + d, 1d - d, 0d + d), sprite, format);

			//case SOUTH: return BakedModelUtils.createQuad(new Vec3d(1d - d, 0d + d, 1d - d), new Vec3d(1d - d, 1d - d, 1d - d), new Vec3d(0d + d, 1d - d, 1d - d), new Vec3d(0d + d, 0d + d, 1d - d), sprite, format);
			case SOUTH: return BakedModelUtils.createQuad(new Vec3d(0d + d, 1d - d, 1d - d), new Vec3d(0d + d, 0d + d, 1d - d), new Vec3d(1d - d, 0d + d, 1d - d), new Vec3d(1d - d, 1d - d, 1d - d), sprite, format);
		}

		return null;
	}

	public static BakedQuad generateDirectionalQuad(EnumFacing facing, TextureAtlasSprite sprite, VertexFormat format) {
		return generateOffsetDirectionalQuad(facing, sprite, format, 0d);
	}
}
