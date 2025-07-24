package org.tor_tik96.chronoline.Network.Strength;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;

import java.util.function.Supplier;

public class ClientStrengthPacketS2C {
    private final int strength;

    public ClientStrengthPacketS2C(int strength) {
        this.strength = strength;
    }

    public static ClientStrengthPacketS2C decode(FriendlyByteBuf buf) {
        int strength = buf.readInt();
        return new ClientStrengthPacketS2C(strength);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(strength);
    }

    public static void handle(ClientStrengthPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientStrength.setStrength(packet.strength);
        });
        context.get().setPacketHandled(true);
    }
}
