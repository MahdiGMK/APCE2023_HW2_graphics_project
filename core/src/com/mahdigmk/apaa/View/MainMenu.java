package com.mahdigmk.apaa.View;

import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.User;

import static com.badlogic.gdx.Gdx.*;

public class MainMenu extends Menu {
    private final User user;

    public MainMenu(AAGame game, User user) {
        super(game);
        this.user = user;
        System.out.println("main menu user : " + user.getUsername());
    }
}
