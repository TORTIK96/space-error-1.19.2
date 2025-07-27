package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;

@Mod.EventBusSubscriber(modid = Chronoline.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterCommands {
    @SubscribeEvent
    public static void onCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        new SetMaxStaminaCommand(dispatcher);
        new SetStrengthLevelCommand(dispatcher);
        new SetCraftLevelCommand(dispatcher);
        new SendMessageInMyChatCommand(dispatcher);
        new SetDistortedFragmentsCommand(dispatcher);
        new SetStaminaRegenerate(dispatcher);
        new SetTimerCommand(dispatcher);
        new addPositiveCommand(dispatcher);
    }
}
