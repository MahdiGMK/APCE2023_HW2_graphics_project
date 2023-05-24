package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mahdigmk.apaa.AAGame;

import static com.badlogic.gdx.Gdx.*;

public abstract class Menu implements Screen {
    protected final AAGame game;
    protected final Stage uiStage;

    public Menu(AAGame game) {
        this.game = game;
        uiStage = new Stage();
        input.setInputProcessor(uiStage);
//        uiStage.setViewport(new FillViewport(graphics.getWidth(), graphics.getHeight()));
        uiStage.setViewport(new FitViewport(graphics.getWidth(), graphics.getHeight()));
    }

    protected Menu getScreen() {
        return (Menu) game.getScreen();
    }

    protected void setScreen(Menu menu) {
        game.setScreen(menu);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float deltaTime) {
        uiStage.act(deltaTime);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }
}
