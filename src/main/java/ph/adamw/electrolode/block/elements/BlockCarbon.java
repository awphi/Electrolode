package ph.adamw.electrolode.block.elements;

import net.minecraft.block.material.Material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ph.adamw.electrolode.EnumPurifiableMineral;
import ph.adamw.electrolode.ModItems;

public class BlockCarbon extends BlockFragmentable {
    public BlockCarbon() {
        super(Material.ROCK, true);
    }

    public Item getFragment(int level) {
        return new ItemStack(ModItems.powder, 1, EnumPurifiableMineral.CARBON.getOrdinal()).getItem();
    }
}
