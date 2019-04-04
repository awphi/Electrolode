package ph.adamw.electrolode.item.core;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import ph.adamw.electrolode.block.machine.core.BlockMachine;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ItemBlockDescribed extends ItemBlock {
    private IExtendedDescription description;

    public ItemBlockDescribed(Block e, IExtendedDescription desc) {
        super(e);
        this.description = desc;
    }

    public ItemBlockDescribed(Block e) {
        this(e, (BlockMachine) e);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, list, flagIn);

        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            list.add("Hold " + ChatFormatting.BLUE + "LSHIFT " + ChatFormatting.GRAY + "for more info.");
            list.add(description.getDescription().split("#s")[0]);
        } else {
            String y = description.getDescription();
            y = y.replaceAll("#s", "");
            list.addAll(Arrays.asList(y.split("#n")));
        }
    }
}
