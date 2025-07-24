package org.tor_tik96.chronoline.Upgrades.Rhetoric;

import org.tor_tik96.chronoline.Network.Magic.ServerMagicPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Rhetoric.ServerRhetoricPacketC2S;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;

public class Rhetoric {
    public static void addRhetoric(int add) {
        int rhetoric = Math.min(ClientRhetoric.getRhetoric() + add, 500);
        PacketHandler.sendToServer(new ServerRhetoricPacketC2S(rhetoric));
    }

    public static void subRhetoric(int sub) {
        int rhetoric = Math.max(ClientRhetoric.getRhetoric()  - sub, 0);
        PacketHandler.sendToServer(new ServerRhetoricPacketC2S(rhetoric));
    }
}
