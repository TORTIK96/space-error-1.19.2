package org.tor_tik96.chronoline.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;

import java.util.Arrays;
import java.util.List;

import static org.tor_tik96.chronoline.Chronoline.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class RegisterHUD {

    public static String string = "";
    public static String sender = "";
    public static int a1 = 2;
    public static int a2 = 0;
    public static int a3 = 0;
    public static int a4 = 0;
    public static int a5 = 0;
    public static int a6 = 0;
    public static int a7 = 0;
    public static int a8 = 0;
    public static int a9 = 0;
    public static int a10 = 0;

    public static BlockPos oldPos;

    public static double lastHealth = 0;
    public static int flickerTimer = 0;
    public static boolean isFlickering = false;

    public static int lastStamina = 0;
    public static int lastDoubleStamina = 0;
    public static int flickerTimerStamina = 0;
    public static boolean isFlickeringStamina = false;

    // В вашем основном классе или инициализации
    public static generalHUD generalHUD;

    public static void init() {
        Minecraft mc = Minecraft.getInstance();
        if (generalHUD == null) {
            generalHUD = new generalHUD(mc, mc.getItemRenderer());
        }
    }

    // В событии рендера GUI
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void RenderPre(RenderGuiEvent.Pre event) {
        event.setCanceled(true);
        if (generalHUD == null) {
            init();
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.screen == null && generalHUD != null) {
            generalHUD.render(event.getPoseStack(), event.getPartialTick());
        }
    }

    // В событии рендера тика
    @SubscribeEvent
    public static void RenderTick(TickEvent.ServerTickEvent event) {
        if (generalHUD != null) {
            generalHUD.tick();
        }
    }



    public static void setStaminaFlick() {
        isFlickeringStamina = true;
        flickerTimerStamina = 25;

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null) {
            double currentHealth = Mth.ceil(player.getHealth());
            // Проверка изменения здоровья
            if (lastHealth != 0 && currentHealth != lastHealth) {
                // Здоровье изменилось
                if (currentHealth < lastHealth) {
                    // Убавление здоровья — активируем мигание
                    isFlickering = true;
                    flickerTimer = 20; // например, 20 тиков (1 секунда)
                } else if (currentHealth > lastHealth) {
                    // Регенация — тоже можно делать эффект
                    isFlickering = true;
                    flickerTimer = 20;
                }
            }
            lastHealth = currentHealth;
            // Обновляем таймер
            if (isFlickering && flickerTimer > 0) {
                flickerTimer--;
            } else {
                isFlickering = false;
            }

            int stamina = ClientStamina.getStamina();
            int cube = ClientStamina.getMaxStamina() / 10;
            if (lastStamina != 0 && stamina % cube == 0 && stamina > lastStamina && stamina > lastDoubleStamina) {
                setStaminaFlick();
            }
            lastDoubleStamina = lastStamina;
            lastStamina = stamina;
            // Обновляем таймер
            if (isFlickeringStamina && flickerTimerStamina > 0) {
                flickerTimerStamina--;
            } else if (isFlickeringStamina) {
                isFlickeringStamina = false;
                flickerTimer = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onchat(ServerChatEvent event) {
        String message = event.getMessage().getString();
        Component playerName = event.getPlayer().getDisplayName();
        ServerPlayer player = event.getPlayer();
        Level level = player.getLevel();
        //string = message;

        List<String> mesg = Arrays.stream(message.split(" ")).toList();
        if (mesg.size() == 3) {
            a1 = Integer.valueOf(mesg.get(0));
            a2 = Integer.valueOf(mesg.get(1));
            a3 = Integer.valueOf(mesg.get(2));/*
            a4 = Integer.valueOf(mesg.get(3));
            a5 = Integer.valueOf(mesg.get(4));
            a6 = Integer.valueOf(mesg.get(5));
            a7 = Integer.valueOf(mesg.get(6));
            a8 = Integer.valueOf(mesg.get(7));
            //a9 = Integer.valueOf(mesg.get(8));
            //a10 = Integer.valueOf(mesg.get(9));*/
        }
    }

    public static void sendMessage(String message) {
        if (generalHUD != null) {
            generalHUD.getChat().addMessage(Component.literal(message));
        }
    }


}