package com.mahdigmk.apaa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;

import javax.swing.plaf.FileChooserUI;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("AP-AA");
        AAGame.fileChooser = new DesktopFileChooser();
        AAGame.fileChooserConfiguration = new NativeFileChooserConfiguration();

        new Lwjgl3Application(new AAGame(), config);
    }
}
