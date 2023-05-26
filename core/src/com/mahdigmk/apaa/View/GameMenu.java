package com.mahdigmk.apaa.View;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Model.Game.BallData;
import com.mahdigmk.apaa.Model.Game.GameData;
import com.mahdigmk.apaa.View.Game.FloatingBall;
import com.mahdigmk.apaa.View.Game.Timer;

import static com.badlogic.gdx.Gdx.*;

import java.util.Random;

public class GameMenu extends Menu {
    public static float outlineIncrement;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final Batch batch;
    private final GameData gameData;
    private final Array<Vector2> details;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Random random;
    private final Timer reverseDirTimer = new Timer(4);
    private float planetRotationSpeed;
    private Array<FloatingBall> floatingBalls;
    private double slowModeTime;
    private Slider slowModeSlider;
    private int ballCount = 0;

    public GameMenu(AAGame game, GameData gameData) {
        super(game);

        this.gameData = gameData;
        camera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        details = new Array<>();
        viewport = new FitViewport(graphics.getWidth(), graphics.getHeight(), camera);
        camera.zoom = gameData.getPlanetRadius() / 150f;
        floatingBalls = new Array<>();

        random = new Random();
        int detailCount = random.nextInt(15) + 15;
        for (int i = 0; i < detailCount; i++) {
            details.add(new Vector2(random.nextFloat(16 * MathUtils.PI),
                    random.nextFloat(gameData.getPlanetRadius() * 0.6f, gameData.getPlanetRadius())));
        }

        slowModeSlider = new Slider(0, (float) gameData.getDifficultyLevel().getFrozenDuration(), .01f, true, game.getSkin());
        slowModeSlider.setDisabled(true);
        uiStage.addActor(slowModeSlider);
        slowModeTime = gameData.getDifficultyLevel().getFrozenDuration();
        batch = new SpriteBatch();
        planetRotationSpeed = (float) gameData.getDifficultyLevel().getRotationSpeed();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);

        update(deltaTime);

        uiStage.act(deltaTime);

        draw();
    }

    private void update(float deltaTime) {
        int phaseIndex = ballCount * 4 / gameData.getBallCount();
        handlePhaseSystem(phaseIndex, deltaTime);

        boolean slowMode = input.isKeyPressed(Input.Keys.TAB) && slowModeTime > 0;
        float rotationSpeed = planetRotationSpeed;
        if (slowMode) {
            slowModeTime -= deltaTime;
            rotationSpeed *= .5f;
        }

        gameData.setRotation(gameData.getRotation() + deltaTime * rotationSpeed);
        for (int i = 0; i < floatingBalls.size; i++) {
            FloatingBall ball = floatingBalls.get(i);
            ball.update(deltaTime);
            if (!ball.isAlive()) {
                floatingBalls.removeIndex(i--);
                BallData data = new BallData(
                        MathUtils.atan2(ball.getPosition().y, ball.getPosition().x) - gameData.getRotation()
                        , ball.getPlayerId(), ball.getpBallIdx());
                if (gameData.validate(data)) {
                    gameData.getBalls().add(data);
                    slowModeTime += 0.5f;
                } else
                    lose(ball.getPlayerId());
            }
        }

        if (input.isKeyJustPressed(game.getSettings().getP1FunctionKey())) {
            shootBall(0, 0, 0);
        }

        slowModeTime = MathUtils.clamp(slowModeTime, 0, gameData.getDifficultyLevel().getFrozenDuration());
        slowModeSlider.setValue((float) slowModeTime);
    }

    private void handlePhaseSystem(int phaseIndex, float deltaTime) {
        boolean reverseDir = phaseIndex > 0;
        boolean decSize = phaseIndex > 0;
        boolean visInvis = phaseIndex > 1;
        boolean randomShootDir = phaseIndex > 2;
        boolean moveShooter = phaseIndex > 2;

        if (reverseDir) reverseDirSystem(deltaTime);

    }

    private void reverseDirSystem(float deltaTime) {
        reverseDirTimer.update(deltaTime);
        if (reverseDirTimer.act()) {
            reverseDirTimer.init(random.nextFloat(1.5f, 6));
            planetRotationSpeed = -planetRotationSpeed;
        }
    }

    private void shootBall(int playerId, float position, float direction) {
        position *= gameData.getPlanetRadius();
        float startY = -gameData.getPlanetRadius() * 2.5f;
        Vector2 shootVel = getRotator(MathUtils.HALF_PI + direction)
                .scl(gameData.getPlanetRadius() * 1.75f);
        if (playerId == 1) {
            startY = -startY;
            shootVel.y = -shootVel.y;
        }

        floatingBalls.add(new FloatingBall(
                gameData,
                batch, font, new Vector2(position, startY),
                shootVel,
                playerId, game.getSettings().getBallCount() - ballCount++
        ));
    }

    private void lose(int playerId) {
        ///TODO
    }

    private void draw() {
        outlineIncrement = gameData.getPlanetRadius() * 0.015f;

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() + outlineIncrement);
        shapeRenderer.setColor(gameData.getMap().getOuterColor());
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius());

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() * 0.8f + outlineIncrement);
        shapeRenderer.setColor(gameData.getMap().getInnerColor());
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() * 0.8f);

        for (Vector2 detail : details) {
            Vector2 pos = getRotator(detail.x + (float) gameData.getRotation());
            pos.scl(detail.y);

            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.circle(pos.x, pos.y, gameData.getPlanetRadius() * 0.03f + outlineIncrement);
            shapeRenderer.setColor(gameData.getMap().getDetailColor());
            shapeRenderer.circle(pos.x, pos.y, gameData.getPlanetRadius() * 0.03f);
        }

        for (BallData ball : gameData.getBalls()) {
            Vector2 pos = getRotator((float) (ball.location() + gameData.getRotation()));
            pos.scl(gameData.getPlanetRadius());


            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rectLine(pos, new Vector2(pos).scl(1.2f), gameData.getBallRadius() * 0.25f + outlineIncrement * 2);
            shapeRenderer.setColor(gameData.getMap().getDetailColor());
            shapeRenderer.rectLine(pos, new Vector2(pos).scl(1.2f), gameData.getBallRadius() * 0.25f);

            pos.scl(1.2f);
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.circle(pos.x, pos.y, gameData.getBallRadius() + outlineIncrement);
            shapeRenderer.setColor(FloatingBall.getColor(ball.playerId()));
            shapeRenderer.circle(pos.x, pos.y, gameData.getBallRadius());

            if (ball.pBallIdx() >= 0) {
            }
        }
        shapeRenderer.end();
        batch.begin();
        for (BallData ball : gameData.getBalls()) {
            if (ball.pBallIdx() == -1) continue;

            Vector2 pos = getRotator((float) (ball.location() + gameData.getRotation()));
            pos.scl(gameData.getPlanetRadius());
            pos.scl(1.2f);

            font.setColor(Color.BLACK);
            Affine2 translation = new Affine2().translate(pos.x, pos.y).scale(new Vector2(2, 2));
            batch.setTransformMatrix(new Matrix4().setAsAffine(translation));// SOME MATRIX BS
            font.draw(batch, "" + ball.pBallIdx(), -20, 5, 2 * gameData.getBallRadius(), Align.center, false);
        }
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(0, -gameData.getPlanetRadius() * 2.5f, gameData.getBallRadius() + outlineIncrement);
        shapeRenderer.setColor(gameData.getMap().getDetailColor());
        shapeRenderer.circle(0, -gameData.getPlanetRadius() * 2.5f, gameData.getBallRadius());

        for (FloatingBall ball : floatingBalls) {
            ball.draw(shapeRenderer);
        }

        shapeRenderer.end();

        drawUI();
    }

    private void drawUI() {
        uiStage.draw();
    }

    private Vector2 getRotator(float angle) {
        return new Vector2(
                MathUtils.cos(angle),
                MathUtils.sin(angle)
        );
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        font.dispose();
        batch.dispose();
    }
}
