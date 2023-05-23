package com.mahdigmk.apaa.Controller;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class ProfileMenuController {
    public static Random random = new Random();
    private static HashMap<User, Texture> userPfpTexture = new HashMap<>();

    public static ControllerResponse changeUsername(User user, String newUsername) {
        ControllerResponse usernameValidate = Utils.validUsername(newUsername);
        if (usernameValidate.getErrorCode() != 0)
            return usernameValidate;
        if (User.getUser(newUsername) != null)
            return new ControllerResponse(2, "new Username already exists");
        File src = new File("Data/Users/" + user.getUsername());
        File dst = new File("Data/Users/" + newUsername);
        try {
            Files.move(src.toPath(), dst.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setUsername(newUsername);
        user.save();
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.save();
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse removeAccount(User user) {
        File file = new File("Data/Users/" + user.getUsername());
        try {
            Files.walk(file.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse changePfp(User user) {
        return changePfp(user, random.nextInt(LoginMenuController.DEFAULT_PFP_COUNT));
    }

    public static ControllerResponse changePfp(User user, int pfp) {
        if (pfp >= LoginMenuController.DEFAULT_PFP_COUNT || pfp < 0)
            return new ControllerResponse(1, "Invalid PFP id");

        unloadPfpTexture(user);

        user.setPfp(pfp);
        user.save();
        return ControllerResponse.SUCCESS;
    }


    public static ControllerResponse changePfp(User user, File file) {
        if (file == null || !file.exists()) return new ControllerResponse(1, "Invalid file path");
        File dest = new File("Data/Users/" + user.getUsername() + "/pfp.png");

        unloadPfpTexture(user);

        dest.delete();
        try {
            Files.copy(file.toPath(), dest.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setPfp(-1);
        user.save();
        return ControllerResponse.SUCCESS;
    }

    private static void unloadPfpTexture(User user) {
        if (user.getPfp() != -1) return;
        Texture texture = userPfpTexture.get(user);
        if (texture != null) {
            texture.dispose();
            userPfpTexture.remove(user);
        }
    }

    public static Texture getPfpTexture(User user) {
        if (user.getPfp() != -1)
            return AAGame.defaultPfp[user.getPfp()];

        if (userPfpTexture.containsKey(user)) return userPfpTexture.get(user);
        Texture texture = new Texture(new FileHandle(new File("Data/Users/" + user.getUsername() + "/pfp.png")));
        userPfpTexture.put(user, texture);
        return texture;
    }
}
