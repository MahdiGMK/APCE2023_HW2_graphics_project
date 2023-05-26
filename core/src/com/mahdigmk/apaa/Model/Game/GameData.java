package com.mahdigmk.apaa.Model.Game;

import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import com.mahdigmk.apaa.Model.DifficultyLevel;
import com.mahdigmk.apaa.Model.Map;
import com.mahdigmk.apaa.Model.User;

import java.io.*;
import java.util.ArrayList;

public class GameData {
    private final DifficultyLevel difficultyLevel;
    private final Map map;
    private final int ballCount;
    private final float planetRadius, ballRadius;
    private final ArrayList<BallData> balls;
    private final boolean is2Player;
    private double rotation;
    private float playTime;

    public GameData(DifficultyLevel difficultyLevel, Map map, int ballCount, float range, float ballRadius, double rotation, ArrayList<BallData> balls, boolean is2Player) {
        this.difficultyLevel = difficultyLevel;
        this.map = map;
        this.ballCount = ballCount;
        this.planetRadius = range;
        this.ballRadius = ballRadius;
        this.rotation = rotation;
        this.balls = new ArrayList<>(balls);
        this.is2Player = is2Player;
    }

    public GameData(DifficultyLevel difficultyLevel, Map map, int ballCount, float radius, float ballRadius, boolean is2Player) {
        this.difficultyLevel = difficultyLevel;
        this.map = map;
        this.ballCount = ballCount;
        this.planetRadius = radius;
        this.ballRadius = ballRadius;
        this.is2Player = is2Player;
        rotation = 0;
        balls = new ArrayList<>();
        float slice = 2 * MathUtils.PI / map.getInitialBallCount();
        for (int i = 0; i < map.getInitialBallCount(); i++) balls.add(new BallData(slice * i, -1, -1));
    }

    public static GameData load(User user) {
        File file = new File("Data/GameSaves/" + user.getUsername() + ".json");

        if (!file.exists())
            return null;

        try {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            GameData data = gson.fromJson(reader, GameData.class);
            reader.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(User user) {
        File file = new File("Data/GameSaves/" + user.getUsername() + ".json");
        file.delete();
    }

    public float getPlayTime() {
        return playTime;
    }

    public void setPlayTime(float playTime) {
        this.playTime = playTime;
    }

    public boolean isIs2Player() {
        return is2Player;
    }

    public int getBallCount() {
        return ballCount;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public float getBallRadius() {
        return ballRadius;
    }

    public float getPlanetRadius() {
        return planetRadius;
    }

    public Map getMap() {
        return map;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public ArrayList<BallData> getBalls() {
        return balls;
    }

    public void save(User user) {
        File file = new File("Data/GameSaves/" + user.getUsername() + ".json");
        file.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate(BallData data) {
        final double circle = 2 * MathUtils.PI;
        double loc = data.location();
        for (BallData ball : balls) {
            double loc2 = ball.location();
            double delta = (loc2 - loc) % circle;
            delta = (delta + circle) % circle;
            delta = Math.min(delta, circle - delta);

            if (delta * planetRadius < ballRadius * 2)
                return false;
        }
        return true;
    }
}
