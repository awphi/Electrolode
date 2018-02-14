package ph.adamw.electrolode.manager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.ModItems;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;

public class OreDictManager {
    public static void registerEntries() {
        for(EnumMineral i : EnumMineral.values()) {
            ItemStack regularPowder = new ItemStack(ModItems.POWDER, 1, i.getOrdinal());
            ItemStack purifiedPowder = new ItemStack(ModItems.POWDER_PURIFIED, 1, i.getOrdinal());
            OreDictionary.registerOre("dust" + i.getOreDictSuffix(), regularPowder);
            OreDictionary.registerOre("dust" + i.getOreDictSuffix() + "Purified", purifiedPowder);
        }
    }

    public static void registerRecipes() {
        for(EnumMineral el : EnumMineral.values()) {

            // Powders -> stuff
            for (ItemStack i : OreDictionary.getOres("dust" + el.getOreDictSuffix())) {
                MachineRecipeComponent entry = new MachineRecipeComponent(i);

                // Purifier
                RecipeHandler.addRecipe(TilePurifier.class, entry, new MachineRecipeComponent(ModItems.POWDER_PURIFIED, 1, el.getOrdinal()));
            }
        }
    }
}
