package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStaminaProvider;

import java.util.function.Supplier;

public class ServerStaminaPacketC2S {
    private final int stamina;

    public ServerStaminaPacketC2S(int stamina) {
        this.stamina = stamina;
    }

    public static ServerStaminaPacketC2S decode(FriendlyByteBuf buf) {
        int stamina = buf.readInt();
        return new ServerStaminaPacketC2S(stamina);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(stamina);
    }

    public static void handle(ServerStaminaPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                stamina.setStamina(packet.stamina);
                PacketHandler.sendToPlayer(new ClientStaminaPacketS2C(stamina.getStamina()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
