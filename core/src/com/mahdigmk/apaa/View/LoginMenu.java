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
    Skin skin;
    private ControllerResponse response;

    public LoginMenu(AAGame game) {
        super(game);

        skin = new Skin(files.internal("neon/skin/neon-ui.json"));
        table = new Table(skin);
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        username = new TextField("", skin, "login");
        password = new TextField("", skin, "password");
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        rememberLogin = new CheckBox("remember login", skin);
        registerButton = new TextButton("register", skin);
        loginButton = new TextButton("login", skin);
        guestButton = new TextButton("login as guest", skin);

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

        errorLabel = new Label("", skin);
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

    private void loginGuest() {
        response = LoginMenuController.loginGuest();
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        setScreen(new MainMenu(game, User.getUser("_guest_")));
    }

    private void login() {
        response = LoginMenuController.login(username.getText(), password.getText());
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        setScreen(new MainMenu(game, User.getUser(username.getText())));
    }

    private void register() {
        response = LoginMenuController.register(username.getText(), password.getText());
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() != 0) return;

        setScreen(new ProfileMenu(game, User.getUser(username.getText())));
    }


    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
