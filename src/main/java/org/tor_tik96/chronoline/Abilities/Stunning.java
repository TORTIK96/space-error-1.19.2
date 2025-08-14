package org.tor_tik96.chronoline.Abilities;

import com.lowdragmc.photon.client.fx.BlockEffect;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.GeneralFuncs;
import org.tor_tik96.chronoline.Sounds.RegisterSounds;
import org.tor_tik96.chronoline.Utils.Utils;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;
import org.tor_tik96.chronoline.particles.DoubleBlockEffect;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class Stunning {
    public static int tick = 0;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if (tick > 0) {
            tick--;
        } else {
            tick = 0;
        }
    }

    public static void stun(ServerPlayer player) {
        Level level = player.getLevel();
        if (!level.isClientSide() && Config.isActiveAbility(AbilityTypes.STUNNING)) {
            if (tick <= 0) {
                level.playSeededSound(null, player.getX(), player.getY(), player.getZ(), RegisterSounds.CRICK.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                double radius = 10.0;
                BlockPos pos = player.getOnPos();
                AABB zoneBox = new AABB(pos).inflate(radius);
                List<Entity> entities = new ArrayList<>(level.getEntitiesOfClass(Entity.class, zoneBox));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity livingEntity && !(entity instanceof Player)) {
                        double dx = entity.getX() - pos.getX() - 0.5;
                        double dz = entity.getZ() - pos.getZ() - 0.5;
                        double distance = Math.sqrt(dx * dx + dz * dz);
                        if (distance < radius && livingEntity.isAlive()) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 100));
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 100));
                        }
                    }
                }
                FX particle = FXHelper.getFX(ResourceLocation.fromNamespaceAndPath("photon", "crick"));
                DoubleBlockEffect effect = new DoubleBlockEffect(particle, level, new Vec3(player.getOnPos().getX(), player.getOnPos().getY() + 1.1, player.getOnPos().getZ()));
                effect.start();
                tick = 900;
            } else {
                int sec = tick / 20;
                GeneralFuncs.sendOneTimeMessage("Перезарядка: " + sec + " " + Utils.seconds(sec));
            }
        }
    }

}
