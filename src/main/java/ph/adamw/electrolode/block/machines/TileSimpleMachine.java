package ph.adamw.electrolode.block.machines;

public abstract class TileSimpleMachine extends TileItemMachine {
    public int getInputSize() {
        return 1;
    }

    public int getOutputSize() {
        return 1;
    }

    public void processingComplete() {
        outputOnlySlotsWrapper.insertItemInternally(0, getCurrentRecipeOutput()[0], false);
        inputOnlySlotsWrapper.extractItemInternally(0, getCurrentRecipeInput()[0].getCount(), false);
    }
}
