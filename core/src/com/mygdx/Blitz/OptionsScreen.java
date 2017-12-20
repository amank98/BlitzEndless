package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.mygdx.Blitz.StartScreen.customChar;


/**
 * Created by Aman on 5/13/2016.
 */
public class OptionsScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private final Game game;
    private ShapeRenderer shapeRenderer;

    private Texture charTexture;
    private Texture charPressTexture;
    private Texture obsTexture;
    private Texture obsPressTexture;

    private float RADIUS = 1f;
    private final Circle bgCircle;

    public OptionsScreen(Game game) {
        this.game = game;
        bgCircle = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
    }

    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        shapeRenderer = new ShapeRenderer();

        charTexture = new Texture(Gdx.files.internal("mainBallL.png"));
        charPressTexture = new Texture(Gdx.files.internal("mainBallS.png"));
        ImageButton character = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(charTexture)), new TextureRegionDrawable(new
                TextureRegion(charPressTexture)));
        character.setPosition(WORLD_WIDTH / 2, (2 * WORLD_HEIGHT) /3, Align.center);
        stage.addActor(character);

        //play
        character.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CharacterColorScreen(game));
                dispose();
            }
        });

        obsTexture = new Texture(Gdx.files.internal("ObsL.png"));
        obsPressTexture = new Texture(Gdx.files.internal("ObsS.png"));
        ImageButton obstacle = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(obsTexture)), new TextureRegionDrawable(new
                TextureRegion(obsPressTexture)));
        obstacle.setPosition(WORLD_WIDTH / 2,  WORLD_HEIGHT /3, Align.center);
        stage.addActor(obstacle);

        //play
        obstacle.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new com.mygdx.Blitz.ObstacleColorScreen(game));
                dispose();
            }
        });
    }

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        charTexture.dispose();
        charPressTexture.dispose();
        obsTexture.dispose();
        obsPressTexture.dispose();
    }

    public void render(float delta) {
        clearScreen();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
            setRadius();
            shapeRenderer.circle(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, RADIUS);
        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(new com.mygdx.Blitz.StartScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
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
