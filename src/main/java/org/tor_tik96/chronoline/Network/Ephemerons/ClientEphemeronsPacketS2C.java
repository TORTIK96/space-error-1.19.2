package org.tor_tik96.chronoline.Network.Ephemerons;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.ClientEphemerons;

import java.util.function.Supplier;

public class ClientEphemeronsPacketS2C {
    private final int ephemerons;

    public ClientEphemeronsPacketS2C(int ephemerons) {
        this.ephemerons = ephemerons;
    }

    public static ClientEphemeronsPacketS2C decode(FriendlyByteBuf buf) {
        int ephemerons = buf.readInt();
        return new ClientEphemeronsPacketS2C(ephemerons);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(ephemerons);
    }

    public static void handle(ClientEphemeronsPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientEphemerons.setEphemerons(packet.ephemerons);
        });
        context.get().setPacketHandled(true);
    }
}
