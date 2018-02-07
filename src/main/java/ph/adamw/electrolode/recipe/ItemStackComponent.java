package ph.adamw.electrolode.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemStackComponent extends MachineRecipeComponent {
    public ItemStackComponent(ItemStack i) {
        super(i);
    }

    public ItemStackComponent(Item i, int amount) {
        this(new ItemStack(i, amount));
    }

    public ItemStackComponent(Item i, int amount, int meta) {
        this(new ItemStack(i, amount, meta));
    }

    public ItemStackComponent(ItemBlock i, int amount, int meta) {
        this(new ItemStack(i, amount, meta));
    }
}
