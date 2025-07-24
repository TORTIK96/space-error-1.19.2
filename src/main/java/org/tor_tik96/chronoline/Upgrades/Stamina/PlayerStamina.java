package org.tor_tik96.chronoline.Upgrades.Stamina;

import net.minecraft.nbt.CompoundTag;

public class PlayerStamina {
    private int stamina;
    private final int MIN_STAMINA = 0;
    private int MAX_STAMINA = 100;
    private boolean regenerate = true;

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getMaxStamina() {
        return MAX_STAMINA;
    }

    public void setMaxStamina(int maxStamina) {
        this.MAX_STAMINA = maxStamina;
    }

    public void setRegenerate(boolean regenerate) {
        this.regenerate = regenerate;
    }

    public boolean isRegenerate() {
        return regenerate;
    }

    public void copyFrom(PlayerStamina source) {
        this.stamina = source.stamina;
        this.MAX_STAMINA = source.MAX_STAMINA;
        this.regenerate = source.regenerate;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("stamina", stamina);
        tag.putInt("max_stamina", MAX_STAMINA);
        tag.putBoolean("regenerate", regenerate);
    }

    public void loadNBTData(CompoundTag tag) {
        stamina = tag.getInt("stamina");
        MAX_STAMINA = tag.getInt("max_stamina");
        regenerate = tag.getBoolean("regenerate");
    }
}
