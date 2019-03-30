package ph.adamw.electrolode.networking;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ph.adamw.electrolode.Electrolode;

import java.util.UUID;

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

		int uuidLength = buf.readInt();
		byte[] uuid = new byte[uuidLength];

		buf.readBytes(uuid);
		player = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getPlayerList().getPlayerByUUID(UUID.fromString(new String(uuid)));
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] uuid = player.getUniqueID().toString().getBytes();

		buf.writeInt(id);
		buf.writeInt(uuid.length);
		buf.writeBytes(uuid);
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
