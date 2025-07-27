package org.tor_tik96.chronoline.hud.reputation;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.tor_tik96.chronoline.Chronoline;

public enum NpcReputations {
    PERDUN ("Пуэр", "perdun", 0, 0, 36, 70),
    LAFARIK ("Лайфарий", "lafarik", 0, 0, 35, 70)

    ;

    private final Component name;
    private final ResourceLocation texture;
    private final int u1;
    private final int v1;
    private final int u2;
    private final int v2;


    NpcReputations(String name, String texture, int u1, int v1, int u2, int v2) {
        this.name = Component.literal(name);
        this.texture = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/characters/" + texture + ".png");
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
    }

    public Component getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getU1() {
        return u1;
    }

    public int getV1() {
        return v1;
    }

    public int getU2() {
        return u2;
    }

    public int getV2() {
        return v2;
    }

    public static NpcReputations getToName(String name) {
        NpcReputations answer = null;

        for (NpcReputations type : NpcReputations.values()) {
            if (type.getName().getString().equals(name)) {
                answer = type;
            }
        }

        return answer;
    }
}
