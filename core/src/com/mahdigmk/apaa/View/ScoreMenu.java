package com.mahdigmk.apaa.View;

import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.User;

import static com.badlogic.gdx.Gdx.*;

public class ScoreMenu extends Menu {
    private final User user;

    public ScoreMenu(AAGame game, User user) {
        super(game);
        this.user = user;
    }
}
