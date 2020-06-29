package com.briankenjiaguilar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backgroundImg, topTube, bottomTube;
	Texture [] flappyBird;
	int flapState, gameState;
	float birdY, velocity, gravity, gap, tubeOffset, maxTubOffset, width, height, aspectRatio;
	Random randomGenerator;
	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		flapState = 0;
		gameState = 0;
		backgroundImg = new Texture("bg.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		flappyBird = new Texture[2];
		flappyBird[0] = new Texture("bird.png");
		flappyBird[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2 - flappyBird[0].getHeight()/2;
		velocity = 0;
		gravity = 2;
		gap = 400;
		randomGenerator = new Random();

		width = 1000;
		aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		height = width * aspectRatio;
		maxTubOffset = height - gap/2 - 100;

		camera = new OrthographicCamera(width,height);
		camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 2, 0);
		camera.update();
	}

	@Override
	public void render () {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Draws the background
		batch.draw(backgroundImg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0) {

			if (Gdx.input.justTouched()) {
				velocity = -30;
				tubeOffset = (randomGenerator.nextFloat() - 0.5f) * (height - gap - 200);
			}


			batch.draw(topTube, Gdx.graphics.getWidth()/2 - topTube.getWidth()/2, Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset);
			batch.draw(bottomTube, Gdx.graphics.getWidth()/2 - bottomTube.getWidth()/2, Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffset);


			// Development purposes
			if (birdY > 420 || velocity < 0) {
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
