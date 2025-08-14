package org.tor_tik96.chronoline.Network.Ephemerons;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.PlayerEphemeronsProvider;

import java.util.function.Supplier;

public class ServerEphemeronsPacketC2S {
    private final int ephemerons;

    public ServerEphemeronsPacketC2S(int ephemerons) {
        this.ephemerons = ephemerons;
    }

    public static ServerEphemeronsPacketC2S decode(FriendlyByteBuf buf) {
        int ephemerons = buf.readInt();
        return new ServerEphemeronsPacketC2S(ephemerons);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(ephemerons);
    }

    public static void handle(ServerEphemeronsPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).ifPresent(playerEphemerons -> {
                playerEphemerons.setEphemerons(packet.ephemerons);
                PacketHandler.sendToPlayer(new ClientEphemeronsPacketS2C(playerEphemerons.getEphemerons()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
