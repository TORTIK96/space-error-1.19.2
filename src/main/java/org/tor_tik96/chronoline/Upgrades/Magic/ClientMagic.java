package org.tor_tik96.chronoline.Upgrades.Magic;

public class ClientMagic {
    private static int magic = 0;

    public static int getMagic() {
        return magic;
    }

    public static void setMagic(int magic) {
        ClientMagic.magic = magic;
    }
}
