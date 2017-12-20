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
 * Created by Aman on 5/6/2017.
 */

public class ChallOpsScreen extends ScreenAdapter {
    private final Game game;
    private Stage stage;
    private Camera camera;

    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private ShapeRenderer shapeRenderer;

    private Texture instruTexture;
    private Texture instruPressTexture;
    private Texture rewardTexture;
    private Texture rewardPressTexture;

    private float RADIUS = 1f;
    private final Circle bgCircle;

    public ChallOpsScreen(Game game) {
        this.game = game;
        bgCircle = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
    }

    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        instruTexture = new Texture(Gdx.files.internal("newInstructions.png"));
        instruPressTexture = new Texture(Gdx.files.internal("newInstructionsPress.png"));
        ImageButton instru = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(instruTexture)), new TextureRegionDrawable(new
                TextureRegion(instruPressTexture)));
        instru.setPosition(WORLD_WIDTH / 2, (3*WORLD_HEIGHT) / 5, Align.center);
        stage.addActor(instru);

        instru.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new FidgetInsOne(game));
                dispose();
            }
        });

        rewardTexture = new Texture(Gdx.files.internal("rewardL.png"));
        rewardPressTexture = new Texture(Gdx.files.internal("rewardS.png"));
        ImageButton reward = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(rewardTexture)), new TextureRegionDrawable(new
                TextureRegion(rewardPressTexture)));
        reward.setPosition(WORLD_WIDTH / 2, (2*WORLD_HEIGHT) / 5, Align.center);
        stage.addActor(reward);

        reward.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new RewardScreen(game));
                dispose();
            }
        });



    }

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        instruTexture.dispose();
        instruPressTexture.dispose();
        rewardTexture.dispose();
        rewardPressTexture.dispose();
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
