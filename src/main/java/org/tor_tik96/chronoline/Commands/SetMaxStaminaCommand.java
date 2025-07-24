package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Stamina.ServerMaxStaminaPacketC2S;
import org.tor_tik96.chronoline.Upgrades.Stamina.Stamina;

public class SetMaxStaminaCommand {

    public SetMaxStaminaCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setMaxStamina")
                        .then(Commands.argument("number", IntegerArgumentType.integer(100))
                                .executes(commandContext -> setMaxStamina(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "number"))))
        );
    }

    private int setMaxStamina(CommandSourceStack commandContext, int number) throws CommandSyntaxException{
        PacketHandler.sendToServer(new ServerMaxStaminaPacketC2S(number));
        return 1;
    }
}
