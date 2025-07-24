package org.tor_tik96.chronoline.Upgrades.Stamina;

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

public class PlayerStaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerStamina> PLAYER_STAMINA = CapabilityManager.get(new CapabilityToken<PlayerStamina>() { });

    private PlayerStamina stamina = null;

    private final LazyOptional<PlayerStamina> optional = LazyOptional.of(this::createPlayerStamina);

    private @NotNull PlayerStamina createPlayerStamina() {
        if (this.stamina == null) {
            this.stamina = new PlayerStamina();
        }
        return this.stamina;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_STAMINA) return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerStamina().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerStamina().loadNBTData(tag);
    }
}
