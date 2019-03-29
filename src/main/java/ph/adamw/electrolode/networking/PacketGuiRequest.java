package ph.adamw.electrolode.networking;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ph.adamw.electrolode.Electrolode;

@NoArgsConstructor
public class PacketGuiRequest implements IMessage {
	int id;
	EntityPlayer player;
	BlockPos pos;

	public PacketGuiRequest(int id, EntityPlayer entityPlayer, BlockPos pos) {
		this.id = id;
		this.player = entityPlayer;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		int nameLength = buf.readInt();
		byte[] name = new byte[nameLength];
		buf.readBytes(name);
		player = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getPlayerList().getPlayerByUsername(new String(name));
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(player.getName().getBytes().length);
		buf.writeBytes(player.getName().getBytes());
		buf.writeLong(pos.toLong());
	}

	public static class Handler implements IMessageHandler<PacketGuiRequest, IMessage> {
		@Override
		public IMessage onMessage(PacketGuiRequest message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketGuiRequest message, MessageContext ctx) {
			message.player.openGui(Electrolode.instance, message.id, message.player.world, message.pos.getX(), message.pos.getY(), message.pos.getZ());
		}
	}
}
