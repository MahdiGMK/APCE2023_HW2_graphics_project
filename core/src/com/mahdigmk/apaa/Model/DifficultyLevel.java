package com.mahdigmk.apaa.Model;

public enum DifficultyLevel {
    EASY(0.4, 0.2, 7, 5),
    MEDIUM(0.8, 0.6, 5, 3),
    HARD(1.4, 1.2, 3, 2);
    private final double windSpeed;
    private final double frozenDuration;
    private final double totalSpaceRatio;
    private final double rotationSpeed;

    DifficultyLevel(double rotationSpeed, double windSpeed, double frozenDuration, double totalSpaceRatio) {
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.frozenDuration = frozenDuration;
        this.totalSpaceRatio = totalSpaceRatio;
    }

    public double getTotalSpaceRatio() {
        return totalSpaceRatio;
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
