package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.badlogic.gdx.Gdx.*;

public class ScoreMenu extends Menu {
    private final User user;
    private final ArrayList<User> users;
    private Table table;
    private Window[] windows;

    public ScoreMenu(AAGame game) {
        super(game);
        user = game.getUser();
        users = User.getAllUsers();
        Collections.sort(users, Comparator.comparingInt(User::getScore));
        Collections.reverse(users);
        int rank = 0;
        for (; rank < users.size(); rank++)
            if (users.get(rank).getUsername().equals(user.getUsername())) break;
        users.removeIf(user1 -> user1.getUsername().equals("_guest_"));

        table = new Table();
        table.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.WHITE, game.getSkin().getDrawable("window-c"));

        windows = new Window[10];
        for (int i = 0; i < Math.min(10, users.size()); i++) {
            User useri = users.get(i);
            windows[i] = new Window("", windowStyle);
            table.add(windows[i]).width(graphics.getWidth());
            Table innerTable = new Table();
            innerTable.align(Align.left);
            innerTable.add(new Label(i + 1 + " | ", game.getSkin())).width(50);
            innerTable.add(new Label(useri.getUsername(), game.getSkin())).width(100);
            innerTable.add(new Label(" | score : " + useri.getScore(), game.getSkin())).width(100);
            windows[i].add(innerTable);
            table.row();
            table.add(new UIBox(Color.BLACK)).width(graphics.getWidth() * 0.9f).height(2);
            table.row();
        }
        if (windows[0] != null)
            windows[0].setColor(transformColor(new Color(0xFFB500FF)));
        if (windows[1] != null)
            windows[1].setColor(transformColor(new Color(0x979797FF)));
        if (windows[2] != null)
            windows[2].setColor(transformColor(new Color(0xBC4A00FF)));

        Window userWindow = new Window("", windowStyle);
        table.add(userWindow).width(graphics.getWidth());
        Table innerTable = new Table();
        innerTable.align(Align.left);
        innerTable.add(new Label(rank + 1 + " | ", game.getSkin())).width(50);
        innerTable.add(new Label(" you ", game.getSkin())).width(100);
        innerTable.add(new Label(" | score : " + user.getScore(), game.getSkin())).width(100);
        userWindow.add(innerTable);
        userWindow.setBounds(0, 0, graphics.getWidth(), 50);

        TextButton mainMenuButton = new TextButton("back to main menu", game.getSkin());
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu();
            }
        });

        mainMenuButton.setPosition(0, 50);
        uiStage.addActor(mainMenuButton);
        uiStage.addActor(userWindow);
        uiStage.addActor(table);
    }

    public Color transformColor(Color in) {
        if (game.getSettings().isMonochromatic()) {
            float gray = (in.r + in.g + in.b) / 3;
            return new Color(gray, gray, gray, in.a);
        }
        return in;
    }

    private void mainMenu() {
        setScreen(new MainMenu(game));
    }
}
