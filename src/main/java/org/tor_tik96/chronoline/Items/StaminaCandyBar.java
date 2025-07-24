package org.tor_tik96.chronoline.Items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;
import org.tor_tik96.chronoline.Upgrades.Stamina.Stamina;

import java.util.List;
import java.util.Random;

public class StaminaCandyBar extends Item {
    private final StaminaCandyBarTypes type;

    public StaminaCandyBar(Properties properties, StaminaCandyBarTypes type) {
        super(properties);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(type.getDescription()));
        tooltip.add(Component.empty());
        if (type.equals(StaminaCandyBarTypes.RANDOM)) {
            Random random = new Random();
            int rand = random.nextInt(0, Integer.MAX_VALUE);
            tooltip.add(Component.literal("§5+§k" + rand + "§r§5 оч§kков §r§5выносл§kивос§r§5ти"));
        } else {
            tooltip.add(Component.literal("§a+" + type.getStamina() + " очков выносливости"));
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity.getType().equals(EntityType.PLAYER)) {
            if (type.equals(StaminaCandyBarTypes.RANDOM)) {
                Random random = new Random();
                int rand = random.nextInt(0, ClientStamina.getMaxStamina());
                Stamina.addStamina(rand, ClientStamina.getMaxStamina());
            } else {
                Stamina.addStamina(type.getStamina(), ClientStamina.getMaxStamina());
            }
            itemStack.shrink(1);
        }

        return itemStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.startUsingItem(hand);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
