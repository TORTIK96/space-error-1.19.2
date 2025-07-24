package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import org.tor_tik96.chronoline.hud.RegisterHUD;

import java.util.Arrays;
import java.util.List;

public class SendMessageInMyChatCommand {
    public SendMessageInMyChatCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("send_msg")
                        .then(Commands.argument("message", MessageArgument.message())
                                .executes(commandContext -> sendMessageInMyChat(commandContext.getSource(), MessageArgument.getMessage(commandContext, "message"))))
        );
    }

    private int sendMessageInMyChat(CommandSourceStack commandContext, Message senderAndMessage) throws CommandSyntaxException {
        if (RegisterHUD.generalHUD != null) {
            List<String> mes = Arrays.stream(senderAndMessage.getString().split(" ")).toList();
            String sender = mes.get(0);
            String senderColor = mes.get(1);
            String message = "";
            for (String s : mes) {
                if (mes.indexOf(s) > 1) {
                    message += s + " ";
                }
            }
            RegisterHUD.generalHUD.getMyChat().addMessage(sender, message, senderColor);
        }
        return 1;
    }
}
