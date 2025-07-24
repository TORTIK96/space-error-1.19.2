package org.tor_tik96.chronoline.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;

import java.util.Random;
@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class DistortedFragmentHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() != null && event.getEntity() != null) {
            Entity attacker = event.getSource().getEntity();
            LivingEntity target = event.getEntity();
            if (attacker.getType().equals(EntityType.PLAYER)) {
                Random random = new Random();
                int rand = random.nextInt(1, 100);
                if (rand <= 25) {
                    target.spawnAtLocation(new ItemStack(RegisterItems.DISTORTED_FRAGMENT.get(), 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void mineEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null && !event.isCanceled()) {
            Random random = new Random();
            int rand = random.nextInt(1, 100);
            if (rand <= 1) {
                player.addItem(new ItemStack(RegisterItems.DISTORTED_FRAGMENT.get(), 1));
            }
        }
    }
}
