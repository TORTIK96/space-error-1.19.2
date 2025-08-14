package org.tor_tik96.chronoline.Upgrades.Ephemeron;

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

public class PlayerEphemeronsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerEphemerons> PLAYER_EPHEMERONS = CapabilityManager.get(new CapabilityToken<PlayerEphemerons>() { });

    private PlayerEphemerons playerEphemerons = null;

    private final LazyOptional<PlayerEphemerons> optional = LazyOptional.of(this::createPlayerEphemerons);

    private @NotNull PlayerEphemerons createPlayerEphemerons() {
        if (this.playerEphemerons == null) {
            this.playerEphemerons = new PlayerEphemerons();
        }
        return this.playerEphemerons;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_EPHEMERONS) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerEphemerons().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerEphemerons().loadNBTData(tag);
    }
}
