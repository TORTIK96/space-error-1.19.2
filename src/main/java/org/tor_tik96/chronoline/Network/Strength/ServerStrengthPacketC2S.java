package org.tor_tik96.chronoline.Network.Strength;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Strength.PlayerStrengthProvider;

import java.util.function.Supplier;

public class ServerStrengthPacketC2S {
    private final int strength;

    public ServerStrengthPacketC2S(int strength) {
        this.strength = strength;
    }

    public static ServerStrengthPacketC2S decode(FriendlyByteBuf buf) {
        int strength = buf.readInt();
        return new ServerStrengthPacketC2S(strength);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(strength);
    }

    public static void handle(ServerStrengthPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerStrengthProvider.PLAYER_STRENGTH).ifPresent(strength -> {
                strength.setStrength(packet.strength);
                PacketHandler.sendToPlayer(new ClientStrengthPacketS2C(strength.getStrength()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
