package com.stellar_escape.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stellar_escape.test.screens.MainMenuScreen;

public class Main extends Game {

//	public static final float SCREEN_WIDTH = 480;
//	public static final float SCREEN_HEIGHT = 800;



	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;




	public SpriteBatch batch;
	public BitmapFont bitmapFont;
	
	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
	}
}
