package ph.adamw.electrolode.manager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.Electrolode;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.machine.press.BlockPress;
import ph.adamw.electrolode.block.machine.purifier.BlockPurifier;
import ph.adamw.electrolode.block.machine.softener.BlockSoftener;
import ph.adamw.electrolode.item.core.IExtendedDescription;
import ph.adamw.electrolode.item.core.ItemBlockDescribed;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

//TODO migrate to using forge registries instead of these managers
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

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for(BlockBase i : blockMap.values()) {
            i.initModel();
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for(BlockBase i : blockMap.values()) {
            if(i.getRegistryName() == null) {
                continue;
            }

            if (i instanceof IExtendedDescription) {
                registry.register(new ItemBlockDescribed(i).setRegistryName(i.getRegistryName()));
            } else {
                registry.register(new ItemBlock(i).setRegistryName(i.getRegistryName()));
            }
        }
    }

    private static Block nameBlock(Block i, String name) {
        return i.setUnlocalizedName(Electrolode.MODID + "." + name).setRegistryName(Electrolode.MODID + ":" + name);
    }

    public static void registerTileEntity(Class<? extends TileEntity> e, String name) {
        GameRegistry.registerTileEntity(e, Electrolode.MODID + "_" + name);
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registerBlock(registry, BlockPurifier.class);
        registerBlock(registry, BlockPress.class);
        registerBlock(registry, BlockSoftener.class);
    }
}
