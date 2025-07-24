package org.tor_tik96.chronoline.Network.Craft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Craft.PlayerCraftProvider;

import java.util.function.Supplier;

public class ServerCraftPacketC2S {
    private final int craft;

    public ServerCraftPacketC2S(int craft) {
        this.craft = craft;
    }

    public static ServerCraftPacketC2S decode(FriendlyByteBuf buf) {
        int strength = buf.readInt();
        return new ServerCraftPacketC2S(strength);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(craft);
    }

    public static void handle(ServerCraftPacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            ServerPlayer player = context.get().getSender();
            player.getCapability(PlayerCraftProvider.PLAYER_CRAFT).ifPresent(craft -> {
                craft.setCraft(packet.craft);
                PacketHandler.sendToPlayer(new ClientCraftPacketS2C(craft.getCraft()), player);
            });
        });
        context.get().setPacketHandled(true);
    }
}
