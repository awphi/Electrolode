package ph.adamw.electrolode.energy.network;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ph.adamw.electrolode.tile.channel.TileCable;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class EnergyNode {
	private final BlockPos cablePos;
	private final Set<EnergyNode> adjacencies = new HashSet<>();

	public EnergyNode(TileCable cable) {
		this.cablePos = cable.getPos();
	}

	public void addTwoWayAdjacency(EnergyNode node) {
		this.getAdjacencies().add(node);
		node.getAdjacencies().add(this);
	}

	@Nullable
	public TileCable getCable(World world) {
		final TileEntity te = world.getTileEntity(cablePos);

		if(te instanceof TileCable) {
			return (TileCable) te;
		}

		return null;
	}
}
