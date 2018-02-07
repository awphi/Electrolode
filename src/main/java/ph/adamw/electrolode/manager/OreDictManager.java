package ph.adamw.electrolode.manager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ph.adamw.electrolode.EnumPurifiableMineral;
import ph.adamw.electrolode.ModItems;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.item.ItemPowder;
import ph.adamw.electrolode.item.ItemPurifiedPowder;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;

public class OreDictManager {
    public static void registerEntries() {
        for(EnumPurifiableMineral i : EnumPurifiableMineral.values()) {
            OreDictionary.registerOre("dust" + i.getOreDictSuffix(), new ItemStack(ModItems.get(ItemPowder.class), 1, i.getOrdinal()));
        }
    }

    public static void registerRecipes() {
        for(EnumPurifiableMineral el : EnumPurifiableMineral.values()) {

            //Powders -> Purified Powders
            for (ItemStack i : OreDictionary.getOres("dust" + el.getOreDictSuffix())) {
                MachineRecipeComponent entry = new MachineRecipeComponent(i);
                RecipeHandler.addRecipe(TilePurifier.class, entry, new MachineRecipeComponent(new ItemStack(ModItems.get(ItemPurifiedPowder.class), 1, el.getOrdinal())));
            }
        }
    }
}
