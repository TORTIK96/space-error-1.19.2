package org.tor_tik96.chronoline.Network.Chat;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.SetCloseDoorPacketC2S;
import org.tor_tik96.chronoline.Network.SetCloseDoorPacketS2C;

import java.util.function.Supplier;

public class SendChatMessagePacketC2S {
    private final String sender;
    private final String message;
    private final String senderColor;

    public SendChatMessagePacketC2S(String sender, String message, String senderColor) {
        this.sender = sender;
        this.message = message;
        this.senderColor = senderColor;
    }

    public static SendChatMessagePacketC2S decode(FriendlyByteBuf buf) {
        String sender = buf.readUtf();
        String message = buf.readUtf();
        String senderColor = buf.readUtf();
        return new SendChatMessagePacketC2S(sender, message, senderColor);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(sender);
        buf.writeUtf(message);
        buf.writeUtf(senderColor);
    }

    public static void handle(SendChatMessagePacketC2S packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА СЕРВЕРЕ
            if (ServerLifecycleHooks.getCurrentServer() != null) {
                for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                    PacketHandler.sendToPlayer(new SendChatMessagePacketS2C(packet.sender, packet.message, packet.senderColor), player);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
