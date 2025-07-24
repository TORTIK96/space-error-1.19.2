package org.tor_tik96.chronoline.Upgrades.Strength;

public class ClientStrength {
    private static int strength = 0;

    public static int getStrength() {
        return strength;
    }

    public static void setStrength(int strength) {
        ClientStrength.strength = strength;
    }
}
