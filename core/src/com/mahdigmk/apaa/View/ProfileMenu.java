package com.mahdigmk.apaa.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Controller.ControllerResponse;
import com.mahdigmk.apaa.Controller.ProfileMenuController;
import com.mahdigmk.apaa.Model.User;

import static com.badlogic.gdx.Gdx.*;

public class ProfileMenu extends Menu {
    private ControllerResponse response;
    private User user;
    private Table table;
    private Image pfp;
    private TextField username, password;
    private IconButton changePfpButton, changeUsernameButton, changePasswordButton;
    private TextButton mainMenuButton;
    private Label errorLabel;

    public ProfileMenu(AAGame game) {
        super(game);
        this.user = game.getUser();

        table = new Table();
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        pfp = new Image(ProfileMenuController.getPfpTexture(user));
        username = new TextField(user.getUsername(), game.getSkin(), "login");
        password = new TextField(user.getPassword(), game.getSkin(), "password");
        Texture changeIcon = game.assetManager.get("icons/change.png");
        changePfpButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        changeUsernameButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        changePasswordButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        mainMenuButton = new TextButton("back to main menu", game.getSkin());
        errorLabel = new Label("", game.getSkin());
        errorLabel.setColor(Color.RED);

        changePfpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePfp();
            }
        });
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeUsername();
            }
        });
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePassword();
            }
        });
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu();
            }
        });

        table.add(pfp);
        table.add(changePfpButton);
        table.row();
        table.add(username);
        table.add(changeUsernameButton);
        table.row();
        table.add(password);
        table.add(changePasswordButton).spaceBottom(20);
        table.row();
        table.add(errorLabel).colspan(2).spaceBottom(20);
        table.row();
        table.add(mainMenuButton).colspan(2);

        uiStage.addActor(table);
    }

    private void mainMenu() {
    }

    private void changePfp() {

    }

    private void changeUsername() {
        response = ProfileMenuController.changeUsername(user, username.getText());
        updateMessage();
        if (response.getErrorCode() != 0) return;


        user = User.getUser(username.getText());
        game.setUser(user);
    }

    private void changePassword() {
        response = ProfileMenuController.changePassword(user, password.getText());
        updateMessage();
    }

    private void updateMessage() {
        errorLabel.setText(response.getResponseMessage());
        if (response.getErrorCode() == 0) errorLabel.setColor(Color.GREEN);
        else errorLabel.setColor(Color.RED);
    }
}
