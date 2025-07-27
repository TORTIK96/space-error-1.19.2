package org.tor_tik96.chronoline;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.compress.utils.Lists;
import org.tor_tik96.chronoline.Npcs.NPC;
import org.tor_tik96.chronoline.Npcs.NpcUtils;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Chronoline.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NPCS = BUILDER.comment("репутация (НИЧЕГО НЕ ТРОГАТЬ ЭТО ОЧЕНЬ ВАЖНО!)").defineListAllowEmpty(List.of("npcs"), () -> NpcUtils.finalNPCs, Config::validateNpcs);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean validateNpcs(final Object object) {
        return true;
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
