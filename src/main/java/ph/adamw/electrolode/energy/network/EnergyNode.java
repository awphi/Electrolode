package ph.adamw.electrolode.energy.network;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import ph.adamw.electrolode.block.BlockCable;
import ph.adamw.electrolode.block.BlockProperties;
import ph.adamw.electrolode.block.machine.core.Connections;
import ph.adamw.electrolode.tile.channel.TileCable;
import ph.adamw.electrolode.util.WorldUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnergyNode {
	@Getter
	private final BlockPos cablePos;
	private final Map<EnumFacing, EnergyNode> connections = new HashMap<>();

	private static final Map<Long, EnergyNode> nbtCache = new HashMap<>();

	public EnergyNode(BlockPos pos) {
		this.cablePos = pos;
	}

	public void addTwoWayEdge(World world, EnergyNode node) {
		addEdge(world, node);
		node.addEdge(world, this);
	}

	private void addEdge(World world, EnergyNode node) {
		connections.put(WorldUtils.getFacingFrom(cablePos, node.getCablePos()), node);
		connectionChanged(world);
	}

	public void removeEdge(World world, EnergyNode node) {
		connections.remove(WorldUtils.getFacingFrom(cablePos, node.getCablePos()));
		connectionChanged(world);
	}


	public TileCable getCable(World world) {
		final TileEntity te = world.getTileEntity(cablePos);

		if(te instanceof TileCable) {
			return (TileCable) te;
		}

		return null;
	}

	/**
	 * Used to update the block on when connections are changed so it can be rendered as such
	 */
	private void connectionChanged(World world) {
		final IBlockState state = world.getBlockState(cablePos);

		if(state.getBlock() instanceof BlockCable && state instanceof IExtendedBlockState) {
			final IExtendedBlockState exState = (IExtendedBlockState) state;
			world.setBlockState(cablePos, exState.withProperty(BlockProperties.CONNECTIONS, generateConnections()));
		} else {
			System.err.println("Expected cable at " + cablePos.toString() + " but found " + state.getBlock().toString() + "!");
		}
	}

	private Connections generateConnections() {
		Connections result = new Connections();

		for(EnumFacing i : connections.keySet()) {
			result = result.with(i, true);
		}

		return result;
	}

	public static EnergyNode readFromNbt(NBTTagCompound compound) {
		final EnergyNode node = buildOrRetrieveLoneNode(compound.getLong("cablePos"));

		int c = 0;
		while(compound.hasKey("connection" + c)) {
			final long x = compound.getLong("connection" + c);
			final BlockPos pos = BlockPos.fromLong(x);
			node.connections.put(WorldUtils.getFacingFrom(node.getCablePos(), pos), buildOrRetrieveLoneNode(x));
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
