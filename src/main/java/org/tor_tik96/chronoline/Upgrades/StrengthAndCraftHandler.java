package org.tor_tik96.chronoline.Upgrades;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import org.tor_tik96.chronoline.Network.PacketHandler;
import org.tor_tik96.chronoline.Upgrades.Craft.ClientCraft;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;

public class StrengthAndCraftHandler {

    public static boolean canUseToolPlayer(Player player) {
        boolean leftHand = canUseTool(player.getItemInHand(InteractionHand.OFF_HAND));
        boolean rightHand = canUseTool(player.getItemInHand(InteractionHand.MAIN_HAND));
        return leftHand && rightHand;
    }

    public static boolean canUseTool(ItemStack itemStack) {
        if (!itemStack.isEmpty() && itemStack.is(Tags.Items.TOOLS)) {
            if (itemStack.is(Tags.Items.TOOLS_AXES)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.AXE);
                return canUseItem(level, Upgrades.STRENGTH);
            } else if (itemStack.is(Tags.Items.TOOLS_PICKAXES)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.PICKAXE);
                return canUseItem(level, Upgrades.CRAFT);
            } else if (itemStack.is(Tags.Items.TOOLS_SHOVELS)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.SHOVEL);
                return canUseItem(level, Upgrades.CRAFT);
            } else if (itemStack.is(Tags.Items.TOOLS_HOES)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.HOE);
                return canUseItem(level, Upgrades.CRAFT);
            } else if (itemStack.is(Tags.Items.TOOLS_SWORDS)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.SWORD);
                return canUseItem(level, Upgrades.STRENGTH);
            } else if (itemStack.is(Tags.Items.TOOLS_BOWS)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.BOW);
                return canUseItem(level, Upgrades.STRENGTH);
            } else if (itemStack.is(Tags.Items.TOOLS_CROSSBOWS)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.CROSSBOW);
                return canUseItem(level, Upgrades.STRENGTH);
            } else if (itemStack.is(Tags.Items.TOOLS_TRIDENTS)) {
                int level = getUseLevel(itemStack.getItem(), ToolType.TRIDENT);
                return canUseItem(level, Upgrades.STRENGTH);
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean canUseItem(int level, Upgrades type) {
        if (type.equals(Upgrades.STRENGTH) && ClientStrength.getStrength() >= level) {
            return true;
        } else if (type.equals(Upgrades.CRAFT) && ClientCraft.getCraft() >= level) {
            return true;
        }
        return false;
    }

    public static int getUseLevel(Item item, ToolType type) {
        int level = 1;
        if (type.equals(ToolType.AXE)) {
            AxeItem axeItem = (AxeItem) item;
            Tier tier = axeItem.getTier();

            int uses = tier.getUses();
            float speed = tier.getSpeed();
            double attackDamage = axeItem.getAttackDamage() + tier.getAttackDamageBonus(); // базовый урон оружия

            double weightUses = type.getWeightUses();
            double weightSpeed = type.getWeightSpeed();
            double weightAttackDamage = type.getWeightAttackDamage();

            double baseFactor = 15.000000000000002;
            int baseLevel = type.getBaseLevel();

            double factor = (uses * weightUses + speed * weightSpeed + attackDamage * weightAttackDamage);

            double rawLevel;

            if (factor > baseFactor) {
                double newFactor = (factor / baseFactor);
                rawLevel = baseLevel * newFactor;
            } else if (factor < baseFactor) {
                double newFactor = baseFactor / factor;
                rawLevel = baseLevel - (baseLevel * newFactor);
            } else {
                rawLevel = baseLevel;
            }

            level = (int) Math.round(rawLevel);
            if (level < 0) level = 0;
            if (level > 500) level = 500;
        } else if (type.equals(ToolType.PICKAXE)) {
            PickaxeItem pickaxeItem = (PickaxeItem) item;
            Tier tier = pickaxeItem.getTier();

            int uses = tier.getUses();
            float speed = tier.getSpeed();
            double attackDamage = pickaxeItem.getAttackDamage() + tier.getAttackDamageBonus(); // базовый урон оружия

            double weightUses = type.getWeightUses();
            double weightSpeed = type.getWeightSpeed();
            double weightAttackDamage = type.getWeightAttackDamage();

            double baseFactor = 24.700000000000003; //0.20023600000000003
            int baseLevel = type.getBaseLevel();

            double factor = (uses * weightUses + speed * weightSpeed + attackDamage * weightAttackDamage);
            double rawLevel;

            if (factor > baseFactor) {
                double newFactor = (factor / baseFactor);
                rawLevel = baseLevel * newFactor;
            } else if (factor < baseFactor) {
                double newFactor = baseFactor / factor;
                rawLevel = baseLevel - (baseLevel * newFactor);
            } else {
                rawLevel = baseLevel;
            }

            level = (int) Math.round(rawLevel);
            if (level < 0) level = 0;
            if (level > 500) level = 500;
        } else if (type.equals(ToolType.SHOVEL)) {
            ShovelItem shovelItem = (ShovelItem) item;
            Tier tier = shovelItem.getTier();

            int uses = tier.getUses();
            float speed = tier.getSpeed();
            double attackDamage = shovelItem.getAttackDamage() + tier.getAttackDamageBonus(); // базовый урон оружия

            double weightUses = type.getWeightUses();
            double weightSpeed = type.getWeightSpeed();
            double weightAttackDamage = type.getWeightAttackDamage();

            double baseFactor = 24.75; //0.20023600000000003
            int baseLevel = type.getBaseLevel();

            double factor = (uses * weightUses + speed * weightSpeed + attackDamage * weightAttackDamage);
            double rawLevel;

            if (factor > baseFactor) {
                double newFactor = (factor / baseFactor);
                rawLevel = baseLevel * newFactor;
            } else if (factor < baseFactor) {
                double newFactor = baseFactor / factor;
                rawLevel = baseLevel - (baseLevel * newFactor);
            } else {
                rawLevel = baseLevel;
            }

            level = (int) Math.round(rawLevel);
            if (level < 0) level = 0;
            if (level > 500) level = 500;
        } else if (type.equals(ToolType.HOE)) {
            HoeItem hoeItem = (HoeItem) item;
            Tier tier = hoeItem.getTier();

            int uses = tier.getUses();
            float speed = tier.getSpeed();
            double attackDamage = hoeItem.getAttackDamage() + tier.getAttackDamageBonus(); // базовый урон оружия

            double weightUses = type.getWeightUses();
            double weightSpeed = type.getWeightSpeed();
            double weightAttackDamage = type.getWeightAttackDamage();

            double baseFactor = 30.3; //0.20023600000000003
            int baseLevel = type.getBaseLevel();

            double factor = (uses * weightUses + speed * weightSpeed + attackDamage * weightAttackDamage);
            double rawLevel;

            if (factor > baseFactor) {
                double newFactor = (factor / baseFactor);
                rawLevel = baseLevel * newFactor;
            } else if (factor < baseFactor) {
                double newFactor = baseFactor / factor;
                rawLevel = baseLevel - (baseLevel * newFactor);
            } else {
                rawLevel = baseLevel;
            }

            level = (int) Math.round(rawLevel);
            if (level < 0) level = 0;
            if (level > 500) level = 500;
        } else if (type.equals(ToolType.SWORD)) {
            SwordItem swordItem = (SwordItem) item;
            Tier tier = swordItem.getTier();

            int uses = tier.getUses();
            float speed = tier.getSpeed();
            double attackDamage = swordItem.getDamage() + tier.getAttackDamageBonus(); // базовый урон оружия

            double weightUses = type.getWeightUses();
            double weightSpeed = type.getWeightSpeed();
            double weightAttackDamage = type.getWeightAttackDamage();

            double baseFactor = 14.100000000000001; //0.20023600000000003
            int baseLevel = type.getBaseLevel();

            double factor = (uses * weightUses + speed * weightSpeed + attackDamage * weightAttackDamage);
            double rawLevel;

            if (factor > baseFactor) {
                double newFactor = (factor / baseFactor);
                rawLevel = baseLevel * newFactor;
            } else if (factor < baseFactor) {
                double newFactor = baseFactor / factor;
                rawLevel = baseLevel - (baseLevel * newFactor);
            } else {
                rawLevel = baseLevel;
            }

            level = (int) Math.round(rawLevel);
            if (level < 0) level = 0;
            if (level > 500) level = 500;
        } else if (type.equals(ToolType.BOW)) {
            level = 10;
        } else if (type.equals(ToolType.CROSSBOW)) {
            level = 10;
        } else if (type.equals(ToolType.TRIDENT)) {
            level = 10;
        }

        return level;
    }

    public enum ToolType {
        AXE (0.2, 0.4, 0.4, 0.8, 4),
        SHOVEL (0.4, 0.5, 0.1, 0.8, 2),
        PICKAXE (0.4, 0.5, 0.1, 0.5, 3),
        HOE (0.5, 0.4, 0.1, 0.8, 1),
        SWORD (0.2, 0.4, 0.5, 0.8, 5),
        BOW (0, 0, 0, 0, 0),
        CROSSBOW (0, 0, 0, 0, 0),
        TRIDENT (0, 0, 0, 0, 0);

        private final double weightUses;
        private final double weightSpeed;
        private final double weightAttackDamage;
        private final double reductionFactor;
        private final int baseLevel;


        private ToolType(double weightUses, double weightSpeed, double weightAttackDamage, double reductionFactor, int baseLevel) {
            this.weightUses = weightUses;
            this.weightSpeed = weightSpeed;
            this.weightAttackDamage = weightAttackDamage;
            this.reductionFactor = reductionFactor;
            this.baseLevel = baseLevel;
        }

        public double getWeightUses() {
            return weightUses;
        }

        public double getWeightSpeed() {
            return weightSpeed;
        }

        public double getWeightAttackDamage() {
            return weightAttackDamage;
        }

        public double getReductionFactor() {
            return reductionFactor;
        }

        public int getBaseLevel() {
            return baseLevel;
        }
    }

}
