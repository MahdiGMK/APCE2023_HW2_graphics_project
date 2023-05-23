package com.mahdigmk.apaa.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class IconButton extends TextButton {
    private Sprite sprite;
    private float spriteScale;

    public IconButton(Texture texture, float spriteScale, Skin skin) {
        super("", skin);
        sprite = new Sprite(texture);
        this.spriteScale = spriteScale;
    }

    public IconButton(Texture texture, float spriteScale, Skin skin, String styleName) {
        super("", skin, styleName);
        sprite = new Sprite(texture);
        this.spriteScale = spriteScale;
    }

    public IconButton(Texture texture, float spriteScale, TextButtonStyle style) {
        super("", style);
        sprite = new Sprite(texture);
        this.spriteScale = spriteScale;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public void setTexture(Texture texture) {
        sprite.setTexture(texture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setBounds(getX() + getWidth() * spriteScale / 2, getY() + getHeight() * spriteScale / 2,
                getWidth() * spriteScale, getHeight() * spriteScale);
        sprite.draw(batch);
    }

}
