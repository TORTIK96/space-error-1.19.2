package org.tor_tik96.chronoline.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.data.SoundDefinition;
import org.jetbrains.annotations.Nullable;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.DistortedFragments;

import java.util.List;

public class DistortedFragment extends Item {

    public DistortedFragment(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.chronoline.distorted_fragment.desc1"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(this));
            int count = 0;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item.getItem().equals(this)) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                    count += item.getCount();

                }
            }
            DistortedFragments.addDistortedFragments(count);
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
