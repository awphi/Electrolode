package ph.adamw.electrolode.manager;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import ph.adamw.electrolode.ModFluids;
import ph.adamw.electrolode.block.machine.softener.TileSoftener;
import ph.adamw.electrolode.item.core.EnumMineral;
import ph.adamw.electrolode.ModItems;
import ph.adamw.electrolode.block.machine.press.TilePress;
import ph.adamw.electrolode.item.core.EnumPlate;
import ph.adamw.electrolode.recipe.MachineRecipeComponent;
import ph.adamw.electrolode.recipe.RecipeHandler;
import ph.adamw.electrolode.util.ItemUtils;

public class MachineRecipeManager {
    public static void registerMachineRecipes() {
        // Press
        ItemStack[] ingredients = ItemUtils.makeItemStackArray(new ItemStack(ModItems.POWDER_PURIFIED, 1, EnumMineral.IRON.getOrdinal()), 3);
        RecipeHandler.addRecipe(TilePress.class, ItemUtils.toMachineRecipeArray(ingredients), new MachineRecipeComponent(ModItems.PLATE, 1, EnumPlate.STRUCTURAL.getOrdinal()));

        ingredients = ItemUtils.makeItemStackArray(new ItemStack(ModItems.POWDER_PURIFIED, 1, EnumMineral.CARBON.getOrdinal()), 3);
        RecipeHandler.addRecipe(TilePress.class, ItemUtils.toMachineRecipeArray(ingredients), new MachineRecipeComponent(ModItems.PLATE, 1, EnumPlate.STRENGTHENED.getOrdinal()));

        ingredients = ItemUtils.makeItemStackArray(new ItemStack(ModItems.POWDER_PURIFIED, 1, EnumMineral.CARBON.getOrdinal()), 3);
        ingredients[1] = new ItemStack(ModItems.POWDER_PURIFIED, 1, EnumMineral.GOLD.getOrdinal());
        RecipeHandler.addRecipe(TilePress.class, ItemUtils.toMachineRecipeArray(ingredients), new MachineRecipeComponent(ModItems.PLATE, 1, EnumPlate.CONDUCTIVE.getOrdinal()));

        // Softener
        MachineRecipeComponent[] components = new MachineRecipeComponent[] {new MachineRecipeComponent(ModItems.SALT), new MachineRecipeComponent(new FluidStack(FluidRegistry.WATER, 500))};
        RecipeHandler.addRecipe(TileSoftener.class, components, new MachineRecipeComponent(ModFluids.SOFTENED_WATER, 500));
    }
}
