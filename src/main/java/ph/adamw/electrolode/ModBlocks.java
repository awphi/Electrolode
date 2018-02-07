package ph.adamw.electrolode;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import ph.adamw.electrolode.block.BlockBase;
import ph.adamw.electrolode.block.machine.BlockBaseMachine;
import ph.adamw.electrolode.block.machine.press.BlockPress;
import ph.adamw.electrolode.block.machine.purifier.BlockPurifier;
import ph.adamw.electrolode.block.machine.purifier.TilePurifier;
import ph.adamw.electrolode.item.IExtendedDescription;
import ph.adamw.electrolode.item.ItemBlockDescribed;
import ph.adamw.electrolode.item.ItemMachine;

import java.util.HashMap;

public class ModBlocks {
    private static final HashMap<Class<? extends BlockBase>, BlockBase> blockMap = new HashMap<>();
    private static IForgeRegistry<Block> registry;

    private static void registerBlock(Class<? extends BlockBase> block) {
        if(registry == null) {
            System.err.println("Attempted to register block at invalid time: " + block.getName());
            return;
        }
        try {
            blockMap.put(block, block.newInstance());
            registry.register(nameBlock(getBlock(block), getBlockName(block)));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Block getBlock(Class<? extends BlockBase> e) {
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
            if(i instanceof BlockBaseMachine) {
                registry.register(new ItemMachine((BlockBaseMachine) i).setRegistryName(i.getRegistryName()));
            } else if (i instanceof IExtendedDescription) {
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
        ModBlocks.registry = registry;
        registerBlock(BlockPurifier.class);
        registerBlock(BlockPress.class);
    }

    //Register one-off TEs here, machine tile entities are register along with their block
    public static void registerTileEntities() {

    }
}
