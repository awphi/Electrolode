package ph.adamw.electrolode.fluid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

/**
 * Created by adamhodson on 17/02/2018.
 */
public class FluidSoftenedWater extends FluidBase {
    private static final Material SOFTENED_WATER = new MaterialLiquid(MapColor.LIGHT_BLUE);

    public FluidSoftenedWater() {
        super("softenedwater", SOFTENED_WATER);
    }
}
