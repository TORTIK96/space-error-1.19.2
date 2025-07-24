package org.tor_tik96.chronoline.Network.DistortedFragments;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.PlayerDistortedFragmentsProvider;

import java.util.function.Supplier;

public class ServerDistortedFragmentsPacketC2S {
    private final int distortedFragments;

    public ServerDistortedFragmentsPacketC2S(int distortedFragments) {
        this.distortedFragments = distortedFragments;
    }

    public static ServerDistortedFragmentsPacketC2S decode(FriendlyByteBuf buf) {
        int distortedFragments = buf.readInt();
        return new ServerDistortedFragmentsPacketC2S(distortedFragments);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(distortedFragments);
    }

    public static void handle(ServerDistortedFragmentsPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerDistortedFragmentsProvider.PLAYER_DISTORTED_FRAGMENTS).ifPresent(distortedFragments -> {
                distortedFragments.setFragments(packet.distortedFragments);
                PacketHandler.sendToPlayer(new ClientDistortedFragmentsPacketS2C(distortedFragments.getFragments()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
