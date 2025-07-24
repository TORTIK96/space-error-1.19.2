package org.tor_tik96.chronoline.Upgrades.DistortedFragments;

import org.tor_tik96.chronoline.Network.DistortedFragments.ServerDistortedFragmentsPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;

public class DistortedFragments {
    public static void addDistortedFragments(int add) {
        int distortedFragments = ClientDistortedFragments.getDistortedFragments() + add;
        PacketHandler.sendToServer(new ServerDistortedFragmentsPacketC2S(distortedFragments));
    }

    public static void subDistortedFragments(int sub) {
        int distortedFragments = ClientDistortedFragments.getDistortedFragments()  - sub;
        PacketHandler.sendToServer(new ServerDistortedFragmentsPacketC2S(distortedFragments));
    }
}
