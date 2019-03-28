package ph.adamw.electrolode.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockFluidBase extends BlockFluidClassic {
    //TODO use this placeable flag to block placement of it FROM BUCKETS MAINLY
    private final boolean placeable;

    public BlockFluidBase(Fluid fluid, Material mat, boolean placeable) {
        super(fluid, mat);
        setRegistryName(fluid.getName());
        setUnlocalizedName(getRegistryName().toString());
        fluid.setBlock(this);
        this.placeable = placeable;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}
