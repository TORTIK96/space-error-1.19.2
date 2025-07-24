package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;
import org.tor_tik96.chronoline.Upgrades.Stamina.Stamina;

public class SetStrengthLevelCommand {
    public SetStrengthLevelCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setStrengthLevel")
                        .then(Commands.argument("number", IntegerArgumentType.integer(0, 500))
                                .executes(commandContext -> setStrengthLevel(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "number"))))
        );
    }

    private int setStrengthLevel(CommandSourceStack commandContext, int number) throws CommandSyntaxException {
        PacketHandler.sendToServer(new ServerStrengthPacketC2S(number));
        return 1;
    }
}
