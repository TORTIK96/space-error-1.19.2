package org.tor_tik96.chronoline.Network.Rhetoric;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.ClientRhetoric;

import java.util.function.Supplier;

public class ClientRhetoricPacketS2C {
    private final int rhetoric;

    public ClientRhetoricPacketS2C(int rhetoric) {
        this.rhetoric = rhetoric;
    }

    public static ClientRhetoricPacketS2C decode(FriendlyByteBuf buf) {
        int rhetoric = buf.readInt();
        return new ClientRhetoricPacketS2C(rhetoric);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(rhetoric);
    }

    public static void handle(ClientRhetoricPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientRhetoric.setRhetoric(packet.rhetoric);
        });
        context.get().setPacketHandled(true);
    }
}
