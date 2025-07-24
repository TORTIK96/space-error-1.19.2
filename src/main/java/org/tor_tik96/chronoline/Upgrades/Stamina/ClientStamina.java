package org.tor_tik96.chronoline.Upgrades.Stamina;

public class ClientStamina {
    private static int stamina = 0;
    private static int maxStamina = 100;
    private static boolean regenerate = true;

    public static void setStamina(int stamina) {
        ClientStamina.stamina = stamina;
    }

    public static void setMaxStamina(int maxStamina) {
        ClientStamina.maxStamina = maxStamina;
    }

    public static void setRegenerate(boolean regenerate) {
        ClientStamina.regenerate = regenerate;
    }

    public static int getStamina() {
        return stamina;
    }

    public static int getMaxStamina() {
        return maxStamina;
    }

    public static boolean isRegenerate() {
        return regenerate;
    }
}
