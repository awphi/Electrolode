package ph.adamw.electrolode.energy.network;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Stack;

@Getter
public class EnergyRequest {
	private final int amount;
	private final EnergyNode from;
	private final BlockPos to;

	private Stack<EnergyNode> route;

	@Setter
	private int bottleneck;

	public EnergyRequest(int amount, EnergyNode from, BlockPos to) {
		this.amount = this.bottleneck = amount;
		this.from = from;
		this.to = to;
	}

	private Stack<EnergyNode> resolveRoute(EnergyNode from, EnergyNode toCable) {
		final Stack<EnergyNode> route = new Stack<>();

		route.add(from);

		if(from != toCable) {
			route.add(toCable);
		}

		return route;
	}

	public void execute(World world, EnergyNetwork network) {
		final EnergyNode toNode = network.resolveFirstNodeNextTo(to);

		if(toNode == null) {
			System.err.println("Failed to resolve energy node next to " + to.toString());
			return;
		}

		route = resolveRoute(from, toNode);
		route.pop().getChannelTile(world).routeEnergy(this);
	}
}
