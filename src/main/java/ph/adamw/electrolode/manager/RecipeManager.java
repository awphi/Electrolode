package ph.adamw.electrolode.manager;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ph.adamw.electrolode.ElectrolodeItems;
import ph.adamw.electrolode.block.machine.coalgenerator.TileCoalGenerator;
import ph.adamw.electrolode.block.machine.press.TilePress;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.item.core.EnumPlate;
import ph.adamw.electrolode.recipe.GeneratorRecipe;
import ph.adamw.electrolode.recipe.ItemStackRecipeComponent;
import ph.adamw.electrolode.recipe.MachineRecipe;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.util.ItemUtils;

public class RecipeManager {
    /**
     * Writes recipes to machines w/o the ore dict
     *  - Overrides recipes created w/ ore dict in OreDictManager
     */
    public static void register() {
        // Structural Plate
        ItemStackRecipeComponent[] ingredients
                = ItemUtils.makeItemStackRecipeArray(new ItemStack(ElectrolodeItems.POWDER_PURIFIED, 1, EnumMineral.IRON.ordinal()), 3);
        MachineRecipe recipe = new MachineRecipe(
                ingredients,
                new ItemStackRecipeComponent(new ItemStack(ElectrolodeItems.PLATE, 1, EnumPlate.STRUCTURAL.ordinal())),
                1000
        );
        RecipeHandler.addRecipe(TilePress.class, recipe);

        // Strengthened Plate
        ingredients =
                ItemUtils.makeItemStackRecipeArray(new ItemStack(ElectrolodeItems.POWDER_PURIFIED, 1, EnumMineral.CARBON.ordinal()), 3);
        recipe = new MachineRecipe(
                ingredients,
                new ItemStackRecipeComponent(new ItemStack(ElectrolodeItems.PLATE, 1, EnumPlate.STRENGTHENED.ordinal())),
                1000
        );
        RecipeHandler.addRecipe(TilePress.class, recipe);

        // Conductive Plate
        ingredients =
                ItemUtils.makeItemStackRecipeArray(new ItemStack(ElectrolodeItems.POWDER_PURIFIED, 1, EnumMineral.CARBON.ordinal()), 3);
        ingredients[1] = new ItemStackRecipeComponent(new ItemStack(ElectrolodeItems.POWDER_PURIFIED, 1, EnumMineral.GOLD.ordinal()));
        recipe = new MachineRecipe(
                ingredients,
                new ItemStackRecipeComponent(new ItemStack(ElectrolodeItems.PLATE, 1, EnumPlate.CONDUCTIVE.ordinal())),
                1000
        );
        RecipeHandler.addRecipe(TilePress.class, recipe);

        // Coal to RF
        recipe = new GeneratorRecipe(new ItemStackRecipeComponent(new ItemStack(Items.COAL)), 20000, 300);
        RecipeHandler.addRecipe(TileCoalGenerator.class, recipe);

        // Charcoal to RF
        recipe = new GeneratorRecipe(new ItemStackRecipeComponent(new ItemStack(Items.COAL, 1, 1)), 20000, 500);
        RecipeHandler.addRecipe(TileCoalGenerator.class, recipe);
    }
}

