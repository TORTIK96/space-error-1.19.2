package org.tor_tik96.chronoline.Upgrades.Stamina;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Network.Craft.ClientCraftPacketS2C;
import org.tor_tik96.chronoline.Network.DistortedFragments.ClientDistortedFragmentsPacketS2C;
import org.tor_tik96.chronoline.Network.Magic.ClientMagicPacketS2C;
import org.tor_tik96.chronoline.Network.Rhetoric.ClientRhetoricPacketS2C;
import org.tor_tik96.chronoline.Network.Stamina.ClientMaxStaminaPacketS2C;
import org.tor_tik96.chronoline.Network.Stamina.ClientStaminaPacketS2C;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Stamina.ClientStaminaRegeneratePacketS2C;
import org.tor_tik96.chronoline.Network.Strength.ClientStrengthPacketS2C;
import org.tor_tik96.chronoline.Upgrades.Craft.PlayerCraftProvider;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.PlayerDistortedFragmentsProvider;
import org.tor_tik96.chronoline.Upgrades.Magic.PlayerMagicProvider;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.PlayerRhetoricProvider;
import org.tor_tik96.chronoline.Upgrades.Strength.PlayerStrengthProvider;
import org.tor_tik96.chronoline.Upgrades.StrengthAndCraftHandler;

import java.util.HashMap;
import java.util.Map;

import static org.tor_tik96.chronoline.Upgrades.Stamina.Stamina.*;
import static org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina.*;
import static org.tor_tik96.chronoline.hud.RegisterHUD.*;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class StaminaHandler {
    private static boolean stay = true;
    private static int stayTime = 0;

    private static int runTime = 0;

    private static boolean hit = true;
    private static int hitTime = 0;

    private static boolean mine = true;
    private static int mineTime = 0;

    private static boolean place = true;
    private static int placeTime = 0;

    private static boolean jump = true;
    private static int jumpTime = 0;

    private static boolean shoot = true;
    private static int shootTime = 0;
    private static Map<Item, Integer> arrows = new HashMap<>();

    private static int regenerateTick = 0;

    public static boolean isStay() {
        return (stay && stayTime == 0) && (hit && hitTime == 0) && (mine && mineTime == 0) && (place && placeTime == 0) && (jump && jumpTime == 0);
    }

    @SubscribeEvent
    public static void regenerate(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ServerPlayer player = (ServerPlayer) event.player;
            if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
                player.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                    PacketHandler.sendToPlayer(new ClientStaminaPacketS2C(stamina.getStamina()), player);
                    int maxStamina = stamina.getMaxStamina();
                    if (maxStamina < 100) {
                        PacketHandler.sendToPlayer(new ClientMaxStaminaPacketS2C(100), player);
                    } else {
                        PacketHandler.sendToPlayer(new ClientMaxStaminaPacketS2C(stamina.getMaxStamina()), player);
                    }
                    PacketHandler.sendToPlayer(new ClientStaminaRegeneratePacketS2C(stamina.isRegenerate()), player);
                });
                player.getCapability(PlayerStrengthProvider.PLAYER_STRENGTH).ifPresent(strength -> {
                    PacketHandler.sendToPlayer(new ClientStrengthPacketS2C(strength.getStrength()), player);
                });
                player.getCapability(PlayerCraftProvider.PLAYER_CRAFT).ifPresent(craft -> {
                    PacketHandler.sendToPlayer(new ClientCraftPacketS2C(craft.getCraft()), player);
                });
                player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    PacketHandler.sendToPlayer(new ClientMagicPacketS2C(magic.getMagic()), player);
                });
                player.getCapability(PlayerRhetoricProvider.PLAYER_RHETORIC).ifPresent(rhetoric -> {
                    PacketHandler.sendToPlayer(new ClientRhetoricPacketS2C(rhetoric.getRhetoric()), player);
                });
                player.getCapability(PlayerDistortedFragmentsProvider.PLAYER_DISTORTED_FRAGMENTS).ifPresent(distortedFragments -> {
                    PacketHandler.sendToPlayer(new ClientDistortedFragmentsPacketS2C(distortedFragments.getFragments()), player);
                });

                if (isStay() && ClientStamina.isRegenerate()) {
                    if (regenerateTick >= 10) {
                        regenerateTick = 0;
                        int regenerateCount = getMaxStamina() / 100;
                        addStamina(regenerateCount, getMaxStamina());
                    }
                    regenerateTick++;
                } else {
                    regenerateTick = 0;
                } // Сделать gamerule на естественную регенерацию стамины
                boolean isSprinting = player.isSprinting();
                if (isSprinting && getStamina() >= StaminaActionsCost.RUNNING.getCost() && runTime < 20) {
                    stay = false;
                    stayTime = 40;
                    runTime++;
                } else if (isSprinting && getStamina() >= StaminaActionsCost.RUNNING.getCost()) {
                    runTime = 0;
                    subStamina(StaminaActionsCost.RUNNING.getCost());
                    setStaminaFlick();
                    stay = false;
                    stayTime = 40;
                } else if (isSprinting) {
                    player.setSprinting(false);
                    player.setSpeed(0.1F);
                    stay = false;
                    stayTime = 40;
                    runTime = 0;
                }

                if (!isSprinting && stayTime > 0) {
                    stayTime--;
                    stay = false;
                } else if (!isSprinting) {
                    stayTime = 0;
                    stay = true;
                }

                if (hitTime > 0) {
                    hitTime--;
                    hit = false;
                } else {
                    hitTime = 0;
                    hit = true;
                }

                if (mineTime > 0) {
                    mineTime--;
                    mine = false;
                } else {
                    mineTime = 0;
                    mine = true;
                }

                if (placeTime > 0) {
                    placeTime--;
                    place = false;
                } else {
                    placeTime = 0;
                    place = true;
                }

                if (jumpTime > 0) {
                    jumpTime--;
                    jump = false;
                } else {
                    jumpTime = 0;
                    jump = true;
                }

                if (shootTime > 0) {
                    shootTime--;
                    shoot = false;
                } else {
                    shootTime = 0;
                    shoot = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void hitEvent(AttackEntityEvent event) {
        Player attacker = event.getEntity();
        Entity target = event.getTarget();
        if (attacker != null && target != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (getStamina() >= StaminaActionsCost.HITTING.getCost() && StrengthAndCraftHandler.canUseToolPlayer(attacker)) {
                subStamina(StaminaActionsCost.HITTING.getCost());
                setStaminaFlick();
                hit = false;
                hitTime = 40;
            } else {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
                if (getStamina() >= StaminaActionsCost.JUMPING.getCost()) {
                    subStamina(StaminaActionsCost.JUMPING.getCost());
                    setStaminaFlick();
                } else {
                    Vec3 deltaMovement = player.getDeltaMovement();
                    player.setDeltaMovement(deltaMovement.x(), 0, deltaMovement.z());
                }
                jump = false;
                jumpTime = 40;
            }
        }
    }

    @SubscribeEvent
    public static void mineEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (canBreaking() && StrengthAndCraftHandler.canUseToolPlayer(player)) {
                subStamina(StaminaActionsCost.MINING.getCost());
                setStaminaFlick();
                mine = false;
                mineTime = 40;
            } else {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void mineSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (!canBreaking() || !StrengthAndCraftHandler.canUseToolPlayer(event.getEntity()) && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            event.setCanceled(true);
        }
    }

    private static boolean canBreaking() {
        return getStamina() >= StaminaActionsCost.MINING.getCost();
    }

    @SubscribeEvent
    public static void placeEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (event.getItemStack().getItem() instanceof BlockItem && getStamina() >= StaminaActionsCost.PLACING.getCost()) {
                subStamina(StaminaActionsCost.PLACING.getCost());
                setStaminaFlick();
            } else if (getStamina() < StaminaActionsCost.PLACING.getCost()){
                event.setCanceled(true); //Возвращать игроку блок который он пытался поставить
            }
            place = false;
            placeTime = 40;
        }
    }

    @SubscribeEvent
    public static void shootEvent(ArrowLooseEvent event) {
        Player player = event.getEntity();
        if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (getStamina() >= StaminaActionsCost.SHOOTING.getCost() && StrengthAndCraftHandler.canUseToolPlayer(player)) {
                subStamina(StaminaActionsCost.SHOOTING.getCost());
                setStaminaFlick();
            } else {
                event.setCanceled(true);
            }
            shoot = false;
            shootTime = 40;
        }
    }

    @SubscribeEvent
    public static void shootTridentEvent(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (event.getItemStack().is(Tags.Items.TOOLS_TRIDENTS)) {
                if (getStamina() >= StaminaActionsCost.SHOOTING.getCost() && StrengthAndCraftHandler.canUseToolPlayer(player)) {
                    subStamina(StaminaActionsCost.SHOOTING.getCost());
                    setStaminaFlick();
                } else {
                    event.setCanceled(true);
                }
                shoot = false;
                shootTime = 40;
            }
        }
    }

    @SubscribeEvent
    public static void onUseEvent(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player != null && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            if (!StrengthAndCraftHandler.canUseToolPlayer(player)) {
                event.setCanceled(true);
            }
        }
    }

}
