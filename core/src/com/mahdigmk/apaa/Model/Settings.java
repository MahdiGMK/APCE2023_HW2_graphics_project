package com.mahdigmk.apaa.Model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
    public static final int MAX_BALL_COUNT = 10;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
    private int ballCount = 5;
    private boolean muteMusic = false;
    private boolean monochromatic = false;
    private Language language = Language.ENGLISH;
    private int functionKey = Input.Keys.SPACE;

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
        this.ballCount = MathUtils.clamp(ballCount, 0, MAX_BALL_COUNT);
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

    public Language isPersian() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(int functionKey) {
        this.functionKey = functionKey;
    }
}
