package com.briankenjiaguilar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backgroundImg;
	Texture [] flappyBird;
	int flapState;

	@Override
	public void create () {
		batch = new SpriteBatch();
		flapState = 0;
		backgroundImg = new Texture("bg.png");
		flappyBird = new Texture[2];
		flappyBird[0] = new Texture("bird.png");
		flappyBird[1] = new Texture("bird2.png");
	}

	@Override
	public void render () {

		// Renders a different image of the bird based off of the
		// flap state
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}

		// Starts rendering sprites
		batch.begin();
		// Draws the background
		batch.draw(backgroundImg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Slows down the render speed for the bird flapping
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Animates the bird flapping based off of the flap state
		batch.draw(flappyBird[flapState],Gdx.graphics.getWidth()/2 - flappyBird[flapState].getWidth()/2, Gdx.graphics.getHeight()/2 - flappyBird[flapState].getHeight()/2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
