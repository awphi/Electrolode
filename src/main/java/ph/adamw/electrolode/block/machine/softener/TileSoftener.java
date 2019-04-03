package ph.adamw.electrolode.block.machine.softener;

import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.block.machine.TileTankedMachine;
import ph.adamw.electrolode.gui.machine.GuiMachine;
import ph.adamw.electrolode.gui.machine.GuiSoftener;
import ph.adamw.electrolode.inventory.ElectrolodeContainer;
import ph.adamw.electrolode.inventory.fluid.FluidTankBase;

public class TileSoftener extends TileTankedMachine {
    @Override
    public Class<? extends ElectrolodeContainer> getContainerClass() {
        return ContainerSoftener.class;
    }

    @Override
    public Class<? extends GuiMachine> getGuiClass() {
        return GuiSoftener.class;
    }

    public int getOutputSlots() {
        return 0;
    }

    public int getInputSlots() {
        return 1;
    }

    public int getBaseEnergyUsage() {
        return 80;
    }

    protected void addInputTanks() {
        inputTanks.add(new FluidTankBase(5000));
    }

    protected void addOutputTanks() {
        outputTanks.add(new FluidTankBase(5000));
    }

    public int getBaseMaxEnergy() {
        return 25000;
    }

    @Override
    protected void addPotentialFaceRoles() {
        super.addPotentialFaceRoles();
        potentialRoles.remove(EnumFaceRole.OUTPUT_ITEM);
    }
}
