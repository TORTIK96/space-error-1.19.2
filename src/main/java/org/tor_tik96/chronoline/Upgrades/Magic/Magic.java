package org.tor_tik96.chronoline.Upgrades.Magic;

import org.tor_tik96.chronoline.Network.Magic.ServerMagicPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
public class Magic {
    public static void addMagic(int add) {
        int magic = Math.min(ClientMagic.getMagic() + add, 500);
        PacketHandler.sendToServer(new ServerMagicPacketC2S(magic));
    }

    public static void subMagic(int sub) {
        int magic = Math.max(ClientMagic.getMagic()  - sub, 0);
        PacketHandler.sendToServer(new ServerMagicPacketC2S(magic));
    }
}
