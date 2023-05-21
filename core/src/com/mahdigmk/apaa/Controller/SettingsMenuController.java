package com.mahdigmk.apaa.Controller;

import com.badlogic.gdx.Input;
import com.mahdigmk.apaa.Model.DifficultyLevel;
import com.mahdigmk.apaa.Model.Language;
import com.mahdigmk.apaa.Model.Settings;
import com.mahdigmk.apaa.Model.User;

public class SettingsMenuController {
    public static ControllerResponse changeDifficultyLevel(User user, Settings settings, DifficultyLevel newValue) {
        if (newValue == null) return new ControllerResponse(1, "Invalid difficulty level");
        settings.setDifficultyLevel(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changeBallCount(User user, Settings settings, int newValue) {
        if (newValue < 0 || newValue > Settings.MAX_BALL_COUNT) return new ControllerResponse(1, "Invalid ball count");
        settings.setBallCount(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changeMuteMusic(User user, Settings settings, boolean newValue) {
        settings.setMuteMusic(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changeMonochromatic(User user, Settings settings, boolean newValue) {
        settings.setMonochromatic(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changeLanguage(User user, Settings settings, Language newValue) {
        if (newValue == null) return new ControllerResponse(1, "Invalid language");
        settings.setLanguage(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changeFunctionKey(User user, Settings settings, int newValue) {
        settings.setFunctionKey(newValue);
        settings.save(user);
        return ControllerResponse.SUCCESS;
    }
}
