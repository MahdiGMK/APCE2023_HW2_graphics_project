package com.mahdigmk.apaa.View.Game;

import com.badlogic.gdx.Gdx;

public enum GameMusic {
    _1("music/Darwinia01.ogg"),
    _2("music/Darwinia02.ogg"),
    _3("music/Darwinia03.ogg");
    private final String path;

    GameMusic(String path) {
        this.path = path;
    }

    public com.badlogic.gdx.audio.Music newMusic() {
        return Gdx.audio.newMusic(Gdx.files.internal(path));
    }
}
