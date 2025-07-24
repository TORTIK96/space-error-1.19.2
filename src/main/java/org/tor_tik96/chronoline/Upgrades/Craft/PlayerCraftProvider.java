package org.tor_tik96.chronoline.Upgrades.Craft;

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
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStamina;

public class PlayerCraftProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    public static Capability<PlayerCraft> PLAYER_CRAFT = CapabilityManager.get(new CapabilityToken<PlayerCraft>() { });

    private PlayerCraft craft = null;

    private final LazyOptional<PlayerCraft> optional = LazyOptional.of(this::createPlayerCraft);

    private @NotNull PlayerCraft createPlayerCraft() {
        if (this.craft == null) {
            this.craft = new PlayerCraft();
        }
        return this.craft;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_CRAFT) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerCraft().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerCraft().loadNBTData(tag);
    }
}
