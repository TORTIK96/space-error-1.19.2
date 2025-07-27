package org.tor_tik96.chronoline.Npcs;

import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.reputation.NpcReputations;

import java.util.ArrayList;
import java.util.List;

public class NpcUtils {
    public static final List<String> finalNPCs = List.of(
            new NPC(NpcReputations.PERDUN, 0, true, true).toString(),
            new NPC(NpcReputations.LAFARIK, 70, false, true).toString()
    );

    public static List<NPC> getAllNpc() {
        return Config.getNpcs();
    }

    public static List<NPC> getOpenNpc() {
        List<NPC> npcs = Config.getNpcs();
        List<NPC> answer = new ArrayList<>();
        for (NPC npc : npcs) {
            if (npc.isOpen()) {
                answer.add(npc);
            }
        }
        return answer;
    }

    public static List<NPC> getCloseNpc() {
        List<NPC> npcs = Config.getNpcs();
        List<NPC> answer = new ArrayList<>();
        for (NPC npc : npcs) {
            if (!npc.isOpen()) {
                answer.add(npc);
            }
        }
        return answer;
    }

    public static List<NPC> getAliveNpc() {
        List<NPC> npcs = Config.getNpcs();
        List<NPC> answer = new ArrayList<>();
        for (NPC npc : npcs) {
            if (npc.isAlive()) {
                answer.add(npc);
            }
        }
        return answer;
    }

    public static List<NPC> getDieNpc() {
        List<NPC> npcs = Config.getNpcs();
        List<NPC> answer = new ArrayList<>();
        for (NPC npc : npcs) {
            if (!npc.isAlive()) {
                answer.add(npc);
            }
        }
        return answer;
    }

    public static NPC getNpc(NpcReputations type) {
        List<NPC> npcs = Config.getNpcs();
        NPC answer = null;
        for (NPC npc : npcs) {
            if (npc.getType().equals(type)) {
                answer = npc;
            }
        }
        return answer;
    }

    public static Integer getNpcIndex(NpcReputations type) {
        List<NPC> npcs = Config.getNpcs();
        int answer = -1;
        for (NPC npc : npcs) {
            if (npc.getType().equals(type)) {
                answer = npcs.indexOf(npc);
            }
        }
        return answer;
    }

    public static void addNpc(NPC npc) {
        List<NPC> npcs = Config.getNpcs();
        npcs.add(npc);
        Config.setNpcs(npcs);
    }

    public static void updateReputation(NpcReputations type, int reputation) {
        NPC npc = getNpc(type);
        if (npc != null) {
            int index = getNpcIndex(type);
            npc.setReputation(reputation);
            List<NPC> npcs = Config.getNpcs();
            npcs.set(index, npc);
            Config.setNpcs(npcs);
        }
    }

    public static void updateAlive(NpcReputations type, boolean alive) {
        NPC npc = getNpc(type);
        if (npc != null) {
            int index = getNpcIndex(type);
            npc.setAlive(alive);
            List<NPC> npcs = Config.getNpcs();
            npcs.set(index, npc);
            Config.setNpcs(npcs);
        }
    }

    public static void updateOpen(NpcReputations type, boolean open) {
        NPC npc = getNpc(type);
        if (npc != null) {
            int index = getNpcIndex(type);
            npc.setOpen(open);
            List<NPC> npcs = Config.getNpcs();
            npcs.set(index, npc);
            Config.setNpcs(npcs);
        }
    }

    public static void updateNpc(NpcReputations type, NPC npc) {
        int index = getNpcIndex(type);
        if (index != -1) {
            List<NPC> npcs = Config.getNpcs();
            npcs.set(index, npc);
            Config.setNpcs(npcs);
        }
    }

}
