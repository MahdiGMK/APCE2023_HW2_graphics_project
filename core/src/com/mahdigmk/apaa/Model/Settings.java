package com.mahdigmk.apaa.Model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import com.mahdigmk.apaa.View.Game.GameMusic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
    public static final int MIN_BALL_COUNT = 10, MAX_BALL_COUNT = 50;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    private Map map = Map.GRASS_LANDS;
    private int ballCount = 20;
    private boolean muteMusic = false;
    private boolean monochromatic = false;
    //    private Language language = Language.ENGLISH;
    private int p1FunctionKey = Input.Keys.SPACE;
    private int p2FunctionKey = Input.Keys.ENTER;
    private GameMusic selectedMusic = GameMusic.DARWINIA_01;

    public static Settings getSettings(User user) {
        File file = new File("Data/Users/" + user.getUsername() + "/settings.json");
        if (!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            Settings settings = gson.fromJson(reader, Settings.class);
            reader.close();
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getP1FunctionKey() {
        return p1FunctionKey;
    }

    public void setP1FunctionKey(int p1FunctionKey) {
        this.p1FunctionKey = p1FunctionKey;
    }

    public int getP2FunctionKey() {
        return p2FunctionKey;
    }

    public void setP2FunctionKey(int p2FunctionKey) {
        this.p2FunctionKey = p2FunctionKey;
    }

    public void save(User user) {
        File file = new File("Data/Users/" + user.getUsername() + "/settings.json");

        Gson gson = new Gson();
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = MathUtils.clamp(ballCount, MIN_BALL_COUNT, MAX_BALL_COUNT);
    }

    public boolean isMuteMusic() {
        return muteMusic;
    }

    public void setMuteMusic(boolean muteMusic) {
        this.muteMusic = muteMusic;
    }

    public boolean isMonochromatic() {
        return monochromatic;
    }

    public void setMonochromatic(boolean monochromatic) {
        this.monochromatic = monochromatic;
    }

    public GameMusic getSelectedMusic() {
        return selectedMusic;
    }

    public void setSelectedMusic(GameMusic selectedMusic) {
        this.selectedMusic = selectedMusic;
    }
}
