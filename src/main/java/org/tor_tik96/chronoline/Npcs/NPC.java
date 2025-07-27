package org.tor_tik96.chronoline.Npcs;

import org.tor_tik96.chronoline.hud.reputation.NpcReputations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPC {
    private final NpcReputations type;
    private int reputation;
    private List<String> positive = new ArrayList<>();
    private List<String> negative = new ArrayList<>();
    private boolean isAlive;
    private boolean isOpen;

    public NPC(NpcReputations type, int reputation, boolean isAlive, boolean isOpen) {
        this.type = type;
        this.reputation = reputation;
        this.isAlive = isAlive;
        this.isOpen = isOpen;
    }

    public NpcReputations getType() {
        return type;
    }

    public int getReputation() {
        return reputation;
    }

    public List<String> getPositive() {
        return positive;
    }

    public List<String> getNegative() {
        return negative;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setReputation(int reputation) {
        if (reputation > 100) {
            this.reputation = 100;
        } else this.reputation = Math.max(reputation, 0);
    }

    public void addPositive(String positive) {
        this.positive.add(positive);
    }

    public void delPositive(String positive) {
        this.positive.remove(positive);
    }

    public void addNegative(String negative) {
        this.negative.add(negative);
    }

    public void delNegative(String negative) {
        this.negative.remove(negative);
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        String answer = "";
        answer += "type:" + type.getName().getString() + "/" + "reputation:" + reputation + "/";
        answer += "positive:";
        for (String s: positive) {
            answer += s;
            if (positive.indexOf(s) != positive.size()-1) {
                answer += ",";
            }
        }
        answer += "/" + "negative:";
        for (String s: negative) {
            answer += s;
            if (negative.indexOf(s) != negative.size()-1) {
                answer += ",";
            }
        }
        answer += "/" + "alive:" + isAlive + "/" + "open:" + isOpen;
        return answer;
    }

    public static NPC toNPC(String s) {
        List<String> split = Arrays.stream(s.split("/")).toList();
        NpcReputations type = NpcReputations.getToName(Arrays.stream(split.get(0).split(":")).toList().get(1));
        int reputation = Integer.parseInt(Arrays.stream(split.get(1).split(":")).toList().get(1));
        List<String> positive = new ArrayList<>();
        if (!split.get(2).equals("positive:")) {
            for (String s1 : Arrays.stream(Arrays.stream(split.get(2).split(":")).toList().get(1).split(",")).toList()) {
                positive.add(s1);
            }
        }
        List<String> negative = new ArrayList<>();
        if (!split.get(3).equals("negative:")) {
            for (String s1 : Arrays.stream(Arrays.stream(split.get(3).split(":")).toList().get(1).split(",")).toList()) {
                negative.add(s1);
            }
        }
        boolean isAlive = Boolean.parseBoolean(Arrays.stream(split.get(4).split(":")).toList().get(1));
        boolean isOpen = Boolean.parseBoolean(Arrays.stream(split.get(5).split(":")).toList().get(1));
        NPC answer = new NPC(type, reputation, isAlive, isOpen);
        answer.positive.addAll(positive);
        answer.negative.addAll(negative);
        return answer;
    }
}
