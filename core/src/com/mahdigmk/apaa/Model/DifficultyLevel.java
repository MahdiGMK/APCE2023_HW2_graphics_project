package com.mahdigmk.apaa.Model;

public enum DifficultyLevel {
    EASY(5, 1.2, 7),
    MEDIUM(10, 1.5, 5),
    HARD(15, 1.8, 3);
    private final double rotationSpeed;
    private final double windSpeed;
    private final double frozenDuration;

    DifficultyLevel(double rotationSpeed, double windSpeed, double frozenDuration) {
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.frozenDuration = frozenDuration;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getFrozenDuration() {
        return frozenDuration;
    }
}
