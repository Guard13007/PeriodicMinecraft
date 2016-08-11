package com.guard13007.periodic.api;

public enum RedstoneSetting {
    ENERGY_NEED, ENERGY_FULL, ENERGY_EMPTY, INVENTORY_SPACE, INVENTORY_FULL,
    INVENTORY_EMPTY, TANK_NEED, TANK_FULL, TANK_EMPTY, TASK_NONE, TASK_IN_PROGRESS, NULL;

    public static RedstoneSetting[] VALID_SETTINGS = {ENERGY_NEED, ENERGY_FULL, ENERGY_EMPTY,
            INVENTORY_SPACE, INVENTORY_FULL, INVENTORY_EMPTY, TANK_NEED, TANK_FULL, TANK_EMPTY,
            TASK_NONE, TASK_IN_PROGRESS, NULL};

    public static RedstoneSetting getSetting(int i) {
        return i < VALID_SETTINGS.length ? VALID_SETTINGS[i] : NULL;
    }
}
