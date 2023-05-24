package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UIBox extends Actor {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Color mColor;

    public UIBox(Color color) {
        mColor = color;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.end();

        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setColor(mColor.r, mColor.g, mColor.b, mColor.a * parentAlpha);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }
}
