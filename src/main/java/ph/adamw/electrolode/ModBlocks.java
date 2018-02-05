package ph.adamw.electrolode;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.block.elements.BlockCarbon;
import ph.adamw.electrolode.block.elements.TileFragmentable;
import ph.adamw.electrolode.block.machines.purifier.BlockPurifier;
import ph.adamw.electrolode.block.machines.purifier.TilePurifier;
import ph.adamw.electrolode.items.ItemBlockDescribed;

public class ModBlocks {
    public static final BlockCarbon carbonBlock = new BlockCarbon();
    public static final BlockPurifier purifier = new BlockPurifier();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        carbonBlock.initModel();
        purifier.initModel();
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(new ItemBlock(ModBlocks.carbonBlock).setRegistryName(ModBlocks.carbonBlock.getRegistryName()));
        registry.register(new ItemBlockDescribed(ModBlocks.purifier).setRegistryName(ModBlocks.purifier.getRegistryName()));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(nameBlock(carbonBlock,"carbonblock"));
        registry.register(nameBlock(purifier,"purifier"));
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileFragmentable.class, Electrolode.MODID + "_fragmentable");
        GameRegistry.registerTileEntity(TilePurifier.class, Electrolode.MODID + "_purifier");
    }

    private static Block nameBlock(Block i, String name) {
        return i.setUnlocalizedName(Electrolode.MODID + "." + name).setRegistryName(Electrolode.MODID + ":" + name);
    }
}
