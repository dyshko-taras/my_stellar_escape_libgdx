package com.stellar_escape.test.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StarActor extends Actor {

    private float x = 0;
    private float y = 0;
    private float width;
    private float height;
    private Texture img;
    private float scale = Gdx.graphics.getHeight() / 800f;
    private float speed = 200 * scale;


    public StarActor(Texture img, float width, float height, float x, float y) {
        super();
        this.img = img;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(img, x, y, width, height);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        y -= speed * delta;
        if (y <= -height + 20) {
            y = height;
        }
    }


    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
