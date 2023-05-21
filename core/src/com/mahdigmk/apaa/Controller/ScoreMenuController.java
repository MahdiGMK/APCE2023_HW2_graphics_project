package com.mahdigmk.apaa.Controller;

import com.mahdigmk.apaa.Model.User;

public class ScoreMenuController {
    public static ControllerResponse changeScore(User user, int newScore) {
        user.setScore(newScore);
        user.save();
        return ControllerResponse.SUCCESS;
    }
}
