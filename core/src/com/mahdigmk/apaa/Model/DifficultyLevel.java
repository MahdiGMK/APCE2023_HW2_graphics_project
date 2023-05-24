package com.mahdigmk.apaa.Model;

public enum DifficultyLevel {
    EASY(5, 1.2, 7, 5),
    MEDIUM(10, 1.5, 5, 3),
    HARD(15, 1.8, 3, 1.5);
    private final double rotationSpeed;
    private final double windSpeed;
    private final double frozenDuration;
    private final double totalSpaceRatio;

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
