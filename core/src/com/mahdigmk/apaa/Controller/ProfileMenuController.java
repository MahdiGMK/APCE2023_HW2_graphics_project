package com.mahdigmk.apaa.Controller;

import com.mahdigmk.apaa.Model.User;

import java.io.File;
import java.nio.file.Files;
import java.util.Random;

public class ProfileMenuController {
    public static Random random = new Random();

    public static ControllerResponse changeUsername(User user, String newUsername) {

        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changePassword(User user, String newPassword) {

        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse removeAccount(User user) {

        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse assingPfp(User user) {
        return assignPfp(user, random.nextInt(LoginMenuController.DEFAULT_PFP_COUNT));
    }

    public static ControllerResponse assignPfp(User user, int pfp) {
        if (pfp >= LoginMenuController.DEFAULT_PFP_COUNT || pfp < 0)
            return new ControllerResponse(1, "Invalid PFP id");
        user.setPfp(pfp);
        user.save();
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse assignPfp(User user, File file) {
        if (file == null || !file.exists()) return new ControllerResponse(1, "Invalid file path");
        
        return ControllerResponse.SUCCESS;
    }
}
