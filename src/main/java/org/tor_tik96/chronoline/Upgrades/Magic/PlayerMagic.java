package org.tor_tik96.chronoline.Upgrades.Magic;

import net.minecraft.nbt.CompoundTag;
import org.tor_tik96.chronoline.Upgrades.Craft.PlayerCraft;

public class PlayerMagic {
    private int magic;

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void copyFrom(PlayerMagic source) {
        this.magic = source.magic;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("magic", magic);

    }

    public void loadNBTData(CompoundTag tag) {
        magic = tag.getInt("magic");
    }
}
