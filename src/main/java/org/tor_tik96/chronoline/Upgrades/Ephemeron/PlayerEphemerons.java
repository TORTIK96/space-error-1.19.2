package org.tor_tik96.chronoline.Upgrades.Ephemeron;

import net.minecraft.nbt.CompoundTag;

public class PlayerEphemerons {
    private int ephemerons;

    public void setEphemerons(int ephemerons) {
        this.ephemerons = ephemerons;
    }

    public int getEphemerons() {
        return ephemerons;
    }

    public void copyFrom(PlayerEphemerons source) {
        this.ephemerons = source.ephemerons;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("ephemerons", ephemerons);

    }

    public void loadNBTData(CompoundTag tag) {
        ephemerons = tag.getInt("ephemerons");
    }
}
