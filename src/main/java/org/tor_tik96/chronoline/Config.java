package org.tor_tik96.chronoline;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.tor_tik96.chronoline.Npcs.NPC;
import org.tor_tik96.chronoline.Npcs.NpcUtils;
import org.tor_tik96.chronoline.hud.Abilities.AbilityScreen;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Chronoline.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NPCS = BUILDER.comment("репутация (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("npcs"), () -> NpcUtils.finalNPCs, Config::validateNpcs);
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> ABILITIES = BUILDER.comment("способности (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("abilities"), () -> List.of(0, 0, 0, 0, 0, 0, 0, 0, 0), Config::validateNpcs);
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> ABILiTY_DIM_TP_POS = BUILDER.comment("координаты места из способностей (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("ability_dim_pos"), () -> List.of(0, 0, 0), Config::validateNpcs);
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> P_DIM_TP_POS = BUILDER.comment("координаты места для пасхалок (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("p_dim_pos"), () -> List.of(0, 0, 0), Config::validateNpcs);
    private static final ForgeConfigSpec.BooleanValue ABILiTY_DIM_OPEN = BUILDER.comment("может ли игрок телепортироваться в измерение способностей (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").define("ability_dim_open", true);
    private static final ForgeConfigSpec.BooleanValue P_DIM_OPEN = BUILDER.comment("может ли игрок телепортироваться в измерение пасхалок (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").define("ability_dim_open", false);
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> ABILiTY_DIM = BUILDER.comment("прямоугольник места из способностей (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("ability_dim"), () -> List.of(0, 0, 0, 0, 0, 0), Config::validateNpcs);
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> P_DIM = BUILDER.comment("прямоугольник места для пасхалок (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("p_dim"), () -> List.of(0, 0, 0, 0, 0, 0), Config::validateNpcs);
    private static final ForgeConfigSpec.ConfigValue<List<?>> CLOSE_DOORS = BUILDER.comment("список закрытых дверей (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("close_doors"), ArrayList::new, Config::validateNpcs);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean validateNpcs(final Object object) {
        return true;
    }

    public static List<Integer> getAbilities() {
        return (List<Integer>) ABILITIES.get();
    }

    public static boolean isActiveAbility(AbilityTypes type) {
        List<Integer> abilities = getAbilities();
        int index = AbilityScreen.types.indexOf(type);
        return abilities.get(index) == 2;
    }

    public static void setAbilities(List<Integer> list) {
        ABILITIES.set(list);
        SPEC.save();
    }

    public static BlockPos getAbilityDimTeleportPos() {
        List<Integer> ability_dim = (List<Integer>) ABILiTY_DIM_TP_POS.get();
        return new BlockPos(ability_dim.get(0), ability_dim.get(1), ability_dim.get(2));
    }

    public static void setAbilityDimTeleportPos(BlockPos pos) {
        List<Integer> dim = List.of(pos.getX(), pos.getY(), pos.getZ());
        ABILiTY_DIM_TP_POS.set(dim);
        SPEC.save();
    }

    public static BlockPos getPDimTeleportPos() {
        List<Integer> p_dim = (List<Integer>) P_DIM_TP_POS.get();
        return new BlockPos(p_dim.get(0), p_dim.get(1), p_dim.get(2));
    }

    public static void setPDimTeleportPos(BlockPos pos) {
        List<Integer> dim = List.of(pos.getX(), pos.getY(), pos.getZ());
        P_DIM_TP_POS.set(dim);
        SPEC.save();
    }

    public static AABB getAbilityDim() {
        List<Integer> ability_dim = (List<Integer>) ABILiTY_DIM.get();
        return new AABB(ability_dim.get(0), ability_dim.get(1), ability_dim.get(2), ability_dim.get(3), ability_dim.get(4), ability_dim.get(5));
    }

    public static void setAbilityDim(AABB aabb) {
        List<Integer> dim = List.of((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ, (int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ);
        ABILiTY_DIM.set(dim);
        SPEC.save();
    }

    public static AABB getPDim() {
        List<Integer> p_dim = (List<Integer>) P_DIM.get();
        return new AABB(p_dim.get(0), p_dim.get(1), p_dim.get(2), p_dim.get(3), p_dim.get(4), p_dim.get(5));
    }

    public static void setPDim(AABB aabb) {
        List<Integer> dim = List.of((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ, (int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ);
        P_DIM.set(dim);
        SPEC.save();
    }

    public static boolean isOpenAbilityDim() {
        return ABILiTY_DIM_OPEN.get();
    }

    public static void setOpenAbilityDim(boolean open) {
        ABILiTY_DIM_OPEN.set(open);
        SPEC.save();
    }

    public static boolean isOpenPDim() {
        return P_DIM_OPEN.get();
    }

    public static void setOpenPDim(boolean open) {
        P_DIM_OPEN.set(open);
        SPEC.save();
    }

    public static boolean isAbilityDim(BlockPos pos) {
        return getAbilityDim().contains(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean isPDim(BlockPos pos) {
        return getPDim().contains(pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean isDoorClose(BlockPos pos) {
        List<List<Integer>> closes = (List<List<Integer>>) CLOSE_DOORS.get();
        List<BlockPos> closes_pos = new ArrayList<>();
        for (List<Integer> list : closes) {
            closes_pos.add(new BlockPos(list.get(0), list.get(1), list.get(2)));
        }
        return closes_pos.contains(pos);
    }

    public static void addCloseDoor(BlockPos pos) {
        List<List<Integer>> closes = (List<List<Integer>>) CLOSE_DOORS.get();
        closes.add(List.of(pos.getX(), pos.getY(), pos.getZ()));
        CLOSE_DOORS.set(closes);
        SPEC.save();
    }

    public static void delCloseDoor(BlockPos pos) {
        List<List<Integer>> closes = (List<List<Integer>>) CLOSE_DOORS.get();
        closes.remove(List.of(pos.getX(), pos.getY(), pos.getZ()));
        CLOSE_DOORS.set(closes);
        SPEC.save();
    }

    public static List<NPC> getNpcs() {
        List<String> npcs = (List<String>) NPCS.get();
        List<NPC> answer = new ArrayList<>();
        for (String s : npcs) {
            answer.add(NPC.toNPC(s));
        }
        return answer;
    }

    public static void setNpcs (List<NPC> npcs) {
        List<String> newNpcs = new ArrayList<>();
        for (NPC npc : npcs) {
            newNpcs.add(npc.toString());
        }
        NPCS.set(newNpcs);
        SPEC.save();
    }
}
