package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStaminaProvider;

import java.util.function.Supplier;

public class ServerMaxStaminaPacketC2S {
    private final int maxStamina;

    public ServerMaxStaminaPacketC2S(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public static ServerMaxStaminaPacketC2S decode(FriendlyByteBuf buf) {
        int maxStamina = buf.readInt();
        return new ServerMaxStaminaPacketC2S(maxStamina);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(maxStamina);
    }

    public static void handle(ServerMaxStaminaPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                stamina.setMaxStamina(packet.maxStamina);
                PacketHandler.sendToPlayer(new ClientMaxStaminaPacketS2C(stamina.getStamina()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
