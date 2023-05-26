package com.mahdigmk.apaa.View.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mahdigmk.apaa.Model.Game.GameData;
import com.mahdigmk.apaa.View.GameMenu;

public class FloatingBall {
    private final GameData gameData;
    private int pBallIdx;
    private int playerId;
    private Vector2 position;
    private Vector2 velocity;
    private boolean alive = true;

    public FloatingBall(GameData gameData, Vector2 position, Vector2 velocity, int playerId, int pBallIdx) {
        this.gameData = gameData;
        this.position = position;
        this.velocity = velocity;
        this.playerId = playerId;
        this.pBallIdx = pBallIdx;
    }

    public GameData getGameData() {
        return gameData;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void update(float deltaTime) {
        position.mulAdd(velocity, deltaTime);
        velocity.add(-deltaTime * (float) gameData.getDifficultyLevel().getWindSpeed() * gameData.getPlanetRadius(), 0);

        double dst2 = 1.2f * gameData.getPlanetRadius();
        dst2 *= dst2;
        if (position.dot(position) <= dst2)
            alive = false;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(position.x, position.y, gameData.getBallRadius() + GameMenu.outlineIncrement);
        shapeRenderer.setColor(gameData.getMap().getDetailColor());
        shapeRenderer.circle(position.x, position.y, gameData.getBallRadius());
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getpBallIdx() {
        return pBallIdx;
    }

    public void setpBallIdx(int pBallIdx) {
        this.pBallIdx = pBallIdx;
    }
}
