package com.mahdigmk.apaa.View;

import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.User;

public class SettingsMenu extends Menu {
    private final User user;

    public SettingsMenu(AAGame game, User user) {
        super(game);
        this.user = user;
    }
}
