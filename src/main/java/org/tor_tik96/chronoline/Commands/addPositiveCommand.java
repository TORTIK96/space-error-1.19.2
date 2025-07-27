package org.tor_tik96.chronoline.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.world.entity.npc.Npc;
import org.tor_tik96.chronoline.Network.DistortedFragments.ServerDistortedFragmentsPacketC2S;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Npcs.NPC;
import org.tor_tik96.chronoline.Npcs.NpcUtils;
import org.tor_tik96.chronoline.hud.reputation.NpcReputations;

public class addPositiveCommand {
    public addPositiveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("addPositive")
                        .then(Commands.argument("message", MessageArgument.message())
                                .executes(commandContext -> addPositive(commandContext.getSource(), MessageArgument.getMessage(commandContext, "message"))))
        );
    }

    private int addPositive(CommandSourceStack commandContext, Message message) throws CommandSyntaxException {
        NPC npc = NpcUtils.getNpc(NpcReputations.PERDUN);
        npc.addPositive(message.getString());
        NpcUtils.updateNpc(NpcReputations.PERDUN, npc);
        return 1;
    }
}
