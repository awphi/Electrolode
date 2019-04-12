package ph.adamw.electrolode.energy.network;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.tile.channel.TileCable;
import ph.adamw.electrolode.util.WorldUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

public class EnergyNetwork {
	private final Map<BlockPos, EnergyNode> nodes = new HashMap<>();
	private final Set<BlockPos> consumers = new HashSet<>();
	private UUID uuid = UUID.randomUUID();

	private static final Map<UUID, EnergyNetwork> networkCache = new HashMap<>();

	public static EnergyNetwork readFromNbt(NBTTagCompound compound) {
		final UUID uuid = compound.getUniqueId("uuid");

		if(networkCache.containsKey(uuid)) {
			return networkCache.get(uuid);
		}

		final EnergyNetwork network = new EnergyNetwork();
		network.uuid = uuid;

		int c = 0;
		while(compound.hasKey("consumer" + c)) {
			network.consumers.add(BlockPos.fromLong(compound.getLong("consumer" + c)));
			c ++;
		}

		c = 0;
		while(compound.hasKey("node" + c)) {
			network.nodes.put(BlockPos.fromLong(compound.getLong("node" + c)), EnergyNode.readFromNbt(compound.getCompoundTag("node" + c + "-value")));
			c++;
		}

		networkCache.put(network.uuid, network);
		return network;
	}

	public NBTBase writeToNbt(NBTTagCompound compound) {
		compound.setUniqueId("uuid", uuid);

		int c = 0;
		for(BlockPos i : consumers) {
			compound.setLong("consumer" + c, i.toLong());
			c ++;
		}

		c = 0;
		for(BlockPos i : nodes.keySet()) {
			compound.setLong("node" + c, i.toLong());
			compound.setTag("node" + c + "-value", nodes.get(i).writeToNbt(new NBTTagCompound()));
			c ++;
		}

		return compound;
	}


	public void add(TileCable cable, boolean join) {
		cable.setNetwork(this);

		nodes.put(cable.getPos(), new EnergyNode(cable.getPos()));
		final EnergyNode node = nodes.get(cable.getPos());

		System.out.println("Adding " + cable + " to network: " + this + " with joining: " + join + " on side: " + cable.getWorld().getClass().getSimpleName());

		// Connects existing nodes to this one
		if(join) {
			for (EnumFacing i : EnumFacing.VALUES) {
				final BlockPos neighborPos = cable.getPos().offset(i);

				if (nodes.containsKey(neighborPos)) {
					final EnergyNode neighborNode = nodes.get(neighborPos);
					neighborNode.addTwoWayAdjacency(node);
				}
			}
		}

		// Checks for adjacent externals
		for(EnumFacing i : EnumFacing.VALUES) {
			final BlockPos pos = cable.getPos().offset(i);
			final TileEntity te = cable.getWorld().getTileEntity(pos);
			if(te != null && te.hasCapability(CapabilityEnergy.ENERGY, i) && !(te instanceof TileCable)) {
				externalAdded(te, i);
			}
		}
	}

	/**
	 * @param cable the cable to remove from the network
	 * @param sever whether cable connections should be severed or not
	 * @return true if the network was reduced to size 0 after this removal, false otherwise
	 */
	public boolean remove(TileCable cable, boolean sever) {
		System.out.println("Removing " + cable + " from network: " + this + " with sever: " + sever);

		if(sever) {
			final EnergyNode node = nodes.get(cable.getPos());

			for (EnergyNode i : node.getAdjacencies()) {
				i.getAdjacencies().remove(node);
			}
		}

		nodes.remove(cable.getPos());

		if(nodes.size() <= 0) {
			return true;
		}

		return false;
	}

	public EnergyNetwork mergeInto(World world, EnergyNetwork network) {
		for(EnergyNode i : nodes.values()) {
			i.getCable(world).setNetwork(network);
		}

		for(EnergyNode i : nodes.values()) {
			network.add(i.getCable(world), true);
		}

		return network;
	}

	public void externalAdded(TileEntity te, EnumFacing facing) {
		final IEnergyStorage ies = te.getCapability(CapabilityEnergy.ENERGY, facing);

		if(ies != null && ies.canReceive()) {
			consumers.add(te.getPos());
		}
	}

	public void externalRemoved(BlockPos neighbor) {
		consumers.remove(neighbor);
	}

	// Depth first traversal
	public Set<EnergyNode> getConnectedNodes() {
		final Stack<EnergyNode> stack = new Stack<>();
		final Map<EnergyNode, Boolean> map = new HashMap<>();
		stack.add(nodes.values().toArray(new EnergyNode[0])[0]);

		while (!stack.isEmpty()) {
			EnergyNode element = stack.pop();
			map.put(element, true);

			for (EnergyNode n : element.getAdjacencies()) {
				if(!map.containsKey(n)) {
					stack.add(n);
				}
			}
		}

		return map.keySet();
	}

	public boolean isSplintered(Set<EnergyNode> nodes) {
		return nodes.size() != this.nodes.keySet().size();
	}

	/**
	 * Called after the cable has received the energy pushed
	 * @param pos - the position of the cable that received the energy
	 * @param maxReceive indicates the amount that was attempted to push into the network - not actually the amount that was received
	 */
	public void energyPush(World world, BlockPos pos, int maxReceive) {
		final EnergyNode node = nodes.get(pos);
		if(node == null) {
			System.err.println("Could not resolve energy node at " + pos + "! This should not happen.");
			return;
		}

		final Set<BlockPos> ready = new HashSet<>();

		for(BlockPos i : consumers) {
			final TileEntity te =  world.getTileEntity(i);
			final IEnergyStorage ies = te.getCapability(CapabilityEnergy.ENERGY, WorldUtils.getFacingFrom(pos, te.getPos()));

			if(ies != null && ies.canReceive()) {
				ready.add(i);
			}
		}

		if(ready.size() > 0) {
			final int maxTransfer = node.getCable(world).getEnergy().getTransfer();
			final int amount = (int) ((float) Math.min(maxTransfer, maxReceive) / (float) ready.size());

			for(BlockPos i : ready) {
				new EnergyRequest(amount, node, i).execute(world,this);
			}
		}
	}

	public EnergyNode resolveFirstNodeNextTo(BlockPos to) {
		for(EnumFacing i : EnumFacing.VALUES) {
			if(nodes.containsKey(to.offset(i))) {
				return nodes.get(to.offset(i));
			}
		}

		return null;
	}
}
