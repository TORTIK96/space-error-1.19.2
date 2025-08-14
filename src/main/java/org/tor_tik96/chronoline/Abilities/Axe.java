package org.tor_tik96.chronoline.Abilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class Axe {
    @SubscribeEvent
    public static void upDigging(BlockEvent.BreakEvent event) {
        if (!event.isCanceled() && Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer() && Config.isActiveAbility(AbilityTypes.AXE)) {
            Player player = event.getPlayer();
            Level level = player.getLevel();
            BlockPos blockPos = event.getPos();
            if (AxeItem.class.isAssignableFrom(player.getItemInHand(InteractionHand.MAIN_HAND).getItem().getClass())) {
                breakTree(blockPos, level, player);
            }
        }
    }

    public static void breakTree(BlockPos startPos, Level world, Player player) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            if (visited.contains(currentPos)) continue;
            visited.add(currentPos);

            BlockState state = world.getBlockState(currentPos);
            Block block = state.getBlock();

            // Проверяем, что блок это часть дерева
            if (block.getDescriptionId().contains("log")) {
                world.destroyBlock(currentPos, true, player);
                // Добавляем соседние блоки, чтобы проверить их
                for (Direction dir : Direction.values()) {
                    BlockPos neighborPos = currentPos.offset(dir.getNormal());
                    if (!visited.contains(neighborPos)) {
                        queue.add(neighborPos);
                    }
                }
            }
        }
    }
}
