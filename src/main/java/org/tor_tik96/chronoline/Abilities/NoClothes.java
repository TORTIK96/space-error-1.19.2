package org.tor_tik96.chronoline.Abilities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class NoClothes {
    @SubscribeEvent
    public static void noClothesEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null && Config.isActiveAbility(AbilityTypes.NO_CLOTHES)) {
            if (isDressed(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 1, false, false));
            }
        }
    }

    private static boolean isDressed(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && player.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && player.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && player.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
    }
}
