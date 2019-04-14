package ph.adamw.electrolode.energy.network;

import net.minecraft.world.World;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;

import java.util.List;
import java.util.Set;

public class EnergyNetworkUtils {
	public static EnergyNetwork mergeNetworks(World world, List<EnergyNetwork> networks) {
		EnergyNetwork network = networks.get(1).mergeInto(world, networks.get(0));

		int i = 2;
		while(i + 1 < networks.size()) {
			final EnergyNetwork j = networks.get(i + 1);
			network = j.mergeInto(world, network);

			i ++;
		}

		return network;
	}

	public static EnergyNetwork divideNetwork(World world, EnergyNetwork network, Set<EnergyNode> subnet) {
		final EnergyNetwork newNetwork = new EnergyNetwork();

		for(EnergyNode i : subnet) {
			final TileEnergyChannel te = i.getChannelTile(world);

			newNetwork.add(te, false);
			network.remove(te, false);

			i.getChannelTile(world).setNetwork(newNetwork);
		}

		return newNetwork;
	}
}
