package org.tor_tik96.chronoline.Network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.tor_tik96.chronoline.Config;

import java.util.function.Supplier;

public class SetCloseDoorPacketC2S {
    private final BlockPos pos;
    private final boolean add;

    public SetCloseDoorPacketC2S(BlockPos pos, boolean add) {
        this.pos = pos;
        this.add = add;
    }

    public static SetCloseDoorPacketC2S decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        boolean add = buf.readBoolean();
        return new SetCloseDoorPacketC2S(pos, add);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(add);
    }

    public static void handle(SetCloseDoorPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            if (ServerLifecycleHooks.getCurrentServer() != null) {
                for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                    PacketHandler.sendToPlayer(new SetCloseDoorPacketS2C(packet.pos, packet.add), player);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
