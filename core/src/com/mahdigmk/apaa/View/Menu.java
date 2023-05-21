package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mahdigmk.apaa.AAGame;

import static com.badlogic.gdx.Gdx.*;

public abstract class Menu implements Screen {
    protected final AAGame game;
    protected final OrthographicCamera uiCamera;

    public Menu(AAGame game) {
        this.game = game;
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, graphics.getWidth(), graphics.getHeight());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float deltaTime) {

    }

    @Override
    public void resize(int width, int height) {
        uiCamera.viewportWidth = graphics.getWidth();
        uiCamera.viewportHeight = graphics.getHeight();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
