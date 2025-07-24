package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.DistortedFragments.ServerDistortedFragmentsPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Strength.ServerStrengthPacketC2S;

public class SetDistortedFragmentsCommand {
    public SetDistortedFragmentsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setDistortedFragments")
                        .then(Commands.argument("number", IntegerArgumentType.integer())
                                .executes(commandContext -> setDistortedFragments(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "number"))))
        );
    }

    private int setDistortedFragments(CommandSourceStack commandContext, int number) throws CommandSyntaxException {
        PacketHandler.sendToServer(new ServerDistortedFragmentsPacketC2S(number));
        return 1;
    }
}
