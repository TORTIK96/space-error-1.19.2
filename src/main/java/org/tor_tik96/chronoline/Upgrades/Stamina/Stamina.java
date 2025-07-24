package org.tor_tik96.chronoline.Upgrades.Stamina;

import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Stamina.ServerMaxStaminaPacketC2S;
import org.tor_tik96.chronoline.Network.Stamina.ServerStaminaPacketC2S;
import org.tor_tik96.chronoline.Network.Stamina.ServerStaminaRegeneratePacketC2S;

public class Stamina {

    public static void addStamina(int add, int maxStamina) {
        int stamina = Math.min(ClientStamina.getStamina() + add, maxStamina);
        PacketHandler.sendToServer(new ServerStaminaPacketC2S(stamina));
    }

    public static void subStamina(int sub) {
        int stamina = Math.max(ClientStamina.getStamina() - sub, 0);
        PacketHandler.sendToServer(new ServerStaminaPacketC2S(stamina));
    }

    public static void setMaxStamina(int maxStamina) {
        PacketHandler.sendToServer(new ServerMaxStaminaPacketC2S(maxStamina));
    }

    public static void setRegenerate(boolean regenerate) {
        PacketHandler.sendToServer(new ServerStaminaRegeneratePacketC2S(regenerate));
    }



}
