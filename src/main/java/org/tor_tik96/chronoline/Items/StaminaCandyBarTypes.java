package org.tor_tik96.chronoline.Items;

public enum StaminaCandyBarTypes {
    FLOWER (5, "§o§8*Чувствуется слабый аромат §fромашки§8...*"),
    DEFAULT (10, "§o§8*Чувствуется слабый аромат §6сладостей§8...*"),
    INK (15, "§o§8*Чувствуется слабый аромат §0чернил§8...*"),
    HONEY (25, "§o§8*Чувствуется слабый аромат §eмёда§8...*"),
    BERRY (30, "§o§8*Чувствуется слабый аромат §cягод§8...*"),
    ENDER (40, "§o§8*Чувствуется слабый аромат §2Энда§8...*"),
    RANDOM (0, "§o§8*По §2телу прох§5одит дрож§7ь, а н§1а вкус, при§6торный бато§0нчик§8...*")



    ;

    private final int stamina;
    private final String description;

    private StaminaCandyBarTypes(int stamina, String description) {
        this.stamina = stamina;
        this.description = description;
    }

    public int getStamina() {
        return stamina;
    }

    public String getDescription() {
        return description;
    }
}
