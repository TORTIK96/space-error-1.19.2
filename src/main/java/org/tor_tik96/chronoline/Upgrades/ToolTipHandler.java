package org.tor_tik96.chronoline.Upgrades;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Upgrades.Craft.ClientCraft;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;

import static org.tor_tik96.chronoline.Upgrades.StrengthAndCraftHandler.getUseLevel;

@Mod.EventBusSubscriber(modid = Chronoline.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ToolTipHandler {


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void tooltipEvent(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.isEmpty()) {
            int level = 0;
            Upgrades levelType = Upgrades.EMPTY;
            Item item = itemStack.getItem();
            if (AxeItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.AXE);
                levelType = Upgrades.STRENGTH;
            } else if (PickaxeItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.PICKAXE);
                levelType = Upgrades.CRAFT;
            } else if (ShovelItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.SHOVEL);
                levelType = Upgrades.CRAFT;
            } else if (HoeItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.HOE);
                levelType = Upgrades.CRAFT;
            } else if (SwordItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.SWORD);
                levelType = Upgrades.STRENGTH;
            } else if (BowItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.BOW);
                levelType = Upgrades.STRENGTH;
            } else if (CrossbowItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.CROSSBOW);
                levelType = Upgrades.STRENGTH;
            } else if (TridentItem.class.isAssignableFrom(item.getClass())) {
                level = getUseLevel(itemStack.getItem(), StrengthAndCraftHandler.ToolType.TRIDENT);
                levelType = Upgrades.STRENGTH;
            }
            if (!levelType.equals(Upgrades.EMPTY)) {
                if (levelType.equals(Upgrades.STRENGTH)) {
                    if (ClientStrength.getStrength() < level) {
                        event.getToolTip().add(1, Component.empty());
                        event.getToolTip().add(2, Component.literal("§9Минимальный уровень для сражения: §4" + level));
                        event.getToolTip().add(3, Component.empty());
                    } else {
                        event.getToolTip().add(1, Component.empty());
                        event.getToolTip().add(2, Component.literal("§9Минимальный уровень для сражения: §a" + level));
                        event.getToolTip().add(3, Component.empty());
                    }
                } else {
                    if (ClientCraft.getCraft() < level) {
                        event.getToolTip().add(1, Component.empty());
                        event.getToolTip().add(2, Component.literal("§6Минимальный уровень для копания: §4" + level));
                        event.getToolTip().add(3, Component.empty());
                    } else {
                        event.getToolTip().add(1, Component.empty());
                        event.getToolTip().add(2, Component.literal("§6Минимальный уровень для копания: §a" + level));
                        event.getToolTip().add(3, Component.empty());
                    }
                }
            }

        }
    }
}
