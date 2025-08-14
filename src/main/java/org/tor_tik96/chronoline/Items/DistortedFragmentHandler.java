package org.tor_tik96.chronoline.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

import java.util.Random;

import static org.tor_tik96.chronoline.Abilities.MasterChef.getItemEntity;
import static org.tor_tik96.chronoline.Abilities.MasterChef.getSlot;

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
            Level level = player.getLevel();
            Random random = new Random();
            int rand = random.nextInt(1, 100);
            if (rand <= 1) {
                Item item = RegisterItems.DISTORTED_FRAGMENT.get();
                if (getSlot(player, item)) {
                    player.getInventory().add(new ItemStack(item));
                } else {
                    ItemEntity itemEntity = getItemEntity(level, player, item);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }

    @SubscribeEvent
    public static void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        if (player != null && Config.isActiveAbility(AbilityTypes.CRAFT_FRAGMENT)) {
            Level level = player.getLevel();
            Random random = new Random();
            int rand = random.nextInt(1, 100);
            if (rand <= 10) {
                Item item = RegisterItems.DISTORTED_FRAGMENT.get();
                if (getSlot(player, item)) {
                    player.getInventory().add(new ItemStack(item));
                } else {
                    ItemEntity itemEntity = getItemEntity(level, player, item);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }
}
