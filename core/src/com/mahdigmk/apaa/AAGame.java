package com.mahdigmk.apaa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

import static com.badlogic.gdx.Gdx.*;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mahdigmk.apaa.Model.User;

import java.io.File;


public class AAGame extends Game {
    Sprite sprite;
    SpriteBatch batch;

    @Override
    public void create() {
        sprite = new Sprite(new Texture(files.external("Desktop/user1.img")));
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.75f, 0.75f, 0.75f, 1);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
    }

}
