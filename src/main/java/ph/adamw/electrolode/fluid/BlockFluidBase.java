package ph.adamw.electrolode.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidBase extends BlockFluidClassic {
    //TODO use this placeable flag to block placement of it FROM BUCKETS MAINLY
    private final boolean placeable;

    public BlockFluidBase(Fluid fluid, Material mat, boolean placeable) {
        super(fluid, mat);
        setRegistryName(fluid.getName());
        setTranslationKey(getRegistryName().toString());
        fluid.setBlock(this);
        this.placeable = placeable;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}
