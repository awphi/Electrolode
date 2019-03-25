package ph.adamw.electrolode.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

public class EnergyUtils {
    public static boolean isItemStackChargeable(ItemStack itemstack) {
        if(itemstack == null) {
            return false;
        }

        return itemstack.hasCapability(CapabilityEnergy.ENERGY, null);
    }

    public static void discharge(ItemStack itemStack, TileBaseMachine te) {
        if(!isItemStackChargeable(itemStack)) return;

        IEnergyStorage energyStorage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
        if(energyStorage.getEnergyStored() - te.getEnergyUsage() > 0 && te.getEnergyStored() + te.getEnergyUsage() <= te.getMaxEnergyStored()) {
            energyStorage.extractEnergy(te.getEnergyUsage(), false);
            te.receiveEnergy(te.getEnergyUsage(), false);
        }
    }
}
