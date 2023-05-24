package com.mahdigmk.apaa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

import static com.badlogic.gdx.Gdx.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mahdigmk.apaa.Controller.ControllerResponse;
import com.mahdigmk.apaa.Controller.LoginMenuController;
import com.mahdigmk.apaa.Controller.ProfileMenuController;
import com.mahdigmk.apaa.Model.Settings;
import com.mahdigmk.apaa.Model.User;
import com.mahdigmk.apaa.View.LoginMenu;
import com.mahdigmk.apaa.View.ProfileMenu;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;


public class AAGame extends Game {
    public static NativeFileChooser fileChooser;
    public static NativeFileChooserConfiguration fileChooserConfiguration;
    public static Texture[] defaultPfp = new Texture[4];
    public AssetManager assetManager = new AssetManager();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Settings getSettings() {
        return Settings.getSettings(user);
    }

    @Override
    public void create() {
        manageAssets();

        fileChooserConfiguration.directory = Gdx.files.absolute(System.getProperty("user.home"));

        user = User.getUser("user");
        setScreen(new LoginMenu(this));
    }

    private void manageAssets() {
        defaultPfp = new Texture[]{
                new Texture("defaultPfp/1.png"),
                new Texture("defaultPfp/2.png"),
                new Texture("defaultPfp/3.png"),
                new Texture("defaultPfp/4.png")
        };

        assetManager.load("neon/skin/default.json", Skin.class);
        assetManager.load("neon/skin/monochrome.json", Skin.class);

        assetManager.load("icons/change.png", Texture.class);
        assetManager.load("icons/accept_tick.png", Texture.class);

        assetManager.finishLoading();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        super.render();
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        for (Texture pfp : defaultPfp)
            pfp.dispose();

    }

    public Skin getSkin() {
        if (user != null && getSettings().isMonochromatic())
            return assetManager.get("neon/skin/monochrome.json");
        return assetManager.get("neon/skin/default.json");
    }
}
