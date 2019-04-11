package ph.adamw.electrolode.energy.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.tile.channel.TileCable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class EnergyNetwork {
	private final Map<BlockPos, EnergyNode> cache = new HashMap<>();
	private final Map<BlockPos, IEnergyStorage> consumers = new HashMap<>();


	public void add(TileCable cable, boolean join) {
		cable.setNetwork(this);

		cache.put(cable.getPos(), new EnergyNode(cable));
		final EnergyNode node = cache.get(cable.getPos());

		System.out.println("Adding " + cable + " to network: " + this + " with joining: " + join + " on side: " + cable.getWorld().getClass().getSimpleName());

		// Connects existing nodes to this one
		if(join) {
			for (EnumFacing i : EnumFacing.VALUES) {
				final BlockPos neighborPos = cable.getPos().offset(i);

				if (cache.containsKey(neighborPos)) {
					final EnergyNode neighborNode = cache.get(neighborPos);
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
			final EnergyNode node = cache.get(cable.getPos());

			for (EnergyNode i : node.getAdjacencies()) {
				i.getAdjacencies().remove(node);
			}
		}

		cache.remove(cable.getPos());

		if(cache.size() <= 0) {
			EnergyNetworkManager.unregisterNetwork(this);
			return true;
		}

		return false;
	}

	public EnergyNetwork mergeInto(World world, EnergyNetwork network) {
		for(EnergyNode i : cache.values()) {
			i.getCable(world).setNetwork(network);
		}

		for(EnergyNode i : cache.values()) {
			network.add(i.getCable(world), true);
		}

		EnergyNetworkManager.unregisterNetwork(this);
		return network;
	}

	public void externalAdded(TileEntity te, EnumFacing facing) {
		final IEnergyStorage ies = te.getCapability(CapabilityEnergy.ENERGY, facing);

		if(ies.canReceive()) {
			consumers.put(te.getPos(), ies);
		}
	}

	public void externalRemoved(BlockPos neighbor) {
		consumers.remove(neighbor);
	}

	// Depth first traversal
	public Set<EnergyNode> getConnectedNodes() {
		final Stack<EnergyNode> stack = new Stack<>();
		final Map<EnergyNode, Boolean> map = new HashMap<>();
		stack.add(cache.values().toArray(new EnergyNode[0])[0]);

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
		return nodes.size() != cache.keySet().size();
	}

	/**
	 * Called after the cable has received the energy pushed
	 * @param pos - the position of the cable that received the energy
	 * @param maxReceive indicates the amount that was attempted to push into the network - not actually the amount that was received
	 */
	public void energyPush(World world, BlockPos pos, int maxReceive) {
		final EnergyNode node = cache.get(pos);
		if(node == null) {
			System.err.println("Could not resolve energy node at " + pos + "! This should not happen.");
			return;
		}

		final Set<BlockPos> ready = new HashSet<>();

		for(BlockPos i : consumers.keySet()) {
			final IEnergyStorage ies = consumers.get(i);
			if(ies.canReceive()) {
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
			if(cache.containsKey(to.offset(i))) {
				return cache.get(to.offset(i));
			}
		}

		return null;
	}
}
