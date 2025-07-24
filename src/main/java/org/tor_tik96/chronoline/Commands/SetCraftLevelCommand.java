package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.Craft.ServerCraftPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;

public class SetCraftLevelCommand {
    public SetCraftLevelCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setCraftLevel")
                        .then(Commands.argument("number", IntegerArgumentType.integer(0, 500))
                                .executes(commandContext -> setCraftLevel(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "number"))))
        );
    }

    private int setCraftLevel(CommandSourceStack commandContext, int number) throws CommandSyntaxException {
        PacketHandler.sendToServer(new ServerCraftPacketC2S(number));
        return 1;
    }
}
