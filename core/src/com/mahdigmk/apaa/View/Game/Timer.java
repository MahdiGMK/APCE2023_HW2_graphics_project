package com.mahdigmk.apaa.View.Game;

public class Timer {
    private float remaining;
    private float speed;

    public Timer(float timer) {
        this.remaining = timer;
    }

    public float getRemaining() {
        return remaining;
    }

    public void update(float deltaTime) {
        remaining -= deltaTime * speed;
    }

    public boolean act() {
        return remaining <= 0;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void init(float timer) {
        remaining = timer;
    }
}
