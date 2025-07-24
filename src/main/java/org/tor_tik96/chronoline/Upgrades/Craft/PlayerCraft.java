package org.tor_tik96.chronoline.Upgrades.Craft;

import net.minecraft.nbt.CompoundTag;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStamina;

public class PlayerCraft {
    private int craft;

    public int getCraft() {
        return craft;
    }

    public void setCraft(int craft) {
        this.craft = craft;
    }

    public void copyFrom(PlayerCraft source) {
        this.craft = source.craft;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("craft", craft);

    }

    public void loadNBTData(CompoundTag tag) {
        craft = tag.getInt("craft");
    }
}
