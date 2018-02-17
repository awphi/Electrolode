package ph.adamw.electrolode.util;

import net.minecraft.util.ResourceLocation;

/**
 * Simple wrapper class for holding location information as well as a resource.
 *  - Intended for use in GUIs where TextureAtlasSprite isn't used
 */
public class TextureHelper {
    public int X_ORIGIN;
    public int Y_ORIGIN;
    public int HEIGHT;
    public int WIDTH;

    public ResourceLocation resource;

    public TextureHelper(ResourceLocation resourceLocation, int x, int y, int width, int height) {
        X_ORIGIN = x;
        Y_ORIGIN = y;

        HEIGHT = height;
        WIDTH = width;
        resource = resourceLocation;
    }
}
