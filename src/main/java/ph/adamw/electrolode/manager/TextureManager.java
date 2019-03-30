package ph.adamw.electrolode.manager;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ph.adamw.electrolode.block.EnumFaceRole;

public class TextureManager {
	public static void registerSprite(ResourceLocation loc) {
		Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(loc);
	}

	public static void registerTextures() {
		for(ResourceLocation resourceLocation : FluidManager.getFluidResources()) {
			registerSprite(resourceLocation);
		}

		for(EnumFaceRole role : EnumFaceRole.values()) {
			registerSprite(role.resolveResourceLocation());
		}
	}
}
