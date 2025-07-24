package org.tor_tik96.chronoline.Network.Magic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Magic.PlayerMagicProvider;

import java.util.function.Supplier;

public class ServerMagicPacketC2S {
    private final int magic;

    public ServerMagicPacketC2S(int magic) {
        this.magic = magic;
    }

    public static ServerMagicPacketC2S decode(FriendlyByteBuf buf) {
        int magic = buf.readInt();
        return new ServerMagicPacketC2S(magic);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(magic);
    }

    public static void handle(ServerMagicPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                magic.setMagic(packet.magic);
                PacketHandler.sendToPlayer(new ClientMagicPacketS2C(magic.getMagic()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
