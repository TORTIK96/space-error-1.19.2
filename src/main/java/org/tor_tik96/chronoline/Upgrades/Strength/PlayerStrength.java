package org.tor_tik96.chronoline.Upgrades.Strength;

import net.minecraft.nbt.CompoundTag;

public class PlayerStrength {
    private int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void copyFrom(PlayerStrength source) {
        this.strength = source.strength;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("strength", strength);
    }

    public void loadNBTData(CompoundTag tag) {
        strength = tag.getInt("strength");
    }
}
