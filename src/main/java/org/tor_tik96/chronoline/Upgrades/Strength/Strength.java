package org.tor_tik96.chronoline.Upgrades.Strength;

import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;

public class Strength {
    public static void addStrength(int add) {
        int strength = Math.min(ClientStrength.getStrength() + add, 500);
        PacketHandler.sendToServer(new ServerStrengthPacketC2S(strength));
    }

    public static void subStrength(int sub) {
        int strength = Math.max(ClientStrength.getStrength() - sub, 0);
        PacketHandler.sendToServer(new ServerStrengthPacketC2S(strength));
    }
}
