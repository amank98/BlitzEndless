package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class StartScreen extends ScreenAdapter {
	// TEXT SIZES: 120, 100 (COOLTEXT)
	private static final float WORLD_WIDTH = 1920;
	private static final float WORLD_HEIGHT = 1080;
	private Stage stage;
	private float RADIUS = 1f;
	private final Circle bgCircle;
	private final Circle bgCircleOne;
	private final Circle bgCircleTwo;
	private ShapeRenderer shapeRenderer;
	public static Color customChar;
	public static Color customObs;

	private Image title;
	private ImageButton instru;
	private ImageButton play;
	private ImageButton options;
	private ImageButton shop;
	private ImageButton achievements;
	private ImageButton highScore;
	private ImageButton chall;
	private ImageButton multi;

	private Texture challTexture;
	private Texture challPressTexture;
	private Texture playTexture;
	private Texture playPressTexture;
	private Texture titleTexture;
	private Texture instruTexture;
	private Texture instruPressTexture;
	private Texture optionsTexture;
	private Texture optionsPressTexture;
	private Texture shopTexture;
	private Texture shopPressTexture;
	private Texture achievementsTexture;
	private Texture achievementsPressTexture;
	private Texture highScoreTexture;
	private Texture highScorePressTexture;
	private Texture multiTexture;
	private Texture multiPressTexture;
	private Preferences prefs;
	private Preferences charPrefs;
	private Preferences shopPrefs;
	private Preferences instruPrefs;

	private int extraRadOne;
	private int extraRadTwo;
	private float circleY = WORLD_HEIGHT/2;

	//used to check if play was hit (in show) so render can continue
	private boolean playPause =false;

	private final Game game;

	private Camera camera;

	 public StartScreen(Game game) {
		 customObs = Color.GRAY;
		 customChar = Color.PURPLE;
		 this.game = game;
		 bgCircle = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
		 bgCircleOne = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,extraRadOne);
		 bgCircleTwo = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,extraRadTwo);

		 prefs = Gdx.app.getPreferences("color"); //obstacle colors
		 charPrefs = Gdx.app.getPreferences ("charColor"); //character colors
		 shopPrefs = Gdx.app.getPreferences("colo");
		 instruPrefs = Gdx.app.getPreferences("instru");

		 if (!prefs.contains("color")) //character color
			 prefs.putString("color", "PURPLE");
		 if (!charPrefs.contains("charColor")) // obstacle color
			 charPrefs.putString("charColor", "GRAY");
		 if (!shopPrefs.contains("colo")) //shop upgrade one
			 shopPrefs.putBoolean("colo", false);
		 if (!instruPrefs.contains("instru")) //makes sure instructions are shown once when the game is first launched
		 	instruPrefs.putBoolean("instru",false);
	 }

	public void setColor() {
		if (prefs.getString("color").equals("GOLDENROD")) {
			customObs = Color.GOLDENROD;
			ObstacleColorScreen.setColCol(4);
			ObstacleColorScreen.setColRow(22);
		}
		else if (prefs.getString("color").equals("RED")) {
			customObs = Color.RED;
			ObstacleColorScreen.setColCol(4);
			ObstacleColorScreen.setColRow(13);
		}
		else if (prefs.getString("color").equals("PURPLE")) {
			customObs = Color.PURPLE;
			ObstacleColorScreen.setColCol(4);
			ObstacleColorScreen.setColRow(4);
		}
		else if(prefs.getString("color").equals("CYAN")) {
			customObs = Color.CYAN;
			ObstacleColorScreen.setColCol(13);
			ObstacleColorScreen.setColRow(22);
		}
		else if(prefs.getString("color").equals("FIREBREAK")) {
			customObs = Color.FIREBRICK;
			ObstacleColorScreen.setColCol(13);
			ObstacleColorScreen.setColRow(13);
		}
		else if(prefs.getString("color").equals("ROYAL")) {
			customObs = Color.ROYAL;
			ObstacleColorScreen.setColCol(13);
			ObstacleColorScreen.setColRow(4);
		}
		else if(prefs.getString("color").equals("WHITE")) {
			customObs = Color.WHITE;
			ObstacleColorScreen.setColCol(22);
			ObstacleColorScreen.setColRow(22);
		}
		else if(prefs.getString("color").equals("LIME")) {
			customObs = Color.LIME;
			ObstacleColorScreen.setColCol(22);
			ObstacleColorScreen.setColRow(13);
		}
		else if (prefs.getString("color").equals("GRAY")) {
			customObs = Color.GRAY;
			ObstacleColorScreen.setColCol(22);
			ObstacleColorScreen.setColRow(4);
		}

		if (charPrefs.getString("charColor").equals("GOLDENROD")) {
			customChar = Color.GOLDENROD;
			CharacterColorScreen.setColCol(1);
			CharacterColorScreen.setColRow(5);
		}
		else if (charPrefs.getString("charColor").equals("RED")) {
			customChar = Color.RED;
			CharacterColorScreen.setColCol(1);
			CharacterColorScreen.setColRow(3);
		}
		else if (charPrefs.getString("charColor").equals("PURPLE")) {
			customChar = Color.PURPLE;
			CharacterColorScreen.setColCol(1);
			CharacterColorScreen.setColRow(1);
		}
		else if(charPrefs.getString("charColor").equals("CYAN")) {
			customChar = Color.CYAN;
			CharacterColorScreen.setColCol(3);
			CharacterColorScreen.setColRow(5);
		}
		else if(charPrefs.getString("charColor").equals("FIREBRICK")) {
			customChar = Color.FIREBRICK;
			CharacterColorScreen.setColCol(3);
			CharacterColorScreen.setColRow(3);
		}
		else if(charPrefs.getString("charColor").equals("ROYAL")) {
			customChar = Color.ROYAL;
			CharacterColorScreen.setColCol(3);
			CharacterColorScreen.setColRow(1);
		}
		else if(charPrefs.getString("charColor").equals("WHITE")) {
			customChar = Color.WHITE;
			CharacterColorScreen.setColCol(5);
			CharacterColorScreen.setColRow(5);
		}
		else if(charPrefs.getString("charColor").equals("LIME")) {
			customChar = Color.LIME;
			CharacterColorScreen.setColCol(5);
			CharacterColorScreen.setColRow(3);
		}
		else if (charPrefs.getString("charColor").equals("GRAY")) {
			customChar = Color.GRAY;
			CharacterColorScreen.setColCol(5);
			CharacterColorScreen.setColRow(1);
		}

		//used to determine whether or not the first upgrade will be used in the game screen
		if (shopPrefs.getBoolean("colo"))
			com.mygdx.Blitz.Obstacle.setDistanceBetweenLeftAndRight(WORLD_WIDTH / 20);
		else
			com.mygdx.Blitz.Obstacle.resetDistanceBetweenLeftAndRight();

		shopPrefs.flush();
	}

	 public void show() {
		 camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		 camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		 camera.update();
		 stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
		 Gdx.input.setInputProcessor(stage);
		 shapeRenderer = new ShapeRenderer();

		//Instructions
		instruTexture = new Texture(Gdx.files.internal("newInstructions.png"));
		instruPressTexture = new Texture(Gdx.files.internal("newInstructionsPress.png"));
		instru = new ImageButton(new TextureRegionDrawable(new
			 TextureRegion(instruTexture)), new TextureRegionDrawable(new
			 TextureRegion(instruPressTexture)));
		instru.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 2.3f), Align.center);
		stage.addActor(instru);

		//Options
		optionsTexture = new Texture(Gdx.files.internal("ColorsL.png"));
		optionsPressTexture = new Texture(Gdx.files.internal("ColorsS.png"));
		options = new ImageButton(new TextureRegionDrawable(new
							TextureRegion(optionsTexture)), new TextureRegionDrawable(new
							TextureRegion(optionsPressTexture)));
		options.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 1.65f), Align.center);
		stage.addActor(options);

		//shop (upgrades)
		shopTexture = new Texture(Gdx.files.internal("UpgradeL.png"));
		shopPressTexture = new Texture(Gdx.files.internal("UpgradeS.png"));
		shop = new ImageButton(new TextureRegionDrawable(new
							TextureRegion(shopTexture)), new TextureRegionDrawable(new
							TextureRegion(shopPressTexture)));
		shop.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 1.45f), Align.center);
		stage.addActor(shop);

		 //CHALLENGE
		 challTexture = new Texture(Gdx.files.internal("challengeL.png"));
		 challPressTexture = new Texture(Gdx.files.internal("challengeS.png"));
		 chall = new ImageButton(new TextureRegionDrawable(new
				 TextureRegion(challTexture)), new TextureRegionDrawable(new
				 TextureRegion(challPressTexture)));
		 chall.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 1.3f), Align.center);
		 stage.addActor(chall);

		 multiTexture = new Texture(Gdx.files.internal("multiL.png"));
		 multiPressTexture = new Texture(Gdx.files.internal("multiS.png"));
		 multi = new ImageButton(new TextureRegionDrawable(new
				 TextureRegion(multiTexture)), new TextureRegionDrawable(new
				 TextureRegion(multiPressTexture)));
		 multi.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 1.15f), Align.center);
		 stage.addActor(multi);

		playTexture = new Texture(Gdx.files.internal("newPlay.png"));
		playPressTexture = new Texture(Gdx.files.internal("newPlayPress.png"));
		play = new ImageButton(new TextureRegionDrawable(new
				 TextureRegion(playTexture)), new TextureRegionDrawable(new
				 TextureRegion(playPressTexture)));
		//play.setBounds(0, 0, WORLD_WIDTH / 6, WORLD_HEIGHT / 6);
		//play.getImageCell().expand().fill();
		play.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT - (WORLD_HEIGHT / 1.925f), Align.center);
		stage.addActor(play);

		 achievementsTexture = new Texture(Gdx.files.internal("A.png"));
		 achievementsPressTexture = new Texture(Gdx.files.internal("As.png"));
		 achievements = new ImageButton(new TextureRegionDrawable(new
				 TextureRegion(achievementsTexture)), new TextureRegionDrawable(new
				 TextureRegion(achievementsPressTexture)));
		 achievements.setPosition(WORLD_WIDTH / 8, WORLD_HEIGHT - (WORLD_HEIGHT / 1.2f), Align.center);
		 stage.addActor(achievements);

		 highScoreTexture = new Texture(Gdx.files.internal("H.png"));
		 highScorePressTexture = new Texture(Gdx.files.internal("Hs.png"));
		 highScore = new ImageButton(new TextureRegionDrawable(new
				 TextureRegion(highScoreTexture)), new TextureRegionDrawable(new
				 TextureRegion(highScorePressTexture)));
		 highScore.setPosition((7*WORLD_WIDTH )/ 8, WORLD_HEIGHT - (WORLD_HEIGHT / 1.2f), Align.center);
		 stage.addActor(highScore);

		 //play
		 play.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				playPause= true;
			}
		});

		//Instructions
		instru.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				game.setScreen(new com.mygdx.Blitz.InstructionScreen(game));
				dispose();
			}
		});

		options.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				game.setScreen(new OptionsScreen(game));
				dispose();
			}
		});

		shop.addListener(new ActorGestureListener() {
			 @Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
			super.tap(event, x, y, count, button);
			game.setScreen(new com.mygdx.Blitz.ShoptionScreen(game));
			dispose();
			}
		});

		 chall.addListener(new ActorGestureListener() {
			 @Override
			 public void tap(InputEvent event, float x, float y, int count, int button) {
				 super.tap(event, x, y, count, button);
				 game.setScreen(new com.mygdx.Blitz.ChallOpsScreen(game));
				 dispose();
			 }
		 });

		 multi.addListener(new ActorGestureListener() {
			 @Override
			 public void tap(InputEvent event, float x, float y, int count, int button) {
				 super.tap(event, x, y, count, button);
				 game.setScreen(new com.mygdx.Blitz.MultiGameScreen(game));
				 dispose();
			 }
		 });

		achievements.addListener(new ActorGestureListener() {
			 @Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				if (MyBlitzGame.getPS().isSignedIn())
					MyBlitzGame.getPS().showAchievement();
				if (!MyBlitzGame.getPS().isSignedIn())
					MyBlitzGame.getPS().signIn();
			}
		});

		highScore.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				if (MyBlitzGame.getPS().isSignedIn())
					MyBlitzGame.getPS().showScore();
				if (!MyBlitzGame.getPS().isSignedIn())
					MyBlitzGame.getPS().signIn();
			}
		});

		titleTexture = new Texture(Gdx.files.internal("newBl!tz.png"));
		title = new Image(titleTexture);
		title.setPosition(WORLD_WIDTH /2, 3 * WORLD_HEIGHT / 4,
				Align.center);
		stage.addActor(title);

		setColor();
	}

	@Override
	public void dispose() {
		super.dispose();
		shapeRenderer.dispose();
		stage.dispose();
	  	playTexture.dispose();
	  	playPressTexture.dispose();
	  	instruTexture.dispose();
	  	instruPressTexture.dispose();
	  	optionsTexture.dispose();
	  	optionsPressTexture.dispose();
	  	titleTexture.dispose();
	  	shopTexture.dispose();
	  	shopPressTexture.dispose();
		achievementsTexture.dispose();
		achievementsPressTexture.dispose();
		highScoreTexture.dispose();
		highScorePressTexture.dispose();
		challTexture.dispose();
		challPressTexture.dispose();
		multiTexture.dispose();
		multiPressTexture.dispose();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		clearScreen();

		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
			shapeRenderer.circle(WORLD_WIDTH/2,circleY,RADIUS);
			shapeRenderer.circle(WORLD_WIDTH/ 8, WORLD_HEIGHT - (WORLD_HEIGHT / 1.2f), extraRadOne);
			shapeRenderer.circle((7*WORLD_WIDTH )/ 8, WORLD_HEIGHT - (WORLD_HEIGHT / 1.2f), extraRadTwo);
		shapeRenderer.end();

		if (!playPause)
			setCircles();
		if (playPause) {
			if (instruPrefs.getBoolean("instru") == false) {
				instruPrefs.putBoolean("instru", true);
				instruPrefs.flush();
				game.setScreen(new InstructionScreen(game));
				dispose();
			}
			title.remove();
			instru.remove();
			play.remove();
			options.remove();
			shop.remove();
			achievements.remove();
			highScore.remove();
			chall.remove();
			multi.remove();

            setMain();
			if ((RADIUS <= WORLD_WIDTH/30f) && (circleY >= WORLD_HEIGHT - RADIUS)) {
				game.setScreen(new GameScreen(game));
				dispose();
			}

		}


		stage.act(delta);
		stage.draw();
	}

	public void setMain() {
		if (RADIUS > WORLD_WIDTH / 30f)
			RADIUS -= (Gdx.graphics.getHeight()/160f);;
		circleY += (Gdx.graphics.getHeight()/160f);;
		bgCircle.set(WORLD_WIDTH / 2, circleY, RADIUS);

		if (extraRadOne > 0)
			extraRadOne-=1f;
		bgCircleOne.set((stage.getViewport().getWorldWidth() )/ 8, stage.getViewport().getWorldHeight() - (stage.getViewport().getWorldHeight() / 1.2f), extraRadOne);

		if (extraRadTwo > 0)
			extraRadTwo-=1f;
		bgCircleTwo.set((7*stage.getViewport().getWorldWidth())/ 8, stage.getViewport().getWorldHeight() - (stage.getViewport().getWorldHeight() / 1.2f), extraRadTwo);
	}

	public void setCircles() {
		if (RADIUS < stage.getViewport().getWorldHeight()/2 - 20f)
			RADIUS += (stage.getViewport().getWorldHeight()/216f);
		bgCircle.set(stage.getViewport().getWorldWidth()/2,stage.getViewport().getWorldHeight()/2,RADIUS);

		if (extraRadOne < WORLD_HEIGHT/14)
			extraRadOne+=1f;
		bgCircleOne.set((stage.getViewport().getWorldWidth() )/ 8, stage.getViewport().getWorldHeight() - (stage.getViewport().getWorldHeight() / 1.2f), extraRadOne);

		if (extraRadTwo < WORLD_HEIGHT/14)
			extraRadTwo+=1f;
		bgCircleTwo.set((7*stage.getViewport().getWorldWidth())/ 8, stage.getViewport().getWorldHeight() - (stage.getViewport().getWorldHeight() / 1.2f), extraRadOne);
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
			Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
