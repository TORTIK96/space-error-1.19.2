package org.tor_tik96.chronoline.Upgrades.DistortedFragments;

public class ClientDistortedFragments {
    private static int distortedFragments = 0;

    public static void setDistortedFragments(int distortedFragments) {
        ClientDistortedFragments.distortedFragments = distortedFragments;
    }

    public static int getDistortedFragments() {
        return distortedFragments;
    }
}
