package org.tor_tik96.chronoline.Upgrades.Ephemeron;

public class ClientEphemerons {
    private static int ephemerons = 0;

    public static void setEphemerons(int ephemerons) {
        ClientEphemerons.ephemerons = ephemerons;
    }

    public static int getEphemerons() {
        return ephemerons;
    }
}
