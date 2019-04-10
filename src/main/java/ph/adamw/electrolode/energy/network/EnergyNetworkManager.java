package ph.adamw.electrolode.energy.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ph.adamw.electrolode.tile.TileCable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EnergyNetworkManager {
	//To be used for serialization ultimately
	private static final List<EnergyNetwork> networks = new LinkedList<>();

	private static void registerNetwork(TileCable... cables) {
		final EnergyNetwork net = new EnergyNetwork();

		for(TileCable i : cables) {
			net.add(i, true);
		}

		networks.add(net);
	}

	public static void unregisterNetwork(EnergyNetwork network) {
		networks.remove(network);
	}

	public static void nodeCreated(World world, TileCable cable) {
		final List<EnergyNetwork> applicableNetworks = new ArrayList<>();

		for(EnumFacing i : EnumFacing.VALUES) {
			final TileEntity tile = world.getTileEntity(cable.getPos().offset(i));
			if(tile instanceof TileCable) {
				applicableNetworks.add(((TileCable) tile).getNetwork());
			}
		}

		if(applicableNetworks.size() == 0) {
			registerNetwork(cable);
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

		if(network.isSplintered(connectedNodes)) {
			EnergyNetworkUtils.splitNetwork(cable.getWorld(), network, connectedNodes);
		}
	}

	public static void neighborChanged(IBlockAccess world, TileCable cable, BlockPos neighbor) {
		final TileEntity te = world.getTileEntity(neighbor);

		System.out.println(world.getClass().getSimpleName());

		if(te instanceof TileCable || cable.getNetwork() == null) {
			return;
		}

		final BlockPos cablePos = cable.getPos();
		final EnumFacing dir = EnumFacing.getFacingFromVector(
				(float) (cablePos.getX() - neighbor.getX()),
				(float) (cablePos.getY() - neighbor.getY()),
				(float) (cablePos.getZ() - neighbor.getZ()));

		if(te == null) {
			cable.getNetwork().externalRemoved(neighbor);
		} else if(te.hasCapability(CapabilityEnergy.ENERGY, dir)) {
			cable.getNetwork().externalAdded(te, dir);
		}
	}
}
