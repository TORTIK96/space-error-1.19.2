package org.tor_tik96.chronoline.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Network.Craft.ClientCraftPacketS2C;
import org.tor_tik96.chronoline.Network.Craft.ServerCraftPacketC2S;
import org.tor_tik96.chronoline.Network.DistortedFragments.ClientDistortedFragmentsPacketS2C;
import org.tor_tik96.chronoline.Network.DistortedFragments.ServerDistortedFragmentsPacketC2S;
import org.tor_tik96.chronoline.Network.Magic.ClientMagicPacketS2C;
import org.tor_tik96.chronoline.Network.Magic.ServerMagicPacketC2S;
import org.tor_tik96.chronoline.Network.Rhetoric.ClientRhetoricPacketS2C;
import org.tor_tik96.chronoline.Network.Rhetoric.ServerRhetoricPacketC2S;
import org.tor_tik96.chronoline.Network.Stamina.*;
import org.tor_tik96.chronoline.Network.Strength.ClientStrengthPacketS2C;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.ClientDistortedFragments;

public class PacketHandler {
    public static SimpleChannel CHANNEL;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "main"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();


        CHANNEL = net;

        CHANNEL.registerMessage(id(), ServerStaminaPacketC2S.class,
                ServerStaminaPacketC2S::encode,
                ServerStaminaPacketC2S::decode,
                ServerStaminaPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientStaminaPacketS2C.class,
                ClientStaminaPacketS2C::encode,
                ClientStaminaPacketS2C::decode,
                ClientStaminaPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerMaxStaminaPacketC2S.class,
                ServerMaxStaminaPacketC2S::encode,
                ServerMaxStaminaPacketC2S::decode,
                ServerMaxStaminaPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientMaxStaminaPacketS2C.class,
                ClientMaxStaminaPacketS2C::encode,
                ClientMaxStaminaPacketS2C::decode,
                ClientMaxStaminaPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerStaminaRegeneratePacketC2S.class,
                ServerStaminaRegeneratePacketC2S::encode,
                ServerStaminaRegeneratePacketC2S::decode,
                ServerStaminaRegeneratePacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientStaminaRegeneratePacketS2C.class,
                ClientStaminaRegeneratePacketS2C::encode,
                ClientStaminaRegeneratePacketS2C::decode,
                ClientStaminaRegeneratePacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerStrengthPacketC2S.class,
                ServerStrengthPacketC2S::encode,
                ServerStrengthPacketC2S::decode,
                ServerStrengthPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientStrengthPacketS2C.class,
                ClientStrengthPacketS2C::encode,
                ClientStrengthPacketS2C::decode,
                ClientStrengthPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerCraftPacketC2S.class,
                ServerCraftPacketC2S::encode,
                ServerCraftPacketC2S::decode,
                ServerCraftPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientCraftPacketS2C.class,
                ClientCraftPacketS2C::encode,
                ClientCraftPacketS2C::decode,
                ClientCraftPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerMagicPacketC2S.class,
                ServerMagicPacketC2S::encode,
                ServerMagicPacketC2S::decode,
                ServerMagicPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientMagicPacketS2C.class,
                ClientMagicPacketS2C::encode,
                ClientMagicPacketS2C::decode,
                ClientMagicPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerRhetoricPacketC2S.class,
                ServerRhetoricPacketC2S::encode,
                ServerRhetoricPacketC2S::decode,
                ServerRhetoricPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientRhetoricPacketS2C.class,
                ClientRhetoricPacketS2C::encode,
                ClientRhetoricPacketS2C::decode,
                ClientRhetoricPacketS2C::handle);

        CHANNEL.registerMessage(id(), ServerDistortedFragmentsPacketC2S.class,
                ServerDistortedFragmentsPacketC2S::encode,
                ServerDistortedFragmentsPacketC2S::decode,
                ServerDistortedFragmentsPacketC2S::handle);

        CHANNEL.registerMessage(id(), ClientDistortedFragmentsPacketS2C.class,
                ClientDistortedFragmentsPacketS2C::encode,
                ClientDistortedFragmentsPacketS2C::decode,
                ClientDistortedFragmentsPacketS2C::handle);
    }

    public static <MSG> void sendToServer(MSG msg) {
        if (Minecraft.getInstance().getConnection() != null) {
            CHANNEL.sendToServer(msg);
        }
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        if (Minecraft.getInstance().getConnection() != null) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }
}
