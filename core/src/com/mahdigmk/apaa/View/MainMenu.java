package com.mahdigmk.apaa.View;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.Game.GameData;

import static com.badlogic.gdx.Gdx.*;

public class MainMenu extends Menu {
    private Table table;
    private TextButton newGameButton, continueGameButton, startCoopButton, profileMenuButton, scoreMenuButton, settingsMenuButton, exitGameButton;

    public MainMenu(AAGame game) {
        super(game);
        table = new Table();
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());
        newGameButton = new TextButton("new game", game.getSkin());
        continueGameButton = new TextButton("continue game", game.getSkin());
        startCoopButton = new TextButton("start Co-op", game.getSkin());
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
        startCoopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startCoop();
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
        table.add(startCoopButton).colspan(2).width(150);
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

    private void startCoop() {
        float ballRadius = 20;
        int ballCount = game.getSettings().getBallCount() + game.getSettings().getMap().getInitialBallCount();
        double planetRadius =
                game.getSettings().getDifficultyLevel().getTotalSpaceRatio() * ballRadius * ballCount / MathUtils.PI; // R = d * num/pi
        setScreen(
                new GameMenu(
                        game,
                        new GameData(
                                game.getSettings().getDifficultyLevel(),
                                game.getSettings().getMap(),
                                game.getSettings().getBallCount(),
                                (float) planetRadius, ballRadius,
                                true
                        ))
        );

    }

    private void continueGame() {
        GameData data = GameData.load(game.getUser());
        if (data == null) {
            newGame();
            return;
        }
        setScreen(new GameMenu(game, data));
    }

    private void newGame() {
        float ballRadius = 20;
        int ballCount = game.getSettings().getBallCount() + game.getSettings().getMap().getInitialBallCount();
        double planetRadius =
                game.getSettings().getDifficultyLevel().getTotalSpaceRatio() * ballRadius * ballCount / MathUtils.PI; // R = d * num/pi
        setScreen(
                new GameMenu(
                        game,
                        new GameData(
                                game.getSettings().getDifficultyLevel(),
                                game.getSettings().getMap(),
                                game.getSettings().getBallCount(),
                                (float) planetRadius, ballRadius,
                                false
                        ))
        );
    }
}
