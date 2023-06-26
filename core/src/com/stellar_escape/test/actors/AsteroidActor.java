package com.stellar_escape.test.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.stellar_escape.test.Main;

public class AsteroidActor extends Actor {
    public Rectangle rect;
    public Circle circle;
    private Texture img;
    private float scale = Gdx.graphics.getHeight() / 800;
    public float speed = 200 * scale;
    private float elapsedTime = 0;

    public AsteroidActor(Texture texture, float width, float height) {
        super();
        img = texture;
        rect = new Rectangle();
        rect.width = width * scale;
        rect.height = height * scale;
        rect.x = MathUtils.random(0, Main.SCREEN_WIDTH - rect.width);
        rect.y = Main.SCREEN_HEIGHT;
        setPosition(rect.x, rect.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(img, rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        if (elapsedTime >= 0.5) {
            speed += 50 * scale;
            elapsedTime = 0;
        }
    }
}
