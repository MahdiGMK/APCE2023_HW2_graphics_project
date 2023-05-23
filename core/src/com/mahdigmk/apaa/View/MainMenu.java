package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mahdigmk.apaa.AAGame;

import static com.badlogic.gdx.Gdx.*;

public class MainMenu extends Menu {
    private Table table;
    private TextButton newGameButton, continueGameButton, startRaceButton, profileMenuButton, scoreMenuButton, settingsMenuButton, exitGameButton;

    public MainMenu(AAGame game) {
        super(game);
        table = new Table();
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());
        newGameButton = new TextButton("new game", game.getSkin());
        continueGameButton = new TextButton("continue game", game.getSkin());
        startRaceButton = new TextButton("start race", game.getSkin());
        profileMenuButton = new TextButton("profile menu", game.getSkin());
        scoreMenuButton = new TextButton("score menu", game.getSkin());
        settingsMenuButton = new TextButton("settings menu", game.getSkin());
        exitGameButton = new TextButton("exit game", game.getSkin());

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGame();
            }
        });
        continueGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueGame();
            }
        });
        startRaceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startRace();
            }
        });
        profileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                profileMenu();
            }
        });
        scoreMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scoreMenu();
            }
        });
        settingsMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsMenu();
            }
        });
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitGame();
            }
        });


        table.add(newGameButton).width(150);
        table.add(continueGameButton).width(150);
        table.row();
        table.add(startRaceButton).colspan(2).width(150);
        table.row();
        table.add(scoreMenuButton).colspan(2).width(150).spaceBottom(20);
        table.row();
        table.add(profileMenuButton).width(150);
        table.add(settingsMenuButton).width(150);
        table.row();
        table.add(exitGameButton).colspan(2).width(150);

        uiStage.addActor(table);
    }

    private void exitGame() {
        app.exit();
    }

    private void settingsMenu() {
        setScreen(new SettingsMenu(game));
    }

    private void scoreMenu() {
        setScreen(new ScoreMenu(game));
    }

    private void profileMenu() {
        setScreen(new ProfileMenu(game));
    }
    
    private void startRace() {

    }

    private void continueGame() {

    }

    private void newGame() {

    }
}
