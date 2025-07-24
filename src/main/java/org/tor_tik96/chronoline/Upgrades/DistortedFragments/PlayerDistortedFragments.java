package org.tor_tik96.chronoline.Upgrades.DistortedFragments;

import net.minecraft.nbt.CompoundTag;
import org.tor_tik96.chronoline.Upgrades.Magic.PlayerMagic;

public class PlayerDistortedFragments {
    private int fragments;

    public void setFragments(int fragments) {
        this.fragments = fragments;
    }

    public int getFragments() {
        return fragments;
    }

    public void copyFrom(PlayerDistortedFragments source) {
        this.fragments = source.fragments;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("distorted_fragments", fragments);

    }

    public void loadNBTData(CompoundTag tag) {
        fragments = tag.getInt("distorted_fragments");
    }
}
