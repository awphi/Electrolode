package ph.adamw.electrolode.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import ph.adamw.electrolode.block.properties.BlockProperties;
import ph.adamw.electrolode.channel.ChannelTier;
import ph.adamw.electrolode.energy.network.EnergyNode;
import ph.adamw.electrolode.energy.network.EnergyRequest;
import ph.adamw.electrolode.tile.channel.TileEnergyChannel;
import ph.adamw.electrolode.util.WorldUtils;

public class ElectroEnergyChannel extends ElectroEnergyStorage {
	private final static int CAPACITY_MULTIPLIER = 5;

	public ElectroEnergyChannel(TileEnergyChannel tile, int transfer) {
		super(tile, transfer * CAPACITY_MULTIPLIER, transfer);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		final int c = super.receiveEnergy(maxReceive, simulate);

		if(!simulate) {
			((TileEnergyChannel) tile).getNetwork().energyPush(tile.getWorld(), tile.getPos(), maxReceive);
		}

		return c;
	}

	public int getTransfer() {
		return maxReceive;
	}

	public void routeEnergy(EnergyRequest request) {
		request.setBottleneck(extractEnergy(request.getBottleneck(), false));

		if(request.getBottleneck()== 0) {
			System.out.println("Energy transfer in network was bottlenecked to 0, cancelling routing...");
			return;
		}

		// i.e this is the last cable
		if(request.getRoute().isEmpty()) {
			final BlockPos to = request.getTo();
			final EnumFacing facing = WorldUtils.getFacingFrom(tile.getPos(), to);

			final TileEntity te = tile.getWorld().getTileEntity(to);
			if(te == null || !te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
				System.out.println("Tile entity either not found at " + to + " or has no energy capability after routing it there.");
				return;
			}

			te.getCapability(CapabilityEnergy.ENERGY, facing).receiveEnergy(request.getBottleneck(), false);
			return;
		}

		final EnergyNode next = request.getRoute().pop();
		final TileEnergyChannel tile = next.getTile(this.tile.getWorld());

		tile.getEnergy().receiveEnergyInternal(request.getBottleneck(), false);
		tile.routeEnergy(request);
	}

	public void setTier(ChannelTier value) {
		maxExtract = maxReceive = value.getEnergyTransfer();
		capacity = maxReceive * CAPACITY_MULTIPLIER;
	}
}
