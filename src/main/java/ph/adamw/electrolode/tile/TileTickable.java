package ph.adamw.electrolode.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileTickable extends TileEntity implements ITickable {
	private boolean toUpdate = false;

	public abstract void tick();

	@Override
	public void update() {
		tick();

		if(toUpdate) {
			toUpdate = false;
			world.markBlockRangeForRenderUpdate(pos, pos);
			world.notifyBlockUpdate(pos, getState(), getState(), 3);
			world.scheduleBlockUpdate(pos, this.getBlockType(),0,0);
			markDirty();
		}
	}

	public void markForUpdate() {
		toUpdate = true;
	}

	public IBlockState getState() {
		return world.getBlockState(this.pos);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 3, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		handleUpdateTag(packet.getNbtCompound());
	}

}
