package ph.adamw.electrolode.manager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import ph.adamw.electrolode.ModFluids;
import ph.adamw.electrolode.block.machine.softener.TileSoftener;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.ModItems;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.recipe.FluidStackRecipeComponent;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;

public class OreDictManager {
    public static void registerEntries() {
        for(EnumMineral i : EnumMineral.values()) {
            ItemStack regularPowder = new ItemStack(ModItems.POWDER, 1, i.ordinal());
            ItemStack purifiedPowder = new ItemStack(ModItems.POWDER_PURIFIED, 1, i.ordinal());
            OreDictionary.registerOre("dust" + i.getOreDictSuffix(), regularPowder);
            OreDictionary.registerOre("dust" + i.getOreDictSuffix() + "Purified", purifiedPowder);
        }

        OreDictionary.registerOre("foodSalt", ModItems.SALT);
        OreDictionary.registerOre("dustSalt", ModItems.SALT);
        OreDictionary.registerOre("itemSalt", ModItems.SALT);
    }

    /**
     * Register recipes that require the ore dict
     *  - Called after registerEntries so Electrolode ore dict entries can be used too
     */
    public static void registerRecipes() {
        for(EnumMineral el : EnumMineral.values()) {

            // Powders -> stuff
            for (ItemStack i : OreDictionary.getOres("dust" + el.getOreDictSuffix())) {
                RecipeComponent entry = new ItemStackRecipeComponent(i);

                // Purifier
                RecipeHandler.addRecipe(
                        TilePurifier.class,
                        new MachineRecipe(
                            entry,
                            new ItemStackRecipeComponent(new ItemStack(ModItems.POWDER_PURIFIED, 1, el.ordinal())),
                            1000
                        )
                );
            }

            String[] prefixes = new String[] {"food", "dust", "item"};
            final RecipeComponent[] inputs = new RecipeComponent[]{
                    new ItemStackRecipeComponent(new ItemStack(ModItems.SALT)),
                    new FluidStackRecipeComponent(new FluidStack(FluidRegistry.WATER, 500))
            };

            for(String i : prefixes) {
                for(ItemStack j : OreDictionary.getOres(i + "Salt")) {
                    inputs[0] = new ItemStackRecipeComponent(j);
                    if(!RecipeHandler.hasRecipe(TileSoftener.class, inputs)) {
                        RecipeHandler.addRecipe(
                                TileSoftener.class,
                                new MachineRecipe(
                                    inputs,
                                    new FluidStackRecipeComponent(new FluidStack(ModFluids.SOFTENED_WATER, 500)),
                                    1000
                                )
                        );
                    }
                }
            }
        }
    }
}
