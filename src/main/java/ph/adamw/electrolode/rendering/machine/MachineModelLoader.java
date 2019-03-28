package ph.adamw.electrolode.rendering.machine;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import ph.adamw.electrolode.Electrolode;

import java.util.HashSet;
import java.util.Set;

public class MachineModelLoader implements ICustomModelLoader {
	public static final MachineModel MODEL = new MachineModel();
	public static final Set<ResourceLocation> machineTextures = new HashSet<>();

	public static void registerMachineTextures() {
		registerMachineTexture("blocks/machine/base");
		registerMachineTexture("blocks/machine/purifier_front");
	}

	public static void registerMachineTexture(String location) {
		final ResourceLocation rl = new ResourceLocation(Electrolode.MODID, location);
		machineTextures.add(rl);
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(Electrolode.MODID)
				&& modelLocation.getResourcePath().startsWith("machine_");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) {
		return MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}
}
