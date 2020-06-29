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
	int flapState, gameState;
	float birdY, velocity, gravity;

	@Override
	public void create () {
		batch = new SpriteBatch();
		flapState = 0;
		gameState = 0;
		backgroundImg = new Texture("bg.png");
		flappyBird = new Texture[2];
		flappyBird[0] = new Texture("bird.png");
		flappyBird[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2 - flappyBird[0].getHeight()/2;
		velocity = 0;
		gravity = 2;
	}

	@Override
	public void render () {


		if (gameState != 0) {

			if (Gdx.input.justTouched()) {
				velocity = -30;
			}


			// Development purposes
			if (birdY > 0 || velocity < 0) {
				// Makes the bird fall faster when it is not tapped
				velocity += gravity;
				birdY -= velocity;
			}
		} else {

			if (Gdx.input.justTouched()) {
				gameState = 1;
			}

		}

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

		// Animates the bird flapping based off of the flap state
		batch.draw(
				flappyBird[flapState],
				Gdx.graphics.getWidth()/2 - flappyBird[flapState].getWidth()/2,
				birdY
		);

		batch.end();
	}

	public void animateGame() {}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
