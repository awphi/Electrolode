package ph.adamw.electrolode;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by adamhodson on 17/02/2018.
 */
public class ModFluids {
    public static final Fluid SOFTENED_WATER = getRegisteredFluid("softenedWater");


    private static Fluid getRegisteredFluid(String x) {
        return FluidRegistry.getFluid(x);
    }
}
