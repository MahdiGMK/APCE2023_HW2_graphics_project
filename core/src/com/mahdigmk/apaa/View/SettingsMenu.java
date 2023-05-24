package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Controller.SettingsMenuController;
import com.mahdigmk.apaa.Model.DifficultyLevel;
import com.mahdigmk.apaa.Model.Map;
import com.mahdigmk.apaa.Model.Settings;
import com.mahdigmk.apaa.Model.User;

import static com.badlogic.gdx.Gdx.*;

public class SettingsMenu extends Menu {
    private final User user;
    private final Settings settings;

    private Table table;

    private Label difficultyLevelLabel, mapLabel, ballCountLabel, p1ButtonLabel, p2ButtonLabel;
    private int p1FunctionKey, p2FunctionKey;
    private SelectBox<DifficultyLevel> difficultyLevelSelectBox;
    private SelectBox<Map> mapSelectBox;
    private Slider ballCountSlider;
    private CheckBox muteButton, monochromaticButton;
    private TextButton selectP1Button, selectP2Button, applyButton, mainMenuButton;

    public SettingsMenu(AAGame game) {
        super(game);
        user = game.getUser();
        settings = Settings.getSettings(user);

        table = new Table();
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        difficultyLevelLabel = new Label("Difficulty level", game.getSkin());
        difficultyLevelSelectBox = new SelectBox<>(game.getSkin());
        difficultyLevelSelectBox.setItems(DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.HARD);
        difficultyLevelSelectBox.setSelected(settings.getDifficultyLevel());

        mapLabel = new Label("Map", game.getSkin());
        mapSelectBox = new SelectBox<>(game.getSkin());
        mapSelectBox.setItems(Map.GRASS_LANDS, Map.DRY_LANDS, Map.FIRE_LANDS);
        mapSelectBox.setSelected(settings.getMap());

        ballCountLabel = new Label("Ball count : " + settings.getBallCount(), game.getSkin());
        ballCountSlider = new Slider(Settings.MIN_BALL_COUNT, Settings.MAX_BALL_COUNT, 1, false, game.getSkin());
        ballCountSlider.setValue(settings.getBallCount());
        ballCountSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ballCountLabel.setText("Ball count : " + (int) ballCountSlider.getValue());
            }
        });

        muteButton = new CheckBox("mute", game.getSkin());
        muteButton.setChecked(settings.isMuteMusic());

        monochromaticButton = new CheckBox("monochromatic", game.getSkin());
        monochromaticButton.setChecked(settings.isMonochromatic());

        p1ButtonLabel = new Label("main function key", game.getSkin());
        p2ButtonLabel = new Label("alt function key", game.getSkin());
        p1FunctionKey = settings.getP1FunctionKey();
        p2FunctionKey = settings.getP2FunctionKey();
        selectP1Button = new TextButton(Input.Keys.toString(p1FunctionKey), game.getSkin());
        selectP2Button = new TextButton(Input.Keys.toString(p2FunctionKey), game.getSkin());
        applyButton = new TextButton("apply", game.getSkin());
        mainMenuButton = new TextButton("main menu", game.getSkin());

        selectP1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.setInputProcessor(new KeyReader(0));
            }
        });
        selectP2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.setInputProcessor(new KeyReader(1));
            }
        });
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                apply();
            }
        });
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu();
            }
        });


        table.add(difficultyLevelLabel);
        table.add(difficultyLevelSelectBox);
        table.row();
        table.add(mapLabel);
        table.add(mapSelectBox);
        table.row();
        table.add(ballCountLabel);
        table.add(ballCountSlider);
        table.row();
        table.add(muteButton).colspan(2);
        table.row();
        table.add(monochromaticButton).colspan(2);
        table.row();
        table.add(p1ButtonLabel);
        table.add(selectP1Button);
        table.row();
        table.add(p2ButtonLabel);
        table.add(selectP2Button);
        table.row();
        table.add(applyButton);
        table.add(mainMenuButton);
        uiStage.addActor(table);
    }

    private void mainMenu() {
        setScreen(new MainMenu(game));
    }

    private void apply() {
        SettingsMenuController.changeDifficultyLevel(user, settings, difficultyLevelSelectBox.getSelected());
        SettingsMenuController.changeMap(user, settings, mapSelectBox.getSelected());
        SettingsMenuController.changeBallCount(user, settings, (int) ballCountSlider.getValue());
        SettingsMenuController.changeMuteMusic(user, settings, muteButton.isChecked());
        SettingsMenuController.changeMonochromatic(user, settings, monochromaticButton.isChecked());
        SettingsMenuController.changeP1FunctionKey(user, settings, p1FunctionKey);
        SettingsMenuController.changeP2FunctionKey(user, settings, p2FunctionKey);
        setScreen(new SettingsMenu(game));
    }

    private class KeyReader extends InputAdapter {
        private final int pid;

        private KeyReader(int pid) {
            this.pid = pid;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (pid) {
                case 0 -> p1FunctionKey = keycode;
                case 1 -> p2FunctionKey = keycode;
            }
            selectP1Button.setText(Input.Keys.toString(p1FunctionKey));
            selectP2Button.setText(Input.Keys.toString(p2FunctionKey));
            input.setInputProcessor(uiStage);
            return true;
        }
    }
}
