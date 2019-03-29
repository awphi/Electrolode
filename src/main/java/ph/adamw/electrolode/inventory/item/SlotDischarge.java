package ph.adamw.electrolode.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ph.adamw.electrolode.gui.GuiPoint;
import ph.adamw.electrolode.util.EnergyUtils;

import javax.annotation.Nullable;

public class SlotDischarge extends SlotItemHandler {
    public SlotDischarge(IItemHandler itemHandler, int index, GuiPoint point) {
        super(itemHandler, index, point.x, point.y);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return EnergyUtils.isItemStackChargeable(stack);
    }
}
