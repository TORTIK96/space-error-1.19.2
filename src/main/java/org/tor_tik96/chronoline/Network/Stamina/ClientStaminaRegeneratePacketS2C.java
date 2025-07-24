package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;

import java.util.function.Supplier;

public class ClientStaminaRegeneratePacketS2C {
    private final boolean regenerate;

    public ClientStaminaRegeneratePacketS2C(boolean regenerate) {
        this.regenerate = regenerate;
    }

    public static ClientStaminaRegeneratePacketS2C decode(FriendlyByteBuf buf) {
        boolean regenerate = buf.readBoolean();
        return new ClientStaminaRegeneratePacketS2C(regenerate);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(regenerate);
    }

    public static void handle(ClientStaminaRegeneratePacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientStamina.setRegenerate(packet.regenerate);
        });
        context.get().setPacketHandled(true);
    }
}
