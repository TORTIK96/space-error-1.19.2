package org.tor_tik96.chronoline.Utils;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class Timer {
    public static int tick = 0;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if (tick <= 0) {
            tick = 0;
        } else {
            tick--;
        }
    }

    public static void setTimer(int tick) {
        Timer.tick = tick;
    }

    public static int getTimerTick() {
        return tick;
    }

    public static int getTimerSeconds() {
        return tick / 20;
    }

}
