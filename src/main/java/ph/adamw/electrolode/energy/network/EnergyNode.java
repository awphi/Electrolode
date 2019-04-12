package ph.adamw.electrolode.energy.network;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ph.adamw.electrolode.tile.channel.TileCable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class EnergyNode {
	private final BlockPos cablePos;
	private final Set<EnergyNode> adjacencies = new HashSet<>();

	private static final Map<Long, EnergyNode> nodeCache = new HashMap<>();

	public EnergyNode(BlockPos pos) {
		this.cablePos = pos;
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

	public static EnergyNode readFromNbt(NBTTagCompound compound) {
		final EnergyNode node = buildOrRetrieveLoneNode(compound.getLong("cablePos"));

		int c = 0;
		while(compound.hasKey("adjacency" + c)) {
			node.adjacencies.add(buildOrRetrieveLoneNode(compound.getLong("adjacency" + c)));
			c ++;
		}

		return node;
	}

	private static EnergyNode buildOrRetrieveLoneNode(long pos) {
		if(nodeCache.containsKey(pos)) {
			return nodeCache.get(pos);
		}

		final EnergyNode node = new EnergyNode(BlockPos.fromLong(pos));
		nodeCache.put(pos, node);
		return node;
	}

	public NBTTagCompound writeToNbt(NBTTagCompound compound) {
		long pos = cablePos.toLong();

		compound.setLong("cablePos", pos);

		int c = 0;
		for(EnergyNode i : adjacencies) {
			compound.setLong("adjacency" + c, i.getCablePos().toLong());
			c ++;
		}

		return compound;
	}
}
