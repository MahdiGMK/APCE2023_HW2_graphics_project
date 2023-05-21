package com.mahdigmk.apaa;

import com.badlogic.gdx.Gdx;
import com.mahdigmk.apaa.Controller.ControllerResponse;
import com.mahdigmk.apaa.Controller.LoginMenuController;
import com.mahdigmk.apaa.Controller.ProfileMenuController;
import com.mahdigmk.apaa.Model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {
    ControllerResponse response;

    @Test
    @Order(0)
    void register() {
        if (User.getUser("testUser") != null)
            ProfileMenuController.removeAccount(User.getUser("testUser"));

        response = LoginMenuController.register("testUser", "testPassword");
        assertEquals(0, response.getErrorCode());
        response = LoginMenuController.register("testUser", "testPassword");
        assertEquals(1, response.getErrorCode());
    }

    @Test
    @Order(1)
    void login() {
        response = LoginMenuController.login("testUser2", "password");
        assertEquals(1, response.getErrorCode());
        response = LoginMenuController.login("testUser", "password");
        assertEquals(2, response.getErrorCode());
        response = LoginMenuController.login("testUser", "testPassword");
        assertEquals(0, response.getErrorCode());
    }

    @Test
    @Order(2)
    void cngUsername() {
        LoginMenuController.register("user", "user");
        User user = User.getUser("testUser");
        response = ProfileMenuController.changeUsername(user, "user");
        assertEquals(1, response.getErrorCode());
        response = ProfileMenuController.changeUsername(user, "newUser");
        assertEquals(0, response.getErrorCode());

        assertNull(User.getUser("testUser"));
        user = User.getUser("newUser");
        assertNotNull(user);

        response = ProfileMenuController.changeUsername(user, "testUser");
        assertEquals(0, response.getErrorCode());

        assertNull(User.getUser("newUser"));
        user = User.getUser("testUser");
        assertNotNull(user);
    }

    @Test
    @Order(2)
    void cngPassword() {
        User user = User.getUser("testUser");
        response = LoginMenuController.login("testUser", "testPassword");
        assertEquals(0, response.getErrorCode());
        response = ProfileMenuController.changePassword(user, "newPassword");
        assertEquals(0, response.getErrorCode());
        response = LoginMenuController.login("testUser", "testPassword");
        assertEquals(2, response.getErrorCode());
        response = LoginMenuController.login("testUser", "newPassword");
        assertEquals(0, response.getErrorCode());
        response = ProfileMenuController.changePassword(user, "testPassword");
        assertEquals(0, response.getErrorCode());
        response = LoginMenuController.login("testUser", "testPassword");
        assertEquals(0, response.getErrorCode());
    }

    @Test
    @Order(2)
    void cngPfp() {
        User user = User.getUser("testUser");
        response = ProfileMenuController.changePfp(user, 100);
        assertEquals(1, response.getErrorCode());
        response = ProfileMenuController.changePfp(user, 1);
        assertEquals(0, response.getErrorCode());
        assertEquals(1, user.getPfp());
        response = ProfileMenuController.changePfp(user, new File("Data/testPfp.png"));
        assertEquals(0, response.getErrorCode());
        assertEquals(-1, user.getPfp());
    }
}