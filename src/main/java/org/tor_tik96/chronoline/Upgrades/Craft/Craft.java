package org.tor_tik96.chronoline.Upgrades.Craft;

import org.tor_tik96.chronoline.Network.Craft.ServerCraftPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;

public class Craft {
    public static void addCraft(int add) {
        int craft = Math.min(ClientCraft.getCraft() + add, 500);
        PacketHandler.sendToServer(new ServerCraftPacketC2S(craft));
    }

    public static void subCraft(int sub) {
        int craft = Math.max(ClientCraft.getCraft() - sub, 0);
        PacketHandler.sendToServer(new ServerCraftPacketC2S(craft));
    }
}
