package org.tor_tik96.chronoline.Upgrades.Rhetoric;

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

public class PlayerRhetoricProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerRhetoric> PLAYER_RHETORIC = CapabilityManager.get(new CapabilityToken<PlayerRhetoric>() { });

    private PlayerRhetoric rhetoric = null;

    private final LazyOptional<PlayerRhetoric> optional = LazyOptional.of(this::createPlayerRhetoric);

    private @NotNull PlayerRhetoric createPlayerRhetoric() {
        if (this.rhetoric == null) {
            this.rhetoric = new PlayerRhetoric();
        }
        return this.rhetoric;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_RHETORIC) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerRhetoric().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerRhetoric().loadNBTData(tag);
    }
}
