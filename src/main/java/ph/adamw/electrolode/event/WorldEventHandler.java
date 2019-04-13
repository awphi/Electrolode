package ph.adamw.electrolode.event;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ph.adamw.electrolode.energy.network.EnergyNetworkSaveData;

@Mod.EventBusSubscriber
public class WorldEventHandler {
	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load event) {
		if(!event.getWorld().isRemote) {
			System.out.println("Binding...");
			EnergyNetworkSaveData.get(event.getWorld());
		}
	}
}
