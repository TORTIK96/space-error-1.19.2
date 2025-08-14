package org.tor_tik96.chronoline.hud.Abilities;

public enum AbilityTypes {
    DIGGING ("Усовершенствованное копание", "Способность, позволяющая разблокировать навык копания киркой – 3x1", 4, true),
    UNDERWATER ("Подводный Обитатель", "Способность, улучшает поведение игрока в воде. Добавляет скорость, и не тратит воздух при погружение", 5, true),
    MASTER_CHEF ("Мастер Шеф", "При перемещение предмета во вторую руку, предмет зажаривается", 3, true),
    AXE ("Великий Топор", "Способность, позволит разрушить всё дерево целиком – одним взмахом топора", 1, true),
    NIGHT_VISION ("Зоркий Глаз", "Разблокирует способность видеть всё в темноте", 2, true),
    DIMENSION ("Измерение Падших", "Из-за глюков в реальности, образовалось никому неизвестное пространство", 15, false),
    STUNNING ("Великий Воин", "Во время сражения с монстрами, можно вскрикнуть оглушив всех вокруг себя", 8, true),
    NO_CLOTHES ("Одежда не нужна", "Способность, позволит стать быстрее – когда игрок не носит одежду/броню", 9, false),
    CRAFT_FRAGMENT ("Мастер на все руки", "Возможность получить искажённый осколок при крафте какого-либо предмета с шансом в 10%", 6, false)
    ;

    private final String name;
    private final String description;
    private final int cost;
    private final boolean button;

    AbilityTypes(String name, String description, int cost, boolean button) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public boolean isButton() {
        return button;
    }
}
