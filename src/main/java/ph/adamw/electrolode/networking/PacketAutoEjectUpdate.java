package ph.adamw.electrolode.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ph.adamw.electrolode.block.machines.TileBaseMachine;

public class PacketAutoEjectUpdate implements IMessage {
    BlockPos pos;
    boolean enabled;

    public PacketAutoEjectUpdate(BlockPos e, boolean enabled) {
        this.enabled = enabled;
        pos = e;

    }

    public PacketAutoEjectUpdate() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        enabled = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeBoolean(enabled);
    }

    public static class Handler implements IMessageHandler<PacketAutoEjectUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketAutoEjectUpdate message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketAutoEjectUpdate message, MessageContext ctx){
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            // Check if the chunk is loaded to prevent abuse
            if (world.isBlockLoaded(message.pos)) {
                TileBaseMachine te = (TileBaseMachine) world.getTileEntity(message.pos);
                if(te == null) return;
                te.autoEject = message.enabled;
                te.markForUpdate();
            }
        }
    }
}
