package ph.adamw.electrolode;

import net.minecraftforge.fluids.Fluid;
import ph.adamw.electrolode.fluid.FluidBase;
import ph.adamw.electrolode.fluid.FluidSoftenedWater;
import ph.adamw.electrolode.manager.FluidManager;

/**
 * Created by adamhodson on 17/02/2018.
 */
public class ElectrolodeFluids {
    public static final Fluid SOFTENED_WATER = getRegisteredFluid(FluidSoftenedWater.class);

    private static Fluid getRegisteredFluid(Class<? extends FluidBase> e) {
        return FluidManager.getFluid(e);
    }
}
