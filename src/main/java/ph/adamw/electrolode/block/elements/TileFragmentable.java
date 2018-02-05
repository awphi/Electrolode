package ph.adamw.electrolode.block.elements;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileFragmentable extends TileEntity {
    public int durability;

    public TileFragmentable() {
        durability = new Random().nextInt(200) + 20;
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("durability", this.durability);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.durability = compound.getInteger("durability");
    }
}
