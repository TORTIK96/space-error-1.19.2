package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.Stamina.ServerStaminaRegeneratePacketC2S;

public class SetStaminaRegenerate {

    public SetStaminaRegenerate(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setStaminaRegenerate")
                        .then(Commands.argument("bool", BoolArgumentType.bool())
                                .executes(commandContext -> setStaminaRegenerate(commandContext.getSource(), BoolArgumentType.getBool(commandContext, "bool"))))
        );
    }

    private int setStaminaRegenerate(CommandSourceStack commandContext, boolean regenerate) throws CommandSyntaxException {
        PacketHandler.sendToServer(new ServerStaminaRegeneratePacketC2S(regenerate));
        return 1;
    }
}
