package org.tor_tik96.chronoline.Upgrades.Craft;

public class ClientCraft {
    private static int craft = 0;

    public static void setCraft(int craft) {
        ClientCraft.craft = craft;
    }

    public static int getCraft() {
        return craft;
    }
}
