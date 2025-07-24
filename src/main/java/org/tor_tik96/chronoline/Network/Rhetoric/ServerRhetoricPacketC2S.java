package org.tor_tik96.chronoline.Network.Rhetoric;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.PlayerRhetoricProvider;

import java.util.function.Supplier;

public class ServerRhetoricPacketC2S {
    private final int rhetoric;

    public ServerRhetoricPacketC2S(int rhetoric) {
        this.rhetoric = rhetoric;
    }

    public static ServerRhetoricPacketC2S decode(FriendlyByteBuf buf) {
        int rhetoric = buf.readInt();
        return new ServerRhetoricPacketC2S(rhetoric);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(rhetoric);
    }

    public static void handle(ServerRhetoricPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerRhetoricProvider.PLAYER_RHETORIC).ifPresent(rhetoric -> {
                rhetoric.setRhetoric(packet.rhetoric);
                PacketHandler.sendToPlayer(new ClientRhetoricPacketS2C(rhetoric.getRhetoric()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
