package org.tor_tik96.chronoline.Upgrades.DistortedFragments;

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

public class PlayerDistortedFragmentsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerDistortedFragments> PLAYER_DISTORTED_FRAGMENTS = CapabilityManager.get(new CapabilityToken<PlayerDistortedFragments>() { });

    private PlayerDistortedFragments playerDistortedFragments = null;

    private final LazyOptional<PlayerDistortedFragments> optional = LazyOptional.of(this::createPlayerDistortedFragments);

    private @NotNull PlayerDistortedFragments createPlayerDistortedFragments() {
        if (this.playerDistortedFragments == null) {
            this.playerDistortedFragments = new PlayerDistortedFragments();
        }
        return this.playerDistortedFragments;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_DISTORTED_FRAGMENTS) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerDistortedFragments().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerDistortedFragments().loadNBTData(tag);
    }
}
