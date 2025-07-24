package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStaminaProvider;

import java.util.function.Supplier;

public class ServerStaminaRegeneratePacketC2S {
    private final boolean regenerate;

    public ServerStaminaRegeneratePacketC2S(boolean regenerate) {
        this.regenerate = regenerate;
    }

    public static ServerStaminaRegeneratePacketC2S decode(FriendlyByteBuf buf) {
        boolean regenerate = buf.readBoolean();
        return new ServerStaminaRegeneratePacketC2S(regenerate);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(regenerate);
    }

    public static void handle(ServerStaminaRegeneratePacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                stamina.setRegenerate(packet.regenerate);
                PacketHandler.sendToPlayer(new ClientStaminaRegeneratePacketS2C(stamina.isRegenerate()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
