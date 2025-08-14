package org.tor_tik96.chronoline.Sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.tor_tik96.chronoline.Chronoline;

public class RegisterSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Chronoline.MODID);

    public static final RegistryObject<SoundEvent> CRICK = registerSoundEvent("crick");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, name)));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
