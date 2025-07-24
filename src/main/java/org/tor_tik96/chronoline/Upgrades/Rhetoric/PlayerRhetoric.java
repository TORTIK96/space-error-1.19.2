package org.tor_tik96.chronoline.Upgrades.Rhetoric;

import net.minecraft.nbt.CompoundTag;

public class PlayerRhetoric {
    private int rhetoric;

    public void setRhetoric(int rhetoric) {
        this.rhetoric = rhetoric;
    }

    public int getRhetoric() {
        return rhetoric;
    }

    public void copyFrom(PlayerRhetoric source) {
        this.rhetoric = source.rhetoric;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("rhetoric", rhetoric);

    }

    public void loadNBTData(CompoundTag tag) {
        rhetoric = tag.getInt("rhetoric");
    }
}
