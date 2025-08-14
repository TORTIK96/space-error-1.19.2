package org.tor_tik96.chronoline.Abilities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class NightVision {
    @SubscribeEvent
    public static void nightVisionEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null  && Config.isActiveAbility(AbilityTypes.NIGHT_VISION)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 12 * 20, 0, false, false));
        }
    }
}
