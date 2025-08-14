package org.tor_tik96.chronoline.Abilities;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Chronoline.MODID)
public class MasterChef {

    private static int tick = 0;
    private static final Map<Integer, Map<ItemStack, Item>> smelt = new HashMap<>();

    @SubscribeEvent
    public static void cookEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null && Config.isActiveAbility(AbilityTypes.MASTER_CHEF)) {
            Level level = player.getLevel();
            ItemStack stack = player.getItemInHand(InteractionHand.OFF_HAND);
            if (!stack.isEmpty()) {
                Map<String, Object> recipe = canBeCooked(stack, level);
                if (!recipe.isEmpty()) {
                    int time = (int) recipe.get("time") / 3;
                    ItemStack result = (ItemStack) recipe.get("result");
                    if (!smelt.containsValue(Map.of(stack, result.getItem()))) {
                        smelt.put(tick + time, Map.of(stack, result.getItem()));
                    }
                }
            }
            try {
                for (int i : smelt.keySet().stream().toList()) {
                    if (i == tick) {
                        if (isStack(smelt.get(i).keySet().stream().toList().get(0), stack)) {
                            Item item = smelt.get(i).get(smelt.get(i).keySet().stream().toList().get(0));
                            if (getSlot(player, item)) {
                                player.getInventory().add(new ItemStack(item));
                            } else {
                                ItemEntity itemEntity = getItemEntity(level, player, item);
                                level.addFreshEntity(itemEntity);
                            }
                            if (stack.getCount() - 1 == 0) {
                                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.AIR));
                            } else {
                                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(stack.getItem(), stack.getCount() - 1));
                            }
                            smelt.remove(i);
                        }
                    } else if (i < tick) {
                        smelt.remove(i);
                    }
                }
                tick++;
            } catch (Exception e) {
                Chronoline.LOGGER.error(e.getMessage());
                smelt.clear();
            }
        }

    }

    public static @NotNull ItemEntity getItemEntity(Level level, Player player, Item item) {
        return new ItemEntity(level, player.getX() + 0.5, player.getY() + 1, player.getZ() + 0.5, new ItemStack(item));
    }

    private static boolean isStack(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem().equals(stack2.getItem()) && stack1.getCount() == stack2.getCount();
    }

    public static boolean getSlot(Player player, Item item) {
        boolean result = false;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem().equals(item) && stack.getCount() < new ItemStack(item).getMaxStackSize()) {
                result = true;
            } else if (stack.isEmpty()) {
                result = true;
            }
        }
        return result;
    }

    public static Map<String, Object> canBeCooked(ItemStack stack, Level level) {
        RecipeManager recipeManager = level.getRecipeManager();

        List<SmeltingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);
        for (SmeltingRecipe recipe : recipes) {
            if (recipe.getIngredients().size() == 1) {
                if (recipe.getIngredients().get(0).test(stack)) {
                    return Map.of("result", recipe.getResultItem(), "time", recipe.getCookingTime());
                }
            }
        }

        return Map.of();
    }
}
