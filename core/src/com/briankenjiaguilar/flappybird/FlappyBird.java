package com.briankenjiaguilar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	// Sprite Variables
	SpriteBatch batch;
	Texture backgroundImg, topTube, bottomTube;
	Texture [] flappyBird;

	// State variables
	int flapState, gameState;

	// Camera Variables
	float width, height, aspectRatio;
	OrthographicCamera camera;

	// Bird Variables
	float birdY, velocity, gravity;

	// Tube Variables
	float gap, maxTubOffset, tubeVelocity, distanceBetweenTubes;
	float [] tubeX, tubeOffsets;
	int numberOfTubes;

	// Other variables
	Random randomGenerator;


	@Override
	public void create () {
		batch = new SpriteBatch();

		// Game state setup
		flapState = 0;
		gameState = 0;

		// Sprite setup
		backgroundImg = new Texture("bg.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		flappyBird = new Texture[2];
		flappyBird[0] = new Texture("bird.png");
		flappyBird[1] = new Texture("bird2.png");

		// Bird setup
		birdY = Gdx.graphics.getHeight()/2 - flappyBird[0].getHeight()/2;
		velocity = 0;
		gravity = 2;
		gap = 400;

		randomGenerator = new Random();

		// Tube setup
		numberOfTubes = 4;
		tubeX =  new float[numberOfTubes];
		tubeOffsets = new float[numberOfTubes];
		distanceBetweenTubes = Gdx.graphics.getWidth()/2;
		tubeVelocity = 4f;
		for (int i = 0; i < numberOfTubes; i++) {
			tubeOffsets[i] = (randomGenerator.nextFloat() - 0.5f) * (height - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + i * distanceBetweenTubes;
		}

		// Camera setup
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

		// Sets up the camera in the game screen based off of camera values
		batch.setProjectionMatrix(camera.combined);


		batch.begin();
		// Draws the background
		batch.draw(backgroundImg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// If the game state is not 0 then the user has started playing the game
		if (gameState != 0) {

			// Moves the bird up on every screen touch
			if (Gdx.input.justTouched()) {
				velocity = -30;
			}

			// Logic for the tubes rotating on the screen
			for (int i = 0; i < numberOfTubes; i++) {

				// Takes care of the situation when the tube goes off screen
				if (tubeX[i] < - topTube.getWidth()) {
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
				} else {
					tubeX[i]-= tubeVelocity;
					batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight()/2 + gap/2 + tubeOffsets[i]);
					batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffsets[i]);
				}

			}



			// Development purposes
			if (birdY > 420 || velocity < 0) {
				// Makes the bird fall faster when it is not tapped
				velocity += gravity;
				birdY -= velocity;
			}
		} else {
			// User tapped the screen for the first time therefore they are starting the game
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


	@Override
	public void dispose () {
		batch.dispose();
	}
}
