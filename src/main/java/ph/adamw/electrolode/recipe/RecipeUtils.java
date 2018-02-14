package ph.adamw.electrolode.recipe;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class RecipeUtils {
    public static boolean canComponentArraysStack(MachineRecipeComponent[] a, MachineRecipeComponent[] b) {
        if(a.length != b.length) return false;

        for(int i = 0; i < a.length; i ++) {
            if(a[i].getType() == MachineRecipeComponent.Type.ITEM) {
                if(!ItemHandlerHelper.canItemStacksStack(a[i].getItemStack(), b[i].getItemStack())) {
                    return false;
                }
            } else if(a[i].getType() == MachineRecipeComponent.Type.FLUID) {
                if(a[i].getFluidStack() == null || b[i].getFluidStack() == null) {
                    continue;
                }

                if(a[i].getFluidStack().getFluid() != b[i].getFluidStack().getFluid() || !FluidStack.areFluidStackTagsEqual(a[i].getFluidStack(), b[i].getFluidStack())) {
                    return false;
                }
            }
        }

        return true;
    }
}
