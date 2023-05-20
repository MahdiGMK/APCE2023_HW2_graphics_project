package com.mahdigmk.apaa.Controller;

import com.mahdigmk.apaa.Model.User;

import java.util.Random;

public class LoginMenuController {
    public static final int DEFAULT_PFP_COUNT = 4;
    public static Random random = new Random();

    public static ControllerResponse register(String username, String password) {
        User user = User.getUser(username);
        if (user != null) return new ControllerResponse(1, "Username already exists");
        user = User.createUser(username, password, random.nextInt(DEFAULT_PFP_COUNT));
        return ControllerResponse.SUCCESS;
    }

    public static ControllerResponse login(String username, String password) {
        User user = User.getUser(username);
        if (user == null) return new ControllerResponse(1, "Username doesnt exist");
        if (!user.getPassword().equals(password)) return new ControllerResponse(2, "Wrong Password");
        return ControllerResponse.SUCCESS;
    }
}
