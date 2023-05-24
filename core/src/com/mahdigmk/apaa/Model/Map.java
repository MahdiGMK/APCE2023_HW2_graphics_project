package com.mahdigmk.apaa.Model;

public enum Map {
    GRASS_LANDS(3),
    DRY_LANDS(6),
    FIRE_LANDS(10);

    private final int initialCount;

    private Map(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getInitialCount() {
        return initialCount;
    }
}
