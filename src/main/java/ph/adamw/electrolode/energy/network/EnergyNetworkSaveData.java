package ph.adamw.electrolode.energy.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import ph.adamw.electrolode.Electrolode;

import java.util.Map;
import java.util.UUID;

public class EnergyNetworkSaveData extends WorldSavedData {
	private static String DATA_NAME = Electrolode.MODID + "_energynetworks";

	public EnergyNetworkSaveData() {
		super(DATA_NAME);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		EnergyNetworkManager.getNetworks().clear();

		int c = 0;
		while(compound.hasKey("network" + c)) {
			EnergyNetworkManager.getNetworks().put(compound.getUniqueId("network" + c), EnergyNetwork.readFromNbt(compound.getCompoundTag("network" + c + "-value")));

			c ++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		final Map<UUID, EnergyNetwork> map = EnergyNetworkManager.getNetworks();

		int c = 0;
		for(UUID i : map.keySet()) {
			compound.setUniqueId("network" + c, i);
			compound.setTag("network" + c + "-value", map.get(i).writeToNbt(new NBTTagCompound()));
			c ++;
		}

		return compound;
	}

	public static EnergyNetworkSaveData get(World world) {
		final MapStorage storage = world.getMapStorage();
		EnergyNetworkSaveData instance = (EnergyNetworkSaveData) storage.getOrLoadData(EnergyNetworkSaveData.class, DATA_NAME);

		if (instance == null) {
			instance = new EnergyNetworkSaveData();
			storage.setData(DATA_NAME, instance);
		}

		return instance;
	}
}
