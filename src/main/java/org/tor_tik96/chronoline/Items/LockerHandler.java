package org.tor_tik96.chronoline.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.net.Priority;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.GeneralFuncs;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Network.SetCloseDoorPacketC2S;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class LockerHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        ItemStack heldItem = player.getItemInHand(event.getHand());
        if (heldItem.getItem() == RegisterItems.LOCKER.get() && level.getBlockState(pos).getBlock() instanceof DoorBlock) {
            if (!Config.isDoorClose(pos)) {
                BlockPos pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
                if (level.getBlockState(pos1).getBlock() instanceof DoorBlock) {
                    PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos1, true));
                } else if (level.getBlockState(pos2).getBlock() instanceof DoorBlock) {
                    PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos2, true));
                }
                PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos, true));
                level.getBlockState(pos).setValue(DoorBlock.OPEN, false);
                GeneralFuncs.sendOneTimeMessage("Дверь §4закрыта");
                event.setCanceled(true);
            } else {
                BlockPos pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
                if (level.getBlockState(pos1).getBlock() instanceof DoorBlock) {
                    PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos1, false));
                } else if (level.getBlockState(pos2).getBlock() instanceof DoorBlock) {
                    PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos2, false));
                }
                PacketHandler.sendToServer(new SetCloseDoorPacketC2S(pos, false));
                GeneralFuncs.sendOneTimeMessage("Дверь §2открыта");
            }
        }
    }

    @SubscribeEvent
    public static void onDoorInteraction(PlayerInteractEvent.RightClickBlock event) {
        if (!event.isCanceled()) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();

            if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer() && level.getBlockState(pos).getBlock() instanceof DoorBlock && Config.isDoorClose(pos)) {
                // Блокируем открытие двери
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.isCanceled()) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();

            if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer() && level.getBlockState(pos).getBlock() instanceof DoorBlock && Config.isDoorClose(pos)) {
                // Блокируем ломание двери
                event.setCanceled(true);
            }
        }
    }
}
