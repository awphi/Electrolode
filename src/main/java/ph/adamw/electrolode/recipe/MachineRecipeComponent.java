package ph.adamw.electrolode.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import ph.adamw.electrolode.util.FluidUtils;

import javax.crypto.Mac;

public class MachineRecipeComponent {
    private ItemStack itemStack;
    private FluidStack fluidStack;
    private Type type = null;

    public MachineRecipeComponent(ItemStack stack) {
        itemStack = stack;
        type = Type.ITEM;
    }

    public MachineRecipeComponent(Item i, int amount, int meta) {
        this(new ItemStack(i, amount, meta));
    }

    public MachineRecipeComponent(Item i) {
        this(new ItemStack(i));
    }

    public MachineRecipeComponent(Item i, int amount) {
        this(new ItemStack(i, amount));
    }


    public MachineRecipeComponent(FluidStack fluid) {
        fluidStack = fluid;
        type = Type.FLUID;
    }

    public MachineRecipeComponent(Fluid i, int amount) {
        this(new FluidStack(i, amount));
    }

    public boolean isValid(MachineRecipeComponent other) {
        switch(getType()) {
            case ITEM: return compareItemStack(other);
            case FLUID: return compareFluidStack(other);
            default: return false;
        }
    }

    private boolean compareFluidStack(MachineRecipeComponent other) {
        FluidStack th = getFluidStack();
        FluidStack oth = other.getFluidStack();
        if (th == null) {
            return false;
        } else {
            return th.getFluid() == oth.getFluid() && th.amount >= oth.amount && th.tag == oth.tag;
        }
    }

    private boolean compareItemStack(MachineRecipeComponent other) {
        ItemStack th = getItemStack();
        ItemStack oth = other.getItemStack();
        return th.getItem() == oth.getItem() && th.getItemDamage() == oth.getItemDamage() && th.getMetadata() == oth.getMetadata() && th.getTagCompound() == oth.getTagCompound() && th.getCount() >= oth.getCount();

    }

    //Make other constructors here etc.
    public Type getType() {
        return this.type;
    }

    public ItemStack getItemStack() {
        if(type != Type.ITEM) {
            throw new RuntimeException("getItemStack called on MachineRecipeComponent that didn't contain an ItemStack!");
        }
        return itemStack.copy();
    }

    public FluidStack getFluidStack() {
        if(type != Type.FLUID) {
            throw new RuntimeException("getFluidStack called on MachineRecipeComponent that didn't contain an FluidStack!");
        }
        return fluidStack;
    }

    public boolean isEmpty() {
        if(getType() == null) {
            return true;
        }
        switch(getType()) {
            case FLUID: return fluidStack == null;
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
