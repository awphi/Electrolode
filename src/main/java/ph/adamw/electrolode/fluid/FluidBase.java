package ph.adamw.electrolode.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import ph.adamw.electrolode.Electrolode;

public class FluidBase extends Fluid {
    private Material material;

    public FluidBase(String name, Material material) {
        super(name, new ResourceLocation(Electrolode.MODID,"blocks/fluids/" + name + "_still"), new ResourceLocation(Electrolode.MODID,"blocks/fluids/" + name + "_flow"));
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
