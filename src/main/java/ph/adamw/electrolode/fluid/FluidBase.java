package ph.adamw.electrolode.fluid;

import lombok.Getter;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import ph.adamw.electrolode.Electrolode;

public class FluidBase extends Fluid {
    @Getter
    private Material material;

    public FluidBase(String name, Material material) {
        super(name, new ResourceLocation(Electrolode.MODID,"blocks/fluids/" + name), new ResourceLocation(Electrolode.MODID,"blocks/fluids/" + name));
        this.material = material;
    }
}
