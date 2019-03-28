package ph.adamw.electrolode.networking;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.Sys;
import ph.adamw.electrolode.block.EnumFaceRole;
import ph.adamw.electrolode.block.machine.TileBaseMachine;

@NoArgsConstructor
public class PacketSideConfigUpdate implements IMessage {
    BlockPos pos;
    EnumFaceRole role;
    EnumFacing direction;
    int containerIndex;

    public PacketSideConfigUpdate(BlockPos e, EnumFacing side, EnumFaceRole newRole, int index) {
        pos = e;
        role = newRole;
        direction = side;
        containerIndex = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        role = EnumFaceRole.getRole(buf.readInt());
        direction = EnumFacing.getFront(buf.readInt());
        containerIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(role.getValue());
        buf.writeInt(direction.getIndex());
        buf.writeInt(containerIndex);
    }

    public static class Handler implements IMessageHandler<PacketSideConfigUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketSideConfigUpdate message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSideConfigUpdate message, MessageContext ctx){
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            // Check if the chunk is loaded to prevent abuse
            if (world.isBlockLoaded(message.pos)) {
                TileBaseMachine te = (TileBaseMachine) world.getTileEntity(message.pos);
                if(te == null) return;
                te.faceMap.put(message.direction, message.role, message.containerIndex);
                te.markForUpdate();
            }
        }
    }
}
