package org.tor_tik96.chronoline.Network.DistortedFragments;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.ClientDistortedFragments;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;

import java.util.function.Supplier;

public class ClientDistortedFragmentsPacketS2C {
    private final int distortedFragments;

    public ClientDistortedFragmentsPacketS2C(int distortedFragments) {
        this.distortedFragments = distortedFragments;
    }

    public static ClientDistortedFragmentsPacketS2C decode(FriendlyByteBuf buf) {
        int distortedFragments = buf.readInt();
        return new ClientDistortedFragmentsPacketS2C(distortedFragments);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(distortedFragments);
    }

    public static void handle(ClientDistortedFragmentsPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientDistortedFragments.setDistortedFragments(packet.distortedFragments);
        });
        context.get().setPacketHandled(true);
    }
}
