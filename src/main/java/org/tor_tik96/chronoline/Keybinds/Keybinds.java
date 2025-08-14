package org.tor_tik96.chronoline.Keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.lwjgl.glfw.GLFW;
import org.tor_tik96.chronoline.Abilities.Stunning;
import org.tor_tik96.chronoline.Npcs.NpcUtils;
import org.tor_tik96.chronoline.hud.Abilities.AbilityScreen;
import org.tor_tik96.chronoline.hud.Chat.MyChatScreen;
import org.tor_tik96.chronoline.hud.reputation.ReputatationScreen;
import org.tor_tik96.chronoline.hud.upgrades.UpgradesScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class Keybinds {
    public static final KeyMapping chatKey = new KeyMapping("key.chronoline.chat_key", GLFW.GLFW_KEY_Y, "key.categories.multiplayer"){
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                Minecraft.getInstance().setScreen(new MyChatScreen());
            }
            isDownOld = isDown;

        }
    };

    public static final KeyMapping upgradesKey = new KeyMapping("key.chronoline.upgrades_key", GLFW.GLFW_KEY_U, "key.categories.multiplayer"){
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                Minecraft.getInstance().setScreen(new UpgradesScreen());
            }
            isDownOld = isDown;

        }
    };

    public static final KeyMapping reputationKey = new KeyMapping("key.chronoline.reputation_key", GLFW.GLFW_KEY_I, "key.categories.multiplayer"){
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown && !NpcUtils.getOpenNpc().isEmpty()) {
                Minecraft.getInstance().setScreen(new ReputatationScreen());
            }
            isDownOld = isDown;

        }
    };

    public static final KeyMapping stunKey = new KeyMapping("key.chronoline.stun_key", GLFW.GLFW_KEY_G, "key.categories.multiplayer"){
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                if (Minecraft.getInstance().player != null) {
                    Player player = Minecraft.getInstance().player;
                    if (ServerLifecycleHooks.getCurrentServer() != null) {
                        PlayerList serverPlayers = ServerLifecycleHooks.getCurrentServer().getPlayerList();
                        Stunning.stun(serverPlayers.getPlayer(player.getUUID()));
                    }
                }
            }
            isDownOld = isDown;

        }
    };

    public static final KeyMapping abilitiesKey = new KeyMapping("key.chronoline.abilities_key", GLFW.GLFW_KEY_H, "key.categories.multiplayer"){
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                Minecraft.getInstance().setScreen(new AbilityScreen());
            }
            isDownOld = isDown;

        }
    };

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(chatKey);
        event.register(upgradesKey);
        event.register(reputationKey);
        event.register(stunKey);
        event.register(abilitiesKey);
    }


}