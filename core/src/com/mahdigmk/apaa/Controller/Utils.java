package com.mahdigmk.apaa.Controller;

public class Utils {
    public static ControllerResponse validUsername(String username) {
        if (username.length() < 4 || username.length() > 32 ||
                !username.matches("[\\S]+") || username.equals("_guest_"))
            return new ControllerResponse(1, "Invalid Username");
        return ControllerResponse.SUCCESS;
    }
}
