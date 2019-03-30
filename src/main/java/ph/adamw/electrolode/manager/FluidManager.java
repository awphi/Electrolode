package ph.adamw.electrolode.manager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import ph.adamw.electrolode.fluid.FluidBase;
import ph.adamw.electrolode.fluid.FluidSoftenedWater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FluidManager {
    private static HashMap<Class<? extends FluidBase>, FluidBase> fluidMap = new HashMap<>();

    public static FluidBase getFluid(Class<? extends FluidBase> e) {
        return fluidMap.get(e);
    }

    private static void registerFluid(Class<? extends FluidBase> e) {
        try {
            fluidMap.put(e, e.newInstance());
            FluidRegistry.registerFluid(getFluid(e));
            FluidRegistry.addBucketForFluid(getFluid(e));
        } catch (IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    public static void registerFluids() {
        registerFluid(FluidSoftenedWater.class);
    }

    public static Set<ResourceLocation> getFluidResources() {
        final HashSet<ResourceLocation> set = new HashSet<>();

        for(FluidBase i : fluidMap.values()) {
            set.add(i.getStill());
        }

        return set;
    }
}
