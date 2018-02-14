package ph.adamw.electrolode.inventory.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

import javax.annotation.Nullable;

public class FluidTankBase extends FluidTank {
    public FluidTankBase(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FluidTankBase(int capacity) {
        this(null, capacity);
    }

    public FluidTankBase(Fluid fluid, int amount, int capacity) {
        this(new FluidStack(fluid, amount), capacity);
    }

    @Override
    public void onContentsChanged() {
        if(this.tile instanceof TileBaseMachine) {
            ((TileBaseMachine) this.tile).markForUpdate();
        }
    }
}
