package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mygdx.Blitz.StartScreen.customChar;
import static com.mygdx.Blitz.StartScreen.customObs;

public class DeadScreen extends ScreenAdapter {
	private static final float WORLD_WIDTH = 1920;
	private static final float WORLD_HEIGHT = 1080;
	private Stage stage;
	private float RADIUS = (WORLD_HEIGHT/2)-20f;//1f;
	private final Circle bgCircle;
	private ShapeRenderer shapeRenderer;
	private Camera camera;
	private float circleY = WORLD_HEIGHT/2;

	private ImageButton restart;
	private ImageButton rateButton;
	private Image score;
	private Image hscore;
	private Image points;

	private boolean adShowed = false;

	private SpriteBatch batch;

	private final Game game;

	private Texture scoreTexture;
	private Texture restartTexture; //150
	private Texture restartPressTexture; //100
	private Texture highScoreTexture; //150
	private Texture pointsTexture;
	private Texture rateTexture;
	private Texture ratePressTexture;
	private Preferences tempPrefs;
	private boolean obsStop = false;

	private BitmapFont bitmapFont;
	private GlyphLayout glyphLayout;
	private int rCount =0;

	private float circRad = WORLD_WIDTH/20;
	private Vector3 touch = new Vector3();

	Preferences prefs;// We store the value 10 with the key of "highScore"
	Preferences prefsTotal;

	public DeadScreen(Game game) {
		this.game = game;
		bgCircle = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);

		prefs = Gdx.app.getPreferences("highScore");
		if (!prefs.contains("highScore"))
			prefs.putInteger("highScore", 0);
		if (!prefs.contains("rate"))
			prefs.putBoolean("rate",false);

		prefsTotal=Gdx.app.getPreferences("scoreCount");
		if (!prefsTotal.contains("scoreCount"))
			prefsTotal.putInteger("scoreCount", 0);

		tempPrefs = Gdx.app.getPreferences("tColo");//represents colo2
		if (!tempPrefs.contains("tColo")) //shop upgrade one
			tempPrefs.putBoolean("tColo", false);

		if (!prefs.contains("adCount"))
			prefs.putInteger("adCount",1);
	}
	 
	public void show() {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		camera.update();
		stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);

		//SUBMITS SCORE TO THE HIGHSCORE BOARD
		if (MyBlitzGame.getPS().isSignedIn())
			MyBlitzGame.getPS().submitScore(GameScreen.getFinalScore()-1);


		if (MyBlitzGame.getPS().isSignedIn())
			MyBlitzGame.getPS().unlockAchievementTwo();

		//ACHIEVEMENT 3 TEST
		if ((MyBlitzGame.getPS().isSignedIn()) && (GameScreen.getFinalScore()-1 >= 25) )
			MyBlitzGame.getPS().unlockAchievementThree();

		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		glyphLayout = new GlyphLayout();
		
		shapeRenderer = new ShapeRenderer();

		restartTexture = new Texture(Gdx.files.internal("newRestart.png"));
		restartPressTexture = new Texture(Gdx.files.internal("newRestartPress.png"));
		restart = new ImageButton(new TextureRegionDrawable(new
				TextureRegion(restartTexture)), new TextureRegionDrawable(new
				TextureRegion(restartPressTexture)));
		restart.setPosition(WORLD_WIDTH / 2, (3*WORLD_HEIGHT) /8, Align.center);//WORLD_HEIGHT - (WORLD_HEIGHT / 1.7f), Align.center);
		stage.addActor(restart);

		rateTexture = new Texture(Gdx.files.internal("rateL.png"));
		ratePressTexture = new Texture(Gdx.files.internal("rateS.png"));
		rateButton = new ImageButton(new TextureRegionDrawable(new
				TextureRegion(rateTexture)), new TextureRegionDrawable(new
				TextureRegion(ratePressTexture)));
		rateButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT /2, Align.center);
		if ((prefs.getBoolean("rate") == false) && (prefs.getInteger("adCount") % 4 == 0) && (MyBlitzGame.getAds().isWifiConnected()))
			stage.addActor(rateButton);

		scoreTexture = new Texture(Gdx.files.internal("DScore.png"));
		score = new Image(scoreTexture);
		score.setPosition(WORLD_WIDTH / 2, 7 * WORLD_HEIGHT / 8,
				Align.center);
		stage.addActor(score);

		highScoreTexture = new Texture(Gdx.files.internal("DHighScore.png"));
		hscore = new Image(highScoreTexture);
		hscore.setPosition(WORLD_WIDTH / 2,  (3*WORLD_HEIGHT) / 4,
				Align.center);
		stage.addActor(hscore);

		pointsTexture = new Texture(Gdx.files.internal("DPoints.png"));
		points = new Image(pointsTexture);
		points.setPosition(WORLD_WIDTH/2, (5*WORLD_HEIGHT)/8, Align.center);
		stage.addActor(points);

		restart.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				obsStop = true;
				//game.setScreen(new GameScreen(game));
				//dispose();
			}
		});

		rateButton.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				MyBlitzGame.getPS().rateGame();
				prefs.putBoolean("rate",true);
				prefs.flush();
				prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount") + 250);
				prefsTotal.flush();
				rateButton.remove();
			}
		});
	}

	public void dispose() {
		super.dispose();
		stage.dispose();
		shapeRenderer.dispose();
		bitmapFont.dispose();
		restartTexture.dispose();
		restartPressTexture.dispose();
		rateTexture.dispose();
		ratePressTexture.dispose();
		scoreTexture.dispose();
		highScoreTexture.dispose();
	}

	public void drawScore() {
		String scoreAsString = Integer.toString(GameScreen.getFinalScore()-1);//-1 because adds 1 every time
		glyphLayout.setText(bitmapFont, scoreAsString);
		bitmapFont.getData().setScale(4, 4);
		bitmapFont.draw(batch, scoreAsString, (stage.getViewport().getWorldWidth() / 2) + (scoreTexture.getWidth()/2) ,
				( (7 * stage.getViewport().getWorldHeight()) / 8) + (scoreTexture.getHeight()/8));
	}

	public void drawHighScore() {
		String highScoreAsString = Integer.toString(prefs.getInteger("highScore"));
		glyphLayout.setText(bitmapFont, highScoreAsString);
		bitmapFont.getData().setScale(4,4);
		bitmapFont.draw(batch, highScoreAsString, (stage.getViewport().getWorldWidth()
				/ 2) + (highScoreTexture.getWidth()/2), (3*stage.getViewport().getWorldHeight()/4) + (highScoreTexture.getHeight()/8));
	}

	public void drawTotalPoints() {
		String pointsAsString = Integer.toString(prefsTotal.getInteger("scoreCount"));
		glyphLayout.setText(bitmapFont, pointsAsString);
		bitmapFont.getData().setScale(4,4);
		bitmapFont.draw(batch, pointsAsString, ( stage.getViewport().getWorldWidth()
				/ 2) + (pointsTexture.getWidth()/2), (5*stage.getViewport().getWorldHeight()/8) + (pointsTexture.getHeight()/8) );

	}

	public void setHighScore() {
		if (GameScreen.getFinalScore() > prefs.getInteger("highScore")) {
			prefs.putInteger("highScore",GameScreen.getFinalScore()-1);
			prefs.flush();
		}
	}

	public void setTotalPoints() {
		prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount") + GameScreen.getFinalScore()-1);
		prefsTotal.flush();
	}


	public void render(float delta) {	
		clearScreen();

		/*if ( (!adShowed)&&(prefs.getInteger("adCount") % 4 ==0) &&(MyBlitzGame.getAds().isWifiConnected())) {
			MyBlitzGame.getAds().showInterstitialAd(new Runnable() {
				@Override
				public void run() {
					adShowed = true;
				}
			});
		}*/

		if ((Gdx.input.isTouched()) && (!obsStop)) {
			if (tempPrefs.getBoolean("coloT1")) {
				//if ((Gdx.input.getX() > (WORLD_WIDTH- (WORLD_WIDTH/20))) && (Gdx.input.getY() > (WORLD_HEIGHT - (WORLD_WIDTH/20))) ) {

				//y is in Q1
				camera.unproject(touch.set(Gdx.input.getX(), Gdx.input.getY(), 0));

				if ((touch.x > (stage.getViewport().getWorldWidth() - (stage.getViewport().getWorldWidth()/10))) &&
						(touch.y < (stage.getViewport().getWorldWidth()/10))) {
					tempPrefs.putBoolean("coloT1", false);
					tempPrefs.flush();
					game.setScreen(new GameScreen(game, GameScreen.getFinalScore())); // makes sure new game starts with right score
					dispose();
				}
			}

		}

		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
			//shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
			//setRadius();
			if (!obsStop)
				shapeRenderer.circle(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, (WORLD_HEIGHT/2) - 20);//RADIUS);
			else
				shapeRenderer.circle(WORLD_WIDTH/2,circleY,RADIUS);

			if ((tempPrefs.getBoolean("coloT1")) && (!obsStop)) {
					shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
					shapeRenderer.circle(WORLD_WIDTH-circRad, circRad, circRad);
					shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
					shapeRenderer.rect(WORLD_WIDTH-circRad- (WORLD_WIDTH/40), circRad-(WORLD_HEIGHT/100), WORLD_WIDTH/20, WORLD_HEIGHT/50);
					shapeRenderer.rect(WORLD_WIDTH-circRad- (WORLD_HEIGHT/100), circRad-(WORLD_WIDTH/40), WORLD_HEIGHT/50, WORLD_WIDTH/20);
			}
		shapeRenderer.end();

		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		batch.begin();
			if (!obsStop) {
				drawScore();
				setHighScore();
				drawHighScore();
				if (rCount < 1)
					setTotalPoints();
				drawTotalPoints();
			}
			rCount++; //to make sure the total points is incremented only once
		batch.end();

		if (obsStop) {
			setMain();
			score.remove();
			hscore.remove();
			points.remove();
			restart.remove();
			if ((prefs.getBoolean("rate") == false) && (prefs.getInteger("adCount") % 4 == 0))
				rateButton.remove();

			if ((RADIUS <= WORLD_WIDTH/30f) && (circleY >= WORLD_HEIGHT - RADIUS)) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
			game.setScreen(new StartScreen(game));
			dispose();
		}

		stage.act(delta);
		stage.draw();
	}

	public void setMain() {
		if (RADIUS > WORLD_WIDTH / 30f)
			RADIUS -= (Gdx.graphics.getHeight()/160f);;
		circleY += (Gdx.graphics.getHeight()/160f);;
		bgCircle.set(WORLD_WIDTH / 2, circleY, RADIUS);
	}

	public void setRadius() {
		if (RADIUS < WORLD_HEIGHT/2 - 20f)
			RADIUS += (Gdx.graphics.getHeight()/216f);
		bgCircle.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, RADIUS);
	}
		
	private void clearScreen() {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
			Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
