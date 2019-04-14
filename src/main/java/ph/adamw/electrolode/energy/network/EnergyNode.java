package ph.adamw.electrolode.energy.network;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ph.adamw.electrolode.block.properties.Connections;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;
import ph.adamw.electrolode.util.WorldUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnergyNode {
	@Getter
	private final BlockPos cablePos;
	private final Map<EnumFacing, EnergyNode> connections = new HashMap<>();
	private final Connections isConnected = new Connections();

	private static final Map<Long, EnergyNode> nbtCache = new HashMap<>();

	public EnergyNode(BlockPos pos) {
		this.cablePos = pos;
	}

	public void addTwoWayEdge(World world, EnergyNode node) {
		addEdge(world, node);
		node.addEdge(world, this);
	}

	private void addEdge(World world, EnergyNode node) {
		final EnumFacing facing = WorldUtils.getFacingFrom(node.getCablePos(), cablePos);
		connections.put(facing, node);

		isConnected.setConnected(facing, true);
		getChannelTile(world).markForUpdate();
	}

	public void removeEdge(World world, EnergyNode node) {
		final EnumFacing facing = WorldUtils.getFacingFrom(node.getCablePos(), cablePos);
		connections.remove(facing);

		isConnected.setConnected(facing, false);
		getChannelTile(world).markForUpdate();
	}


	public TileEnergyChannel getChannelTile(World world) {
		final TileEntity te = world.getTileEntity(cablePos);

		if(te instanceof TileEnergyChannel) {
			return (TileEnergyChannel) te;
		}

		return null;
	}

	public static EnergyNode readFromNbt(NBTTagCompound compound) {
		final EnergyNode node = buildOrRetrieveLoneNode(compound.getLong("cablePos"));

		int c = 0;
		while(compound.hasKey("connection" + c)) {
			final long x = compound.getLong("connection" + c);
			final BlockPos pos = BlockPos.fromLong(x);
			final EnumFacing facing = WorldUtils.getFacingFrom(pos, node.getCablePos());
			node.connections.put(facing, buildOrRetrieveLoneNode(x));
			node.isConnected.setConnected(facing, true);
			c ++;
		}

		return node;
	}

	private static EnergyNode buildOrRetrieveLoneNode(long pos) {
		if(nbtCache.containsKey(pos)) {
			return nbtCache.get(pos);
		}

		final EnergyNode node = new EnergyNode(BlockPos.fromLong(pos));
		nbtCache.put(pos, node);
		return node;
	}

	public NBTTagCompound writeToNbt(NBTTagCompound compound) {
		long pos = cablePos.toLong();

		compound.setLong("cablePos", pos);

		int c = 0;
		for(EnergyNode i : connections.values()) {
			compound.setLong("connection" + c, i.getCablePos().toLong());
			c ++;
		}

		return compound;
	}

	public Set<EnergyNode> getConnected() {
		return ImmutableSet.copyOf(connections.values());
	}
}
