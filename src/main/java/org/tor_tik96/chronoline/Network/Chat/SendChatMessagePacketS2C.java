package org.tor_tik96.chronoline.Network.Chat;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.hud.Chat.Chat;
import org.tor_tik96.chronoline.hud.RegisterHUD;

import java.util.function.Supplier;

public class SendChatMessagePacketS2C {
    private final String sender;
    private final String message;
    private final String senderColor;

    public SendChatMessagePacketS2C(String sender, String message, String senderColor) {
        this.sender = sender;
        this.message = message;
        this.senderColor = senderColor;
    }

    public static SendChatMessagePacketS2C decode(FriendlyByteBuf buf) {
        String sender = buf.readUtf();
        String message = buf.readUtf();
        String senderColor = buf.readUtf();
        return new SendChatMessagePacketS2C(sender, message, senderColor);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(sender);
        buf.writeUtf(message);
        buf.writeUtf(senderColor);
    }

    public static void handle(SendChatMessagePacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            if (RegisterHUD.generalHUD != null) {
                Chat chat = RegisterHUD.generalHUD.getMyChat();
                if (chat != null) {
                    chat.addMessage(packet.sender, packet.message, packet.senderColor);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}