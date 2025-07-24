package org.tor_tik96.chronoline.Upgrades.Strength;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStrengthProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerStrength> PLAYER_STRENGTH = CapabilityManager.get(new CapabilityToken<PlayerStrength>() {
    });

    private PlayerStrength strength = null;

    private final LazyOptional<PlayerStrength> optional = LazyOptional.of(this::createPlayerStrength);

    private @NotNull PlayerStrength createPlayerStrength() {
        if (this.strength == null) {
            this.strength = new PlayerStrength();
        }
        return this.strength;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_STRENGTH) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerStrength().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerStrength().loadNBTData(tag);
    }

}
