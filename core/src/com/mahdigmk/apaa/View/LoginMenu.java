package com.mahdigmk.apaa.View;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mahdigmk.apaa.AAGame;


import static com.badlogic.gdx.Gdx.*;

public class LoginMenu extends Menu {
    Table table;
    TextField username, password;
    CheckBox rememberLogin;
    TextButton register, login;
    Skin skin;


    public LoginMenu(AAGame game) {
        super(game);

        skin = new Skin(files.internal("neon/skin/neon-ui.json"));
        table = new Table(skin);
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        username = new TextField("", skin, "login");
        password = new TextField("", skin, "password");
        rememberLogin = new CheckBox("remember login", skin);
        register = new TextButton("register", skin);
        login = new TextButton("login", skin);

        table.add(username);
        table.add(password);
        table.row();
        table.add(login).width(100);
        table.add(register).width(100);
        table.row();
        table.add(rememberLogin).colspan(2);

        uiStage.addActor(table);
    }


    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
