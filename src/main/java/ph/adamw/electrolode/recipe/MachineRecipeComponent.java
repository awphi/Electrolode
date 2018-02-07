package ph.adamw.electrolode.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MachineRecipeComponent {
    private ItemStack itemStack;
    private FluidStack fluidStack;

    public MachineRecipeComponent(ItemStack stack) {
        itemStack = stack;
    }

    public MachineRecipeComponent(FluidStack fluid) {
        fluidStack = fluid;
    }

    public boolean isValid(MachineRecipeComponent other) {
        if(getType() == null) {
            return false;
        }
        switch(getType()) {
            case ITEM: return compareItemStack(other);
            case FLUID: return compareFluidStack(other);
            default: return false;
        }
    }

    private boolean compareFluidStack(MachineRecipeComponent other) {
        FluidStack th = getFluidStack();
        FluidStack oth = other.getFluidStack();
        return th.getFluid() == oth.getFluid() && th.amount >= oth.amount && th.tag == oth.tag;
    }

    private boolean compareItemStack(MachineRecipeComponent other) {
        ItemStack th = getItemStack();
        ItemStack oth = other.getItemStack();
        return th.getItem() == oth.getItem() && th.getItemDamage() == oth.getItemDamage() && th.getMetadata() == oth.getMetadata() && th.getTagCompound() == oth.getTagCompound() && th.getCount() >= oth.getCount();

    }

    //Make other constructors here etc.
    private Type getType() {
        if(itemStack != null) return Type.ITEM;
        if(fluidStack != null) return Type.FLUID;
        //etc.
        return null;
    }

    public ItemStack getItemStack() {
        if(itemStack == null) {
            throw new RuntimeException("getItemStack called on MachineRecipeComponent that didn't contain an ItemStack!");
        }
        return itemStack.copy();
    }

    public FluidStack getFluidStack() {
        if(fluidStack == null) {
            throw new RuntimeException("getFluidStack called on MachineRecipeComponent that didn't contain an FluidStack!");
        }
        return fluidStack.copy();
    }

    public boolean isEmpty() {
        if(getType() == null) {
            return true;
        }
        switch(getType()) {
            case FLUID: return fluidStack.amount == 0;
            case ITEM: return itemStack.isEmpty();
            default: return true;
        }
    }


    public enum Type {
        ITEM(0),
        FLUID(1);

        private int id;

        Type(int i) {
            id = i;
        }
    }
}
