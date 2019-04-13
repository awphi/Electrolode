package ph.adamw.electrolode.energy.network;

import lombok.Getter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.tile.channel.TileCable;
import ph.adamw.electrolode.util.WorldUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class EnergyNetworkManager {
	@Getter
	private static final Map<UUID, EnergyNetwork> networks = new HashMap<>();

	static void registerNetwork(EnergyNetwork net) {
		networks.put(net.getUuid(), net);
	}

	public static void unregsiterNetwork(EnergyNetwork network) {
		networks.remove(network.getUuid());
	}

	public static void nodeCreated(World world, TileCable cable) {
		final List<EnergyNetwork> applicableNetworks = new ArrayList<>();

		for(EnumFacing i : EnumFacing.VALUES) {
			final TileEntity tile = world.getTileEntity(cable.getPos().offset(i));
			if(tile instanceof TileCable) {
				final EnergyNetwork network = ((TileCable) tile).getNetwork();

				if(!applicableNetworks.contains(network) && network != null) {
					applicableNetworks.add(network);
				}
			}
		}

		if(applicableNetworks.size() == 0) {
			final EnergyNetwork net = new EnergyNetwork();
			net.add(cable, true);
		} else if(applicableNetworks.size() == 1) {
			applicableNetworks.get(0).add(cable, true);
		} else {
			final EnergyNetwork network = EnergyNetworkUtils.mergeNetworks(world, applicableNetworks);
			network.add(cable, true);
		}
	}

	public static void nodeRemoved(TileCable cable) {
		final EnergyNetwork network = cable.getNetwork();

		// i.e. if this removal destroyed the destroyed don't worry about checking for splinters since it's empty
		if(network.remove(cable, true)) {
			return;
		}

		final Set<EnergyNode> connectedNodes = network.getConnectedNodes();

		if(connectedNodes.size() != network.size()) {
			EnergyNetworkUtils.divideNetwork(cable.getWorld(), network, connectedNodes);
		}
	}

	public static void neighborChanged(IBlockAccess world, TileCable cable, BlockPos neighbor) {
		final TileEntity te = world.getTileEntity(neighbor);

		if(te instanceof TileCable || cable.getNetwork() == null) {
			return;
		}

		final EnumFacing dir = WorldUtils.getFacingFrom(cable.getPos(), neighbor);

		if(te == null) {
			cable.getNetwork().externalRemoved(neighbor);
		} else if(te.hasCapability(CapabilityEnergy.ENERGY, dir)) {
			cable.getNetwork().externalAdded(te, dir);
		}
	}

	public static EnergyNetwork getNetwork(UUID network) {
		return networks.get(network);
	}
}
