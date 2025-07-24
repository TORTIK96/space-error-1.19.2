package org.tor_tik96.chronoline.Network.Craft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Upgrades.Craft.ClientCraft;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;

import java.util.function.Supplier;

public class ClientCraftPacketS2C {
    private final int craft;

    public ClientCraftPacketS2C(int craft) {
        this.craft = craft;
    }

    public static ClientCraftPacketS2C decode(FriendlyByteBuf buf) {
        int craft = buf.readInt();
        return new ClientCraftPacketS2C(craft);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(craft);
    }

    public static void handle(ClientCraftPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            ClientCraft.setCraft(packet.craft);
        });
        context.get().setPacketHandled(true);
    }
}
