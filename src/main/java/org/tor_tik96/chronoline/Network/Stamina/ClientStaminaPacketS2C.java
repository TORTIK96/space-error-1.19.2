package org.tor_tik96.chronoline.Network.Stamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;

import java.util.function.Supplier;

public class ClientStaminaPacketS2C {
    private final int stamina;

    public ClientStaminaPacketS2C(int stamina) {
        this.stamina = stamina;
    }

    public static ClientStaminaPacketS2C decode(FriendlyByteBuf buf) {
        int stamina = buf.readInt();
        return new ClientStaminaPacketS2C(stamina);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(stamina);
    }

    public static void handle(ClientStaminaPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientStamina.setStamina(packet.stamina);
        });
        context.get().setPacketHandled(true);
    }
}
