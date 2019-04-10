package ph.adamw.electrolode.energy.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import ph.adamw.electrolode.tile.TileCable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class EnergyNetwork {
	private final Map<BlockPos, EnergyNode> cache = new HashMap<>();
	private final Map<BlockPos, ExternalState> externalMap = new HashMap<>();
	private final Map<ExternalState, Integer> externalCounts = new HashMap<ExternalState, Integer>() {
		{
			put(ExternalState.CONSUMER, 0);
			put(ExternalState.PRODUCER, 0);
		}
	};


	public void add(TileCable cable, boolean join) {
		cable.setNetwork(this);

		cache.put(cable.getPos(), new EnergyNode(cable));
		final EnergyNode node = cache.get(cable.getPos());

		System.out.println("Adding " + cable + " to network: " + this + " with joining: " + join + " on side: " + cable.getWorld().getClass().getSimpleName());

		if(join) {
			for (EnumFacing i : EnumFacing.VALUES) {
				final BlockPos neighborPos = cable.getPos().offset(i);

				if (cache.containsKey(neighborPos)) {
					final EnergyNode neighborNode = cache.get(neighborPos);
					neighborNode.addTwoWayAdjacency(node);
				}
			}
		}
	}

	/**
	 *
	 * @param cable
	 * @param sever
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
		final ExternalState state = ExternalState.resolve(te.getCapability(CapabilityEnergy.ENERGY, facing));
		if(state != null) {
			externalMap.put(te.getPos(), state);
			externalCounts.put(state, externalCounts.get(state) + 1);
		}
	}

	public void externalRemoved(BlockPos neighbor) {
		final ExternalState state = externalMap.get(neighbor);

		if(state != null) {
			externalCounts.put(state, externalCounts.get(state) - 1);
			externalMap.remove(neighbor);
		}
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

	public enum ExternalState {
		PRODUCER,
		CONSUMER;

		public static ExternalState resolve(IEnergyStorage capability) {
			if(capability.canExtract() && !capability.canReceive()) {
				return PRODUCER;
			}

			return CONSUMER;
		}
	}
}
