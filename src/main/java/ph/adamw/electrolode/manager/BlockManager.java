package ph.adamw.electrolode.manager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.channel.BlockEnergyChannel;
import ph.adamw.electrolode.block.machine.BlockCoalGenerator;
import ph.adamw.electrolode.block.machine.BlockPress;
import ph.adamw.electrolode.block.machine.BlockPurifier;
import ph.adamw.electrolode.block.machine.BlockSoftener;

import java.util.HashMap;

public class BlockManager {
    private static final HashMap<Class<? extends BlockBase>, BlockBase> blockMap = new HashMap<>();

    private static void registerBlock(IForgeRegistry<Block> registry, Class<? extends BlockBase> block) {
        if(registry == null) {
            System.err.println("Attempted to register block at invalid time: " + block.getName());
            return;
        }

        try {
            blockMap.put(block, block.newInstance());
            registry.register(nameBlock(get(block), getBlockName(block)));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Block get(Class<? extends BlockBase> e) {
        return blockMap.get(e);
    }

    private static String getBlockName(Class<? extends BlockBase> e) {
        return blockMap.get(e).getBlockName();
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for(BlockBase i : blockMap.values()) {
            if(i.getRegistryName() == null) {
                continue;
            }

            registry.register(i.getItemBlock().setRegistryName(i.getRegistryName()));
        }
    }

    private static Block nameBlock(Block i, String name) {
        return i.setTranslationKey(Electrolode.MODID + "." + name).setRegistryName(name);
    }

    public static void registerTileEntity(Class<? extends TileEntity> e, String name) {
        GameRegistry.registerTileEntity(e, new ResourceLocation(Electrolode.MODID, "tile_" + name));
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registerBlock(registry, BlockPurifier.class);
        registerBlock(registry, BlockPress.class);
        registerBlock(registry, BlockSoftener.class);
        registerBlock(registry, BlockCoalGenerator.class);
        registerBlock(registry, BlockEnergyChannel.class);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for(BlockBase i : blockMap.values()) {
            i.initModel();
        }
    }


    @SideOnly(Side.CLIENT)
	public static void initItemModels() {
        for(BlockBase i : blockMap.values()) {
            i.initItemModel();
        }
	}
}
