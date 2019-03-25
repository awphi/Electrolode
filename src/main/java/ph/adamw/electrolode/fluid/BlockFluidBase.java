package ph.adamw.electrolode.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidBase extends BlockFluidClassic {
    public BlockFluidBase(Fluid fluid, Material mat) {
        super(fluid, mat);
        setRegistryName(fluid.getName());
        setUnlocalizedName(getRegistryName().toString());
        fluid.setBlock(this);
    }

    public BlockFluidBase(Fluid fluid) {
        this(fluid, Material.WATER);
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}
