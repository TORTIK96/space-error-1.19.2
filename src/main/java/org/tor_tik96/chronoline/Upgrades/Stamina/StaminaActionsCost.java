package org.tor_tik96.chronoline.Upgrades.Stamina;

public enum StaminaActionsCost {
    JUMPING (20), //Прыжок
    MINING (15), //Добыча блоков
    HITTING (10), //Удар
    PLACING (5), //Установка блоков
    RUNNING (10), //Бег (за каждую секунду минус очки)
    SHOOTING (10) //За каждый выстрел
    ;

    private final int cost;

    private StaminaActionsCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
