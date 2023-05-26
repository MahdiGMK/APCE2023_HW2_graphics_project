package com.mahdigmk.apaa.View.Game;

public class Timer {
    private float remaining;

    public Timer(float timer) {
        this.remaining = timer;
    }

    public void update(float deltaTime) {
        remaining -= deltaTime;
    }

    public boolean act() {
        return remaining <= 0;
    }

    public void init(float timer) {
        remaining = timer;
    }
}
