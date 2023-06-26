package com.stellar_escape.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stellar_escape.test.Main;
import com.stellar_escape.test.actors.AsteroidActor;
import com.stellar_escape.test.actors.StarshipActor;
import com.stellar_escape.test.actors.StarActor;

import java.util.Iterator;

public class GameScreen implements Screen {
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;

    final Main main;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;
    private float scale = Gdx.graphics.getHeight() / 800;


    private boolean isPaused = false;
    private boolean isGameOver = false;

    private ImageButton leftButton;
    private ImageButton rightButton;
    private ImageButton pauseButton;
    private ImageButton restartButton;

    private StarshipActor starship;
    private StarActor star1, star2;

    private Array<AsteroidActor> asteroids = new Array<>();
    private Group asteroidsGroup;
    private long lastDropTime = 0;

    private float Score = 0;
    private float fontScale = 1.5f * scale;
    private GlyphLayout fontBounds = new GlyphLayout();


    public GameScreen(Main main) {
        this.main = main;
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();


    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        starship = new StarshipActor(new Texture("starship.png"), 80, 80);
        starship.setPosition(SCREEN_WIDTH / 2 - starship.rect.width / 2, 100 * scale);

        star1 = new StarActor(new Texture("background.jpg"), SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0);
        star2 = new StarActor(new Texture("background.jpg"), SCREEN_WIDTH, SCREEN_HEIGHT, 0, SCREEN_HEIGHT);

        Texture buttonLeftTexture = new Texture("pointer_left.png");
        Texture buttonRightTexture = new Texture("pointer_right.png");
        Texture buttonPauseTexture = new Texture("pause-button.png");
        Texture buttonRestartTexture = new Texture("restart_button.png");

        Image leftButtonImage = new Image(buttonLeftTexture);
        Image rightButtonImage = new Image(buttonRightTexture);
        Image pauseButtonImage = new Image(buttonPauseTexture);
        Image restartButtonImage = new Image(buttonRestartTexture);

        leftButton = new ImageButton(leftButtonImage.getDrawable());
        rightButton = new ImageButton(rightButtonImage.getDrawable());
        pauseButton = new ImageButton(pauseButtonImage.getDrawable());
        restartButton = new ImageButton(restartButtonImage.getDrawable());

        leftButton.setSize(100 * scale, 100 * scale);
        rightButton.setSize(100 * scale, 100 * scale);
        pauseButton.setSize(50 * scale, 50 * scale);
        restartButton.setSize(50 * scale, 50 * scale);

        leftButton.setPosition(20, 20);
        rightButton.setPosition(SCREEN_WIDTH - 20 - rightButton.getWidth(), 20);
        pauseButton.setPosition(SCREEN_WIDTH - 20 - pauseButton.getWidth(), SCREEN_HEIGHT - 20 - pauseButton.getHeight());
        restartButton.setPosition(SCREEN_WIDTH/2 - restartButton.getWidth()/2, SCREEN_HEIGHT/7*3);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
            }
        });
        restartButton.setVisible(false);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
            }
        });



        main.bitmapFont.getData().setScale(fontScale);

        asteroidsGroup = new Group();

        stage.addActor(star1);
        stage.addActor(star2);
        stage.addActor(starship);
        stage.addActor(asteroidsGroup);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(pauseButton);
        stage.addActor(restartButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        viewport.apply();
        main.batch.setProjectionMatrix(viewport.getCamera().combined);

        stage.draw();

        main.batch.begin();
        renderText();
        main.batch.end();


        if (!isPaused) {
            stage.act(delta);
            update();
        }


    }

    private void update() {
        updateStarship();
        spawnAsteroid();
        moveAsteroids();
    }

    private void renderText() {
        main.bitmapFont.getData().setScale(fontScale);
        main.bitmapFont.draw(main.batch, "Score: " + (int) Score, 20, SCREEN_HEIGHT - 20);

        if (isPaused && !isGameOver) {
            fontBounds.setText(main.bitmapFont, "PAUSE");
            float textWidth = fontBounds.width;
            float textX = SCREEN_WIDTH / 2 - textWidth / 2;
            main.bitmapFont.draw(main.batch, "PAUSE", textX, SCREEN_HEIGHT/2);
        }

        if (isGameOver && isPaused) {
            fontBounds.setText(main.bitmapFont, "GAME OVER");
            float textWidth = fontBounds.width;
            float textX = SCREEN_WIDTH / 2 - textWidth / 2;
            main.bitmapFont.draw(main.batch, "GAME OVER", textX, SCREEN_HEIGHT/2);
        }
    }


    private void updateStarship() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (leftButton.isPressed()) {
            starship.rect.x -= starship.getSpeed() * deltaTime;
            if (starship.rect.x < 0) {
                starship.rect.x = 0;
            }
        }

        if (rightButton.isPressed()) {
            starship.rect.x += starship.getSpeed() * deltaTime;
            if (starship.rect.x > SCREEN_WIDTH - starship.rect.width) {
                starship.rect.x = SCREEN_WIDTH - starship.rect.width;
            }
        }
    }

    private void spawnAsteroid() {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            AsteroidActor asteroid = new AsteroidActor(new Texture("asteroid.png"), 80, 80);
            asteroids.add(asteroid);
            asteroidsGroup.addActor(asteroid);
            lastDropTime = TimeUtils.nanoTime();
        }
    }

    private void moveAsteroids() {
        for (Iterator<AsteroidActor> iterator = asteroids.iterator(); iterator.hasNext(); ) {
            AsteroidActor asteroid = iterator.next();
            asteroid.rect.y -= asteroid.speed * Gdx.graphics.getDeltaTime();
            if (asteroid.rect.y + asteroid.rect.height < 0) {
                Score++;
                iterator.remove();
                asteroidsGroup.removeActor(asteroid);
            }
            if (asteroid.rect.overlaps(starship.rect)) {
                isGameOver = true;
                isPaused = true;
                restartButton.setVisible(true);
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


    }
}
