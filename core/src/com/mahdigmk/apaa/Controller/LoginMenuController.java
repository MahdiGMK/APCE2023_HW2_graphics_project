package com.mahdigmk.apaa.Controller;

import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.Settings;
import com.mahdigmk.apaa.Model.User;
import jdk.jshell.execution.Util;

import java.io.*;
import java.util.Random;

public class LoginMenuController {
    public static Random random = new Random();

    public static ControllerResponse register(String username, String password) {
        ControllerResponse usernameValidate = Utils.validUsername(username);
        if (usernameValidate.getErrorCode() != 0)
            return usernameValidate;
        User user = User.getUser(username);
        if (user != null) return new ControllerResponse(2, "Username already exists");
        user = new User(username, password, random.nextInt(AAGame.defaultPfp.length));
        Settings userSettings = new Settings();
        user.save();
        userSettings.save(user);
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse login(String username, String password) {
        User user = User.getUser(username);
        if (user == null) return new ControllerResponse(1, "Username doesnt exist");
        if (!user.getPassword().equals(password)) return new ControllerResponse(2, "Wrong Password");
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse loginGuest() {
        User user = User.getUser("_guest_");
        if (user == null) {
            user = new User("_guest_", "", 0);
            user.save();
            new Settings().save(user);
        }
        return ControllerResponse.SUCCESS;
    }

    public static void rememberUser(User user) {
        File file = new File("Data/Users/rememberLogin.txt");
        file.getParentFile().mkdirs();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            if (user != null)
                writer.write(user.getUsername());
            else
                writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getRememberUser() {
        File file = new File("Data/Users/rememberLogin.txt");
        if (!file.exists()) return null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            User user = User.getUser(reader.readLine());
            reader.close();
            return user;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
