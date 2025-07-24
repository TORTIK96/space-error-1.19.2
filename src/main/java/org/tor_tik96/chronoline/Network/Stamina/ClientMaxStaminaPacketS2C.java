package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;

import java.util.function.Supplier;

public class ClientMaxStaminaPacketS2C {
    private final int maxStamina;

    public ClientMaxStaminaPacketS2C(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public static ClientMaxStaminaPacketS2C decode(FriendlyByteBuf buf) {
        int maxStamina = buf.readInt();
        return new ClientMaxStaminaPacketS2C(maxStamina);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(maxStamina);
    }

    public static void handle(ClientMaxStaminaPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientStamina.setMaxStamina(packet.maxStamina);
        });
        context.get().setPacketHandled(true);
    }
}
