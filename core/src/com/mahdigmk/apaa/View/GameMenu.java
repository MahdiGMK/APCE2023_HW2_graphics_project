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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mahdigmk.apaa.AAGame;
import com.mahdigmk.apaa.Controller.ScoreMenuController;
import com.mahdigmk.apaa.Model.Game.BallData;
import com.mahdigmk.apaa.Model.Game.GameData;
import com.mahdigmk.apaa.View.Game.FloatingBall;
import com.mahdigmk.apaa.View.Game.GameMusic;
import com.mahdigmk.apaa.View.Game.Timer;

import static com.badlogic.gdx.Gdx.*;

import java.util.Random;

public class GameMenu extends Menu {
    public static float outlineIncrement;
    public static GameMenu singleton;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final Batch batch;
    private final GameData gameData;
    private final Array<Vector2> details;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Random random;
    private final Timer reverseDirTimer = new Timer(4);
    private final Timer shrinkBallTimer = new Timer(.5f);
    private final Timer visInvisTimer = new Timer(1f);
    private final Timer randomShootDirTimer = new Timer(4);
    private final Table stageTable;
    private float endGameTime;
    private float planetRotationSpeed;
    private Array<FloatingBall> floatingBalls;
    private double slowModeTime;
    private Slider slowModeSlider;
    private int ballCount = 0;
    private boolean shrinkBall = false;
    private boolean visInvis = false;
    private float shootDir;
    private float playerPosition;
    private boolean isPaused;
    private boolean gameWon;
    private boolean gameLost;
    private Color bkgColor = Color.GRAY;
    private Label remainingBallLabel, scoreLabel, remainingTimeLabel;


    public GameMenu(AAGame game, GameData gameData) {
        super(game);
        singleton = this;
        stageTable = new Table();
        stageTable.setBounds(0, 0, graphics.getWidth(), graphics.getHeight());
        uiStage.addActor(stageTable);
        remainingBallLabel = new Label("a", game.getSkin());
        remainingBallLabel.setPosition(30, 5);
        scoreLabel = new Label("a", game.getSkin());
        scoreLabel.setPosition(30, 35);
        remainingTimeLabel = new Label("a", game.getSkin());
        remainingTimeLabel.setPosition(30, 65);
        uiStage.addActor(remainingBallLabel);
        uiStage.addActor(scoreLabel);
        uiStage.addActor(remainingTimeLabel);

        ballCount = gameData.getBalls().size() - gameData.getMap().getInitialBallCount();

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

    private static Color lerp(Color a, Color b, float t) {
        float r = MathUtils.lerp(a.r, b.r, t);
        float g = MathUtils.lerp(a.g, b.g, t);
        float bl = MathUtils.lerp(a.b, b.b, t);
        float al = MathUtils.lerp(a.a, b.a, t);
        return new Color(r, g, bl, al);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    public Color transformColor(Color in) {
        if (game.getSettings().isMonochromatic()) {
            float gray = (in.r + in.g + in.b) / 3;
            return new Color(gray, gray, gray, in.a);
        }
        return in;
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(transformColor(bkgColor));
        remainingBallLabel.setColor(transformColor(lerp(Color.RED, Color.GREEN, 1.0f * ballCount / gameData.getBallCount())));
        remainingBallLabel.setText("Remaining Balls : " + (gameData.getBallCount() - ballCount));
        scoreLabel.setText("Current Winning Score : " + getScore(true));
        float remTime = 120 - gameData.getPlayTime();
        int mm = (int) remTime / 60, ss = ((int) remTime) % 60;
        remainingTimeLabel.setText("Remaining Time : %02d:%02d".formatted(mm, ss));
        if (remTime <= 0 && !gameWon && !gameLost)
            endGame(false);


        if (!gameWon && !gameLost)
            if (input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (isPaused) resume();
                else pause();
            }

        if (!isPaused)
            update(deltaTime);

        if (gameWon)
            gameWonUpdate(deltaTime);
        if (gameLost)
            gameLostUpdate(deltaTime);

        uiStage.act(deltaTime);

        draw();
    }

    private void gameWonUpdate(float deltaTime) {
        Color from = bkgColor;
        Color to = Color.GREEN;
        bkgColor = lerp(from, to, deltaTime);
        endGameTime += deltaTime;
    }

    private void gameLostUpdate(float deltaTime) {
        Color from = bkgColor;
        Color to = Color.RED;
        bkgColor = lerp(from, to, deltaTime);
        endGameTime += deltaTime;
    }

    private void update(float deltaTime) {
        if (ballCount - floatingBalls.size == gameData.getBallCount()) endGame(true);

        gameData.setPlayTime(gameData.getPlayTime() + deltaTime);
        int phaseIndex = ballCount * 4 / gameData.getBallCount();
        handlePhaseSystem(phaseIndex, deltaTime);

        boolean slowMode = input.isKeyPressed(Input.Keys.TAB) && slowModeTime > 0;
        float rotationSpeed = planetRotationSpeed;
        if (slowMode) {
            float effect = 0.5f;
            slowModeTime -= deltaTime;
            rotationSpeed *= effect;

            bkgColor = lerp(bkgColor, Color.CYAN, deltaTime * 5);
            reverseDirTimer.setSpeed(effect);
            visInvisTimer.setSpeed(effect);
            shrinkBallTimer.setSpeed(effect);
            randomShootDirTimer.setSpeed(effect);
        } else {
            bkgColor = lerp(bkgColor, Color.GRAY, deltaTime * 5);
            float effect = 1;
            reverseDirTimer.setSpeed(effect);
            visInvisTimer.setSpeed(effect);
            shrinkBallTimer.setSpeed(effect);
            randomShootDirTimer.setSpeed(effect);
        }

        gameData.setRotation(gameData.getRotation() + deltaTime * rotationSpeed);
        for (int i = 0; i < floatingBalls.size; i++) {
            FloatingBall ball = floatingBalls.get(i);
            ball.update(deltaTime);
            if (ball.getPosition().x < -graphics.getWidth() / 2 * camera.zoom ||
                    ball.getPosition().x > graphics.getWidth() / 2 * camera.zoom ||
                    ball.getPosition().y < -graphics.getHeight() / 2 * camera.zoom ||
                    ball.getPosition().y > graphics.getHeight() / 2 * camera.zoom) {
                floatingBalls.removeIndex(i--);
                endGame(false);
            }

            if (!ball.isAlive()) {
                floatingBalls.removeIndex(i--);
                BallData data = new BallData(
                        MathUtils.atan2(ball.getPosition().y, ball.getPosition().x) - gameData.getRotation()
                        , ball.getPlayerId(), ball.getpBallIdx());
                if (gameData.validate(data)) {
                    gameData.getBalls().add(data);
                    slowModeTime += 0.5f;
                } else
                    endGame(false);
            }
        }

        if (input.isKeyJustPressed(game.getSettings().getP1FunctionKey())) {
            shootBall(0, playerPosition, shootDir);
        }
        if (input.isKeyJustPressed(game.getSettings().getP2FunctionKey())) {
            if (gameData.isIs2Player())
                shootBall(1, playerPosition, shootDir);
            else
                shootBall(0, playerPosition, shootDir);
        }

        slowModeTime = MathUtils.clamp(slowModeTime, 0, gameData.getDifficultyLevel().getFrozenDuration());
        slowModeSlider.setValue((float) slowModeTime);
    }

    private void handlePhaseSystem(int phaseIndex, float deltaTime) {
        boolean reverseDir = phaseIndex > 0;
        boolean shrinkBall = phaseIndex > 0;
        boolean visInvis = phaseIndex > 1;
        boolean randomShootDir = phaseIndex > 2;
        boolean moveShooter = phaseIndex > 2;

        if (reverseDir) reverseDirSystem(deltaTime);
        if (shrinkBall) shrinkBallSystem(deltaTime);
        if (visInvis) visInvisSystem(deltaTime);
        if (randomShootDir) randomShootDirSystem(deltaTime);
        if (moveShooter) moveShooterSystem(deltaTime);
    }

    private void moveShooterSystem(float deltaTime) {
        if (input.isKeyPressed(Input.Keys.LEFT)) playerPosition -= deltaTime;
        if (input.isKeyPressed(Input.Keys.RIGHT)) playerPosition += deltaTime;
        playerPosition = MathUtils.clamp(playerPosition, -1, 1);
    }

    private void randomShootDirSystem(float deltaTime) {
        randomShootDirTimer.update(deltaTime);
        if (randomShootDirTimer.act()) {
            randomShootDirTimer.init(4);
            shootDir = random.nextFloat(-0.5f, 0.5f);
        }
    }

    private void visInvisSystem(float deltaTime) {
        visInvisTimer.update(deltaTime);
        if (visInvisTimer.act()) {
            if (visInvis) visInvisTimer.init(3f);
            else visInvisTimer.init(0.5f);
            visInvis = !visInvis;
        }
    }

    private void reverseDirSystem(float deltaTime) {
        reverseDirTimer.update(deltaTime);
        if (reverseDirTimer.act()) {
            reverseDirTimer.init(random.nextFloat(1.5f, 6));
            planetRotationSpeed = -planetRotationSpeed;
        }
    }

    private void shrinkBallSystem(float deltaTime) {
        shrinkBallTimer.update(deltaTime);
        if (shrinkBallTimer.act()) {
            shrinkBallTimer.init(.5f);
            shrinkBall = !shrinkBall;
        }
    }

    private void shootBall(int playerId, float position, float direction) {
        if (ballCount >= gameData.getBallCount()) return;
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

    private void endGame(boolean win) {
        GameData.delete(game.getUser());

        floatingBalls.clear();

        if (win) gameWon = true;
        else gameLost = true;
        isPaused = true;
        Window window = new Window("", game.getSkin());
        Table table = new Table();
        if (win)
            table.add(new Label("You won!", game.getSkin())).colspan(2);
        else
            table.add(new Label("You lost!", game.getSkin())).colspan(2);
        table.row();
        table.add(new Label("Score : ", game.getSkin()));
        int score = getScore(win);
        table.add(new Label("" + score, game.getSkin()));
        table.row();
        table.add(new Label("Time spent : ", game.getSkin()));
        int mm = (int) gameData.getPlayTime() / 60, ss = ((int) gameData.getPlayTime()) % 60;
        table.add(new Label(String.format("%02d:%02d", mm, ss), game.getSkin()));
        table.row();
        Button backButton = new TextButton("Back to main menu", game.getSkin());
        Button restartButton = new TextButton("Restart", game.getSkin());
        Button scoreButton = new TextButton("Score menu", game.getSkin());
        table.add(restartButton).colspan(2);
        table.row();
        table.add(backButton).colspan(2);
        table.row();
        table.add(scoreButton).colspan(2);
        window.add(table);

        ScoreMenuController.changeScore(game.getUser(), game.getUser().getScore() + score);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu();
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
            }
        });
        scoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new ScoreMenu(game));
            }
        });

        stageTable.add(window);
    }

    private int getScore(boolean win) {
        int score = (gameData.getBalls().size() - gameData.getMap().getInitialBallCount());
        score *= 5;
        if (win) score *= 2;
        switch (gameData.getDifficultyLevel()) {
            case MEDIUM -> score *= 1.5f;
            case HARD -> score *= 2f;
        }
        switch (gameData.getMap()) {
            case DRY_LANDS -> score *= 1.2f;
            case FIRE_LANDS -> score *= 1.4f;
        }
        return score;
    }

    private void restart() {
        GameMenu menu = new GameMenu(game, new GameData(
                gameData.getDifficultyLevel()
                , gameData.getMap(), gameData.getBallCount(),
                gameData.getPlanetRadius(), gameData.getBallRadius(), gameData.isIs2Player()));

        setScreen(menu);
    }

    private void mainMenu() {
        setScreen(new MainMenu(game));
    }

    @Override
    public void pause() {
        isPaused = true;
        Window window = new Window("", game.getSkin());
        Table table = new Table();
        TextButton playButton;
        if (game.getGameMusic().isPlaying())
            playButton = new TextButton("Pause", game.getSkin());
        else
            playButton = new TextButton("Play", game.getSkin());
        SelectBox<GameMusic> musicSelectBox = new SelectBox<GameMusic>(game.getSkin());
        musicSelectBox.setItems(GameMusic.DARWINIA_01, GameMusic.DARWINIA_02, GameMusic.DARWINIA_03);
        musicSelectBox.setSelected(game.getSelectedMusic());
        TextButton restartButton = new TextButton("Restart", game.getSkin());
        TextButton saveButton = new TextButton("Save", game.getSkin());
        TextButton backButton = new TextButton("Back to main menu", game.getSkin());

        table.add(new Label("Pause menu", game.getSkin())).colspan(2).spaceBottom(20);
        table.row();
        table.add(new Label("move left : LEFT Key", game.getSkin())).colspan(2);
        table.row();
        table.add(new Label("move right : RIGHT Key", game.getSkin())).colspan(2);
        table.row();
        table.add(new Label("frozen mode : TAB key", game.getSkin())).colspan(2);
        table.row();
        table.add(new Label("main shoot : " + Input.Keys.toString(game.getSettings().getP1FunctionKey()) + " key", game.getSkin())).colspan(2);
        table.row();
        table.add(new Label("alt shoot : " + Input.Keys.toString(game.getSettings().getP2FunctionKey()) + " key", game.getSkin())).colspan(2);
        table.row();
        table.add(playButton);
        table.add(musicSelectBox);
        table.row();
        table.add(saveButton).width(100);
        table.add(restartButton).width(100);
        table.row();
        table.add(backButton).colspan(2);
        window.add(table);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                save();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenu();
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
            }
        });
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playMusic(playButton);
            }
        });
        musicSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                changeMusic(musicSelectBox);
            }
        });

        stageTable.add(window);
    }

    private void changeMusic(SelectBox<GameMusic> musicSelectBox) {
        game.setSelectedMusic(musicSelectBox.getSelected(), true);
    }

    private void playMusic(TextButton button) {
        if (game.getGameMusic().isPlaying())
            button.setText("Play");
        else
            button.setText("Pause");
        game.pauseMusic(game.getGameMusic().isPlaying());
    }

    private void save() {
        gameData.save(game.getUser());
    }

    @Override
    public void resume() {
        isPaused = false;
        stageTable.clear();
    }

    private void draw() {
        outlineIncrement = gameData.getPlanetRadius() * 0.015f;

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() + outlineIncrement);
        shapeRenderer.setColor(getOuterColor());
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius());

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() * 0.8f + outlineIncrement);
        shapeRenderer.setColor(getInnerColor());
        shapeRenderer.circle(0, 0, gameData.getPlanetRadius() * 0.8f);

        for (Vector2 detail : details) {
            Vector2 pos = getRotator(detail.x + (float) gameData.getRotation());
            pos.scl(detail.y);

            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.circle(pos.x, pos.y, gameData.getPlanetRadius() * 0.03f + outlineIncrement);
            shapeRenderer.setColor(getDetailColor());
            shapeRenderer.circle(pos.x, pos.y, gameData.getPlanetRadius() * 0.03f);
        }
        if (!visInvis) renderOnPlanetBalls();

        renderPlayer(0, playerPosition, shootDir);
        if (gameData.isIs2Player())
            renderPlayer(1, playerPosition, shootDir);


        for (FloatingBall ball : floatingBalls) {
            ball.draw(shapeRenderer);
        }

        shapeRenderer.end();

        drawUI();
    }

    private Color getDetailColor() {
        return transformColor(gameData.getMap().getDetailColor());
    }

    private Color getInnerColor() {
        return transformColor(gameData.getMap().getInnerColor());
    }

    private Color getOuterColor() {
        return transformColor(gameData.getMap().getOuterColor());
    }

    private void renderPlayer(int playerId, float position, float direction) {
        if (gameWon || gameLost) return;


        position *= gameData.getPlanetRadius();
        float startY = -gameData.getPlanetRadius() * 2.5f;
        Vector2 shootVel = getRotator(MathUtils.HALF_PI + direction)
                .scl(gameData.getPlanetRadius() * 0.25f);
        if (playerId == 1) {
            startY = -startY;
            shootVel.y = -shootVel.y;
        }

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(position, startY, getBallRadius() + outlineIncrement);
        shapeRenderer.rectLine(position, startY, position + shootVel.x, startY + shootVel.y, gameData.getBallRadius() * 0.65f);
        shapeRenderer.setColor(getDetailColor());
        shapeRenderer.circle(position, startY, getBallRadius());
    }

    private void renderOnPlanetBalls() {
        float dst = MathUtils.lerp(1.2f, 4, endGameTime);
        for (BallData ball : gameData.getBalls()) {
            Vector2 pos = getRotator((float) (ball.location() + gameData.getRotation()));
            pos.scl(gameData.getPlanetRadius());


            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rectLine(pos, new Vector2(pos).scl(dst), getBallRadius() * 0.25f + outlineIncrement * 2);
            shapeRenderer.setColor(getDetailColor());
            shapeRenderer.rectLine(pos, new Vector2(pos).scl(dst), getBallRadius() * 0.25f);

            pos.scl(dst);
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.circle(pos.x, pos.y, getBallRadius() + outlineIncrement);
            shapeRenderer.setColor(FloatingBall.getColor(ball.playerId()));
            shapeRenderer.circle(pos.x, pos.y, getBallRadius());

            if (ball.pBallIdx() >= 0) {
            }
        }
        shapeRenderer.end();
        batch.begin();
        for (BallData ball : gameData.getBalls()) {
            if (ball.pBallIdx() == -1) continue;

            Vector2 pos = getRotator((float) (ball.location() + gameData.getRotation()));
            pos.scl(gameData.getPlanetRadius());
            pos.scl(dst);

            font.setColor(Color.BLACK);
            Affine2 translation = new Affine2().translate(pos.x, pos.y).scale(new Vector2(2, 2));
            batch.setTransformMatrix(new Matrix4().setAsAffine(translation));// SOME MATRIX BS
            font.draw(batch, "" + ball.pBallIdx(), -20, 5, 2 * getBallRadius(), Align.center, false);
        }
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    public float getBallRadius() {
        float res = gameData.getBallRadius();
        if (shrinkBall) res *= 0.5f;
        return res;
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
