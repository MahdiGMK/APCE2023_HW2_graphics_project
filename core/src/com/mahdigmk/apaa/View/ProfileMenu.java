package com.mahdigmk.apaa.View;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Controller.ControllerResponse;
import com.mahdigmk.apaa.Controller.LoginMenuController;
import com.mahdigmk.apaa.Controller.ProfileMenuController;
import com.mahdigmk.apaa.Model.User;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import static com.badlogic.gdx.Gdx.*;

public class ProfileMenu extends Menu {
    private ControllerResponse response;
    private User user;
    private Table mainTable, pfpTable;
    // Main Table
    private ShadedImage pfp;
    private TextField username, password;
    private IconButton changePfpButton, changeUsernameButton, changePasswordButton;
    private TextButton mainMenuButton, logoutButton, removeAccountButton;
    private Label errorLabel;
    // pfp table
    private ShadedImage pic[];
    private IconButton picButton[];
    private TextButton back, selectRandom, selectFile;
    private ShaderProgram shader = null;

    public ProfileMenu(AAGame game) {
        super(game);
        this.user = game.getUser();
        if (game.getSettings().isMonochromatic())
            shader = GrayscaleShader.grayscaleShader;

        initMainTable();
        initPfpTable();
    }

    private void initMainTable() {
        mainTable = new Table();
        mainTable.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        pfp = new ShadedImage(ProfileMenuController.getPfpTexture(user), shader);

        username = new TextField(user.getUsername(), game.getSkin(), "login");
        password = new TextField(user.getPassword(), game.getSkin(), "password");
        Texture changeIcon = game.assetManager.get("icons/change.png");
        changePfpButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        changeUsernameButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        changePasswordButton = new IconButton(changeIcon, 0.5f, game.getSkin());
        mainMenuButton = new TextButton("back to main menu", game.getSkin());
        logoutButton = new TextButton("logout", game.getSkin());
        removeAccountButton = new TextButton("remove account", game.getSkin());
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
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logout();
            }
        });
        removeAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeAccount();
            }
        });
        float pfpW = pfp.getWidth();
        float pfpH = pfp.getHeight();
        final float pfpSize = 150;

        mainTable.add(pfp).width(pfpSize).height(pfpH / pfpW * pfpSize);
        mainTable.add(changePfpButton);
        mainTable.row();
        mainTable.add(username);
        mainTable.add(changeUsernameButton);
        mainTable.row();
        mainTable.add(password);
        mainTable.add(changePasswordButton).spaceBottom(20);
        mainTable.row();
        mainTable.add(errorLabel).colspan(2).spaceBottom(20);
        mainTable.row();
        mainTable.add(mainMenuButton).colspan(2);
        mainTable.row();
        mainTable.add(logoutButton).width(150);
        mainTable.add(removeAccountButton).width(150);

        goToMain();
    }

    private void removeAccount() {
        LoginMenuController.rememberUser(null);
        ProfileMenuController.removeAccount(user);
        game.setUser(null);
        setScreen(new LoginMenu(game));
    }

    private void logout() {
        LoginMenuController.rememberUser(null);
        game.setUser(null);
        setScreen(new LoginMenu(game));
    }

    private void initPfpTable() {
        pfpTable = new Table();
        pfpTable.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        pic = new ShadedImage[AAGame.defaultPfp.length];
        picButton = new IconButton[AAGame.defaultPfp.length];
        Texture tickIcon = game.assetManager.get("icons/accept_tick.png", Texture.class);
        for (int i = 0; i < AAGame.defaultPfp.length; i++) {
            pic[i] = new ShadedImage(AAGame.defaultPfp[i], shader);
            picButton[i] = new IconButton(tickIcon, 0.5f, game.getSkin());
            int finalI = i;
            picButton[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ProfileMenuController.changePfp(user, finalI);
                    refreshPfpImg();
                    goToMain();
                }
            });
        }
        back = new TextButton("back", game.getSkin());
        selectRandom = new TextButton("select random", game.getSkin());
        selectFile = new TextButton("select file", game.getSkin());

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToMain();
            }
        });
        selectRandom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProfileMenuController.changePfp(user);
                refreshPfpImg();
                goToMain();
            }
        });
        selectFile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choosePfpFile();
            }
        });

        Table optionsTable = new Table();

        for (Image image : pic) optionsTable.add(image).height(150).width(150);
        optionsTable.row();
        for (IconButton iconButton : picButton) optionsTable.add(iconButton);
        optionsTable.row();

        pfpTable.add(optionsTable).colspan(3);
        pfpTable.row();
        pfpTable.add(back).width(150);
        pfpTable.add(selectRandom).width(150);
        pfpTable.add(selectFile).width(150);
    }

    private void goToMain() {
        uiStage.clear();
        uiStage.addActor(mainTable);
    }

    private void goToPfp() {
        uiStage.clear();
        uiStage.addActor(pfpTable);
    }

    private void mainMenu() {
        setScreen(new MainMenu(game));
    }

    private void refreshPfpImg() {
        pfp.setDrawable(new SpriteDrawable(new Sprite(ProfileMenuController.getPfpTexture(user))));
    }

    private void changePfp() {
        uiStage.clear();
        uiStage.addActor(pfpTable);
    }

    private void choosePfpFile() {
        AAGame.fileChooser.chooseFile(AAGame.fileChooserConfiguration, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                if (file.name().endsWith(".png")) {
                    ProfileMenuController.changePfp(user, file.file());
                    refreshPfpImg();
                    goToMain();
                }
            }

            @Override
            public void onCancellation() {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
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
