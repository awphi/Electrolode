package ph.adamw.electrolode.util;

import net.minecraftforge.fluids.FluidStack;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;

public class FluidUtils {
    public static MachineRecipeComponent[] toMachineRecipeArray(FluidStack[] e) {
        MachineRecipeComponent[] x = new MachineRecipeComponent[e.length];
        for(int i = 0; i < e.length; i ++) {
            x[i] = new MachineRecipeComponent(e[i]);
        }
        return x;
    }
}
