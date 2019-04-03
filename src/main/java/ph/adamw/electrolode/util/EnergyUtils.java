package ph.adamw.electrolode.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.block.machine.TileMachine;

public class EnergyUtils {
    public static boolean isItemStackChargeable(ItemStack itemstack) {
        return itemstack != null && itemstack.hasCapability(CapabilityEnergy.ENERGY, null);
    }

    public static void discharge(ItemStack itemStack, TileMachine te) {
        if(!isItemStackChargeable(itemStack)) {
            return;
        }

        final IEnergyStorage energyStorage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);

        //noinspection ConstantConditions
        if(energyStorage.getEnergyStored() - te.getEnergyUsage() > 0 && te.getEnergy().getEnergyStored() + te.getEnergyUsage() <= te.getEnergy().getMaxEnergyStored()) {
            energyStorage.extractEnergy(te.getEnergyUsage(), false);
            te.getEnergy().receiveEnergy(te.getEnergyUsage(), false);
        }
    }
}
