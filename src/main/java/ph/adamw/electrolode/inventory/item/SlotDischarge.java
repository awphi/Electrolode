package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ph.adamw.electrolode.util.EnergyUtils;

import javax.annotation.Nullable;

public class SlotDischarge extends SlotItemHandler {
    public SlotDischarge(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return EnergyUtils.isItemStackChargeable(stack);
    }
}
