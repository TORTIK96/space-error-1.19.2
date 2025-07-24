package org.tor_tik96.chronoline.Network.Magic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;

import java.util.function.Supplier;

public class ClientMagicPacketS2C {
    private final int magic;

    public ClientMagicPacketS2C(int magic) {
        this.magic = magic;
    }

    public static ClientMagicPacketS2C decode(FriendlyByteBuf buf) {
        int magic = buf.readInt();
        return new ClientMagicPacketS2C(magic);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(magic);
    }

    public static void handle(ClientMagicPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientMagic.setMagic(packet.magic);
        });
        context.get().setPacketHandled(true);
    }
}
