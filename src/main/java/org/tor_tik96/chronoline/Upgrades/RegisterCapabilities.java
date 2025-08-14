package org.tor_tik96.chronoline.Upgrades;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Network.Craft.ClientCraftPacketS2C;
import org.tor_tik96.chronoline.Network.Craft.ServerCraftPacketC2S;
import org.tor_tik96.chronoline.Network.DistortedFragments.ClientDistortedFragmentsPacketS2C;
import org.tor_tik96.chronoline.Network.DistortedFragments.ServerDistortedFragmentsPacketC2S;
import org.tor_tik96.chronoline.Network.Ephemerons.ClientEphemeronsPacketS2C;
import org.tor_tik96.chronoline.Network.Ephemerons.ServerEphemeronsPacketC2S;
import org.tor_tik96.chronoline.Network.Magic.ClientMagicPacketS2C;
import org.tor_tik96.chronoline.Network.Magic.ServerMagicPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Rhetoric.ClientRhetoricPacketS2C;
import org.tor_tik96.chronoline.Network.Rhetoric.ServerRhetoricPacketC2S;
import org.tor_tik96.chronoline.Network.Stamina.*;
import org.tor_tik96.chronoline.Network.Strength.ClientStrengthPacketS2C;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;
import org.tor_tik96.chronoline.Upgrades.Craft.ClientCraft;
import org.tor_tik96.chronoline.Upgrades.Craft.PlayerCraft;
import org.tor_tik96.chronoline.Upgrades.Craft.PlayerCraftProvider;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.ClientDistortedFragments;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.PlayerDistortedFragments;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.PlayerDistortedFragmentsProvider;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.ClientEphemerons;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.PlayerEphemerons;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.PlayerEphemeronsProvider;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;
import org.tor_tik96.chronoline.Upgrades.Magic.PlayerMagic;
import org.tor_tik96.chronoline.Upgrades.Magic.PlayerMagicProvider;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.ClientRhetoric;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.PlayerRhetoric;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.PlayerRhetoricProvider;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStamina;
import org.tor_tik96.chronoline.Upgrades.Stamina.PlayerStaminaProvider;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;
import org.tor_tik96.chronoline.Upgrades.Strength.PlayerStrength;
import org.tor_tik96.chronoline.Upgrades.Strength.PlayerStrengthProvider;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class RegisterCapabilities {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_stamina"), new PlayerStaminaProvider());
            }
            if(!event.getObject().getCapability(PlayerStrengthProvider.PLAYER_STRENGTH).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_strength"), new PlayerStrengthProvider());
            }
            if(!event.getObject().getCapability(PlayerCraftProvider.PLAYER_CRAFT).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_craft"), new PlayerCraftProvider());
            }
            if(!event.getObject().getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_magic"), new PlayerMagicProvider());
            }
            if(!event.getObject().getCapability(PlayerRhetoricProvider.PLAYER_RHETORIC).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_rhetoric"), new PlayerRhetoricProvider());
            }
            if(!event.getObject().getCapability(PlayerDistortedFragmentsProvider.PLAYER_DISTORTED_FRAGMENTS).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_distorted_fragments"), new PlayerDistortedFragmentsProvider());
            }
            if(!event.getObject().getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "player_ephemerons"), new PlayerEphemeronsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerStrengthProvider.PLAYER_STRENGTH).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerStrengthProvider.PLAYER_STRENGTH).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerCraftProvider.PLAYER_CRAFT).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerCraftProvider.PLAYER_CRAFT).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerRhetoricProvider.PLAYER_RHETORIC).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerRhetoricProvider.PLAYER_RHETORIC).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerDistortedFragmentsProvider.PLAYER_DISTORTED_FRAGMENTS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerDistortedFragmentsProvider.PLAYER_DISTORTED_FRAGMENTS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerStamina.class);
        event.register(PlayerStrength.class);
        event.register(PlayerCraft.class);
        event.register(PlayerMagic.class);
        event.register(PlayerRhetoric.class);
        event.register(PlayerDistortedFragments.class);
        event.register(PlayerEphemerons.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
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
                player.getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).ifPresent(ephemerons -> {
                    PacketHandler.sendToPlayer(new ClientEphemeronsPacketS2C(ephemerons.getEphemerons()), player);
                });
                player.getCapability(PlayerEphemeronsProvider.PLAYER_EPHEMERONS).ifPresent(ephemerons -> {
                    PacketHandler.sendToPlayer(new ClientEphemeronsPacketS2C(ephemerons.getEphemerons()), player);
                });
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerLeaveWorld(EntityLeaveLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof Player) {
                PacketHandler.sendToServer(new ServerStaminaPacketC2S(ClientStamina.getStamina()));
                PacketHandler.sendToServer(new ServerStaminaRegeneratePacketC2S(ClientStamina.isRegenerate()));
                PacketHandler.sendToServer(new ServerMaxStaminaPacketC2S(ClientStamina.getMaxStamina()));
                PacketHandler.sendToServer(new ServerStrengthPacketC2S(ClientStrength.getStrength()));
                PacketHandler.sendToServer(new ServerCraftPacketC2S(ClientCraft.getCraft()));
                PacketHandler.sendToServer(new ServerMagicPacketC2S(ClientMagic.getMagic()));
                PacketHandler.sendToServer(new ServerRhetoricPacketC2S(ClientRhetoric.getRhetoric()));
                PacketHandler.sendToServer(new ServerDistortedFragmentsPacketC2S(ClientDistortedFragments.getDistortedFragments()));
                PacketHandler.sendToServer(new ServerEphemeronsPacketC2S(ClientEphemerons.getEphemerons()));
            }
        }
    }
}
