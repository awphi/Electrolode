package ph.adamw.electrolode.util;

import net.minecraft.util.ResourceLocation;

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
