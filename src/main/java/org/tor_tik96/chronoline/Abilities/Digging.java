package org.tor_tik96.chronoline.Abilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class Digging {

    @SubscribeEvent
    public static void upDigging(BlockEvent.BreakEvent event) {
        if (!event.isCanceled() && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer() && Config.isActiveAbility(AbilityTypes.DIGGING)) {
            Player player = event.getPlayer();
            Level level = player.getLevel();
            BlockPos blockPos = event.getPos();
            if (PickaxeItem.class.isAssignableFrom(player.getItemInHand(InteractionHand.MAIN_HAND).getItem().getClass())) {
                BlockState downBlock = level.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()));
                BlockState upBlock = level.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()));
                if (downBlock.getBlock().defaultDestroyTime() > 0) {
                    level.destroyBlock(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()), true, player);
                }
                if (upBlock.getBlock().defaultDestroyTime() > 0) {
                    level.destroyBlock(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()), true, player);
                }
            }
        }
    }

}
