package com.mahdigmk.apaa.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class ShadedImage extends Image {
    private ShaderProgram shader;

    public ShadedImage(ShaderProgram shader) {
        this.shader = shader;
    }

    public ShadedImage(NinePatch patch, ShaderProgram shader) {
        super(patch);
        this.shader = shader;
    }

    public ShadedImage(TextureRegion region, ShaderProgram shader) {
        super(region);
        this.shader = shader;
    }

    public ShadedImage(Texture texture, ShaderProgram shader) {
        super(texture);
        this.shader = shader;
    }

    public ShadedImage(Skin skin, String drawableName, ShaderProgram shader) {
        super(skin, drawableName);
        this.shader = shader;
    }

    public ShadedImage(Drawable drawable, ShaderProgram shader) {
        super(drawable);
        this.shader = shader;
    }

    public ShadedImage(Drawable drawable, Scaling scaling, ShaderProgram shader) {
        super(drawable, scaling);
        this.shader = shader;
    }

    public ShadedImage(Drawable drawable, Scaling scaling, int align, ShaderProgram shader) {
        super(drawable, scaling, align);
        this.shader = shader;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
}
