package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.Craft.ServerCraftPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Utils.Timer;

public class SetTimerCommand {
    public SetTimerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setTimer")
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(0))
                                .executes(commandContext -> setTimer(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "seconds"))))
        );
    }

    private int setTimer(CommandSourceStack commandContext, int number) throws CommandSyntaxException {
        Timer.setTimer(number * 20);
        return 1;
    }
}
