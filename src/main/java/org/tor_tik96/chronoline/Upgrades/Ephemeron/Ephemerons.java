package org.tor_tik96.chronoline.Upgrades.Ephemeron;

import org.tor_tik96.chronoline.Network.Ephemerons.ServerEphemeronsPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;

public class Ephemerons {
    public static void addEphemerons(int add) {
        int ephemerons = ClientEphemerons.getEphemerons() + add;
        PacketHandler.sendToServer(new ServerEphemeronsPacketC2S(ephemerons));
    }

    public static void subEphemerons(int sub) {
        int ephemerons = ClientEphemerons.getEphemerons() - sub;
        PacketHandler.sendToServer(new ServerEphemeronsPacketC2S(ephemerons));
    }
}
