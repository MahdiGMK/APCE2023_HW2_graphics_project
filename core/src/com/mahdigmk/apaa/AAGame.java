package com.mahdigmk.apaa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

import static com.badlogic.gdx.Gdx.*;

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

import java.io.File;


public class AAGame extends Game {
    public static Texture[] defaultPfp;
    public AssetManager assetManager = new AssetManager();
    private Skin skin;
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

        skin = new Skin(files.internal("neon/skin/neon-ui.json"));
        user = User.getUser("user");
        setScreen(new ProfileMenu(this));
    }

    private void manageAssets() {
        defaultPfp = new Texture[]{
                new Texture("defaultPfp/1.png"),
                new Texture("defaultPfp/2.png"),
                new Texture("defaultPfp/3.png"),
                new Texture("defaultPfp/4.png")
        };

        assetManager.load("icons/change.png", Texture.class);

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
        skin.dispose();
        for (Texture pfp : defaultPfp)
            pfp.dispose();

    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
