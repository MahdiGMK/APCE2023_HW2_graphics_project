package com.mahdigmk.apaa.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Controller.ControllerResponse;
import com.mahdigmk.apaa.Controller.LoginMenuController;
import com.mahdigmk.apaa.Model.User;


import static com.badlogic.gdx.Gdx.*;

public class LoginMenu extends Menu {
    Table table;
    TextField username, password;
    CheckBox rememberLogin;
    TextButton registerButton, loginButton, guestButton;
    Label errorLabel;
    private ControllerResponse response;

    public LoginMenu(AAGame game) {
        super(game);

        table = new Table(game.getSkin());
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        username = new TextField("", game.getSkin(), "login");
        password = new TextField("", game.getSkin(), "password");
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        rememberLogin = new CheckBox("remember login", game.getSkin());
        registerButton = new TextButton("register", game.getSkin());
        loginButton = new TextButton("login", game.getSkin());
        guestButton = new TextButton("login as guest", game.getSkin());

        rememberLogin.setChecked(true);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                register();
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                login();
            }
        });
        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginGuest();
            }
        });

        errorLabel = new Label("", game.getSkin());
        errorLabel.setColor(Color.RED);

//        table.debug();
        table.add(username);
        table.add(password);
        table.row();
        table.add(loginButton).width(100);
        table.add(registerButton).width(100);
        table.row();
        table.add(guestButton).width(150).colspan(2);
        table.row();
        table.add(rememberLogin).colspan(2).spaceBottom(20);
        table.row();
        table.add(errorLabel).colspan(2);

        uiStage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        User user = LoginMenuController.getRememberUser();
        game.setUser(user);
        if (user != null)
            setScreen(new MainMenu(game));
    }

    private void loginGuest() {
        response = LoginMenuController.loginGuest();
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        User user = User.getUser("_guest_");
        game.setUser(user);
        if (rememberLogin.isChecked())
            LoginMenuController.rememberUser(user);
        setScreen(new MainMenu(game));
    }

    private void login() {
        response = LoginMenuController.login(username.getText(), password.getText());
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        User user = User.getUser(username.getText());
        game.setUser(user);
        if (rememberLogin.isChecked())
            LoginMenuController.rememberUser(user);
        setScreen(new MainMenu(game));
    }

    private void register() {
        response = LoginMenuController.register(username.getText(), password.getText());
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        User user = User.getUser(username.getText());
        game.setUser(user);
        if (rememberLogin.isChecked())
            LoginMenuController.rememberUser(user);
        setScreen(new ProfileMenu(game));
    }
}
