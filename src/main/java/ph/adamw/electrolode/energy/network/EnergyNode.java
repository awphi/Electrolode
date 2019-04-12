package ph.adamw.electrolode.energy.network;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTBase;
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
import java.util.UUID;

@Setter
@Getter
public class EnergyNode {
	private final BlockPos cablePos;
	private final Set<EnergyNode> adjacencies = new HashSet<>();

	private static final Map<Long, EnergyNode> nodeCache = new HashMap<>();
	private static final Map<Long, NBTTagCompound> serializedCache = new HashMap<>();

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
		final long pos = compound.getLong("cablePos");

		if(nodeCache.containsKey(pos)) {
			return nodeCache.get(pos);
		}

		final EnergyNode node = new EnergyNode(BlockPos.fromLong(pos));
		nodeCache.put(pos, node);

		int c = 0;
		while(compound.hasKey("adjacency" + c)) {
			node.adjacencies.add(readFromNbt(compound.getCompoundTag("adjacency" + c)));
			c ++;
		}

		return node;
	}

	public NBTTagCompound writeToNbt(NBTTagCompound compound) {
		long pos = cablePos.toLong();

		if(serializedCache.containsKey(pos)) {
			return serializedCache.get(pos);
		}

		compound.setLong("cablePos", pos);
		serializedCache.put(pos, compound);

		int c = 0;
		for(EnergyNode i : adjacencies) {
			compound.setTag("adjacency" + c, i.writeToNbt(new NBTTagCompound()));
			c ++;
		}

		return compound;
	}
}
