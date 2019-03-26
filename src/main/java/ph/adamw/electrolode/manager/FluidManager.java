package ph.adamw.electrolode.manager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.fluid.BlockFluidBase;
import ph.adamw.electrolode.fluid.FluidBase;
import ph.adamw.electrolode.fluid.FluidSoftenedWater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FluidManager {
    private static List<BlockFluidBase> fluidBlocks = new ArrayList<>();
    private static HashMap<Class<? extends FluidBase>, FluidBase> fluidMap = new HashMap<>();

    public static FluidBase getFluid(Class<? extends FluidBase> e) {
        return fluidMap.get(e);
    }

    private static void registerFluid(Class<? extends FluidBase> e, boolean placeable) {
        try {
            fluidMap.put(e, e.newInstance());
            FluidRegistry.registerFluid(getFluid(e));
            FluidRegistry.addBucketForFluid(getFluid(e));

            registerFluidBlock(getFluid(e), getFluid(e).getMaterial(), placeable);
        } catch (IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    public static void registerFluidBlocks(IForgeRegistry<Block> registry) {
        for(BlockFluidBase i : fluidBlocks) {
            registry.register(i);
        }
    }

    private static void registerFluidBlock(Fluid e, Material mat, boolean placeable) {
        fluidBlocks.add(new BlockFluidBase(e, mat, placeable));
    }

    public static void renderFluids() {
        for(BlockFluidBase i : fluidBlocks) {
            i.render();
        }
    }

    public static void registerFluids() {
        registerFluid(FluidSoftenedWater.class, false);
    }
}
