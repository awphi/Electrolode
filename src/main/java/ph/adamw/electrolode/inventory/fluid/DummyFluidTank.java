package ph.adamw.electrolode.inventory.fluid;

/**
 * Simple dummy implementation to trick fluid pipes etc. into connecting to things that don't
 * have a capability by providing a dummy to connect to.
 */
public class DummyFluidTank extends FluidTankBase {
    public DummyFluidTank() {
        super(0);
        setCanDrain(false);
        setCanFill(false);
    }
}
