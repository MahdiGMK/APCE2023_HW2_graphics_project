package com.mahdigmk.apaa.View.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum GameMusic {
    DARWINIA_01("music/Darwinia01.ogg"),
    DARWINIA_02("music/Darwinia02.ogg"),
    DARWINIA_03("music/Darwinia03.ogg");
    private final String path;
    private Music music;

    GameMusic(String path) {
        this.path = path;
    }

    public com.badlogic.gdx.audio.Music getMusic() {
        if (music == null)
            return music = Gdx.audio.newMusic(Gdx.files.internal(path));
        return music;
    }
}
