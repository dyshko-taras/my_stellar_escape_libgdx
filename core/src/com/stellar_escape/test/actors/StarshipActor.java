package com.stellar_escape.test.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.stellar_escape.test.Main;

public class StarshipActor extends Actor {
    private Texture imgStarship;
    public Rectangle rect;
    private float scale = Gdx.graphics.getHeight() / 800;
    private float speed = 200 * scale;


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        rect.x = x;
        rect.y = y;
    }

    public StarshipActor(Texture texture, float width, float height) {
        super();
        imgStarship = texture;
        rect = new Rectangle();
        rect.width = width * scale;
        rect.height = height * scale;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(imgStarship, rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

//    public boolean isWithinScreenLimits() {
//        return rect.x > 0 && rect.x < Main.SCREEN_WIDTH - rect.width;
//    }
}
