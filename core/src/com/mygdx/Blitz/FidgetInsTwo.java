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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Aman on 5/25/2017.
 */

public class FidgetInsTwo extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;

    private float RADIUS = 1f;
    private final Circle bgCircle;
    private ShapeRenderer shapeRenderer;
    private Texture nextTexture; //100
    private Texture nextPressTexture; //80
    private Texture backTexture; //100
    private Texture backPressTexture; //80
    private Texture dirTexture;
    private Texture diTexture;
    private Texture hTexture;

    private Game game;

    public FidgetInsTwo (Game game) {
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

        dirTexture = new Texture(Gdx.files.internal("FI2.png"));
        Image you = new Image(dirTexture);
        you.setPosition(WORLD_WIDTH /2, (3*WORLD_HEIGHT) / 4,
                Align.center);
        stage.addActor(you);

        diTexture = new Texture(Gdx.files.internal("FI21.png")); //80
        Image yo = new Image(diTexture);
        yo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT) / 4,
                Align.center);
        stage.addActor(yo);

        hTexture = new Texture(Gdx.files.internal("H.png"));
        Image h = new Image(hTexture);
        h.setPosition(WORLD_WIDTH/ 2, WORLD_HEIGHT / 2,
                Align.center);
        stage.addActor(h);

        nextTexture = new Texture(Gdx.files.internal("nextL.png"));
        nextPressTexture = new Texture(Gdx.files.internal("nextS.png"));
        ImageButton next = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(nextTexture)), new TextureRegionDrawable(new
                TextureRegion(nextPressTexture)));
        next.setPosition(WORLD_WIDTH -nextTexture.getWidth()/2, nextTexture.getHeight()/2, Align.center);
        stage.addActor(next);

        next.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new FidgetInsThree(game));
                dispose();
            }
        });

        backTexture = new Texture(Gdx.files.internal("backL.png"));
        backPressTexture = new Texture(Gdx.files.internal("backS.png"));
        ImageButton back = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(backTexture)), new TextureRegionDrawable(new
                TextureRegion(backPressTexture)));
        back.setPosition(nextTexture.getWidth()/2, nextTexture.getHeight()/2, Align.center);
        stage.addActor(back);

        back.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new FidgetInsOne(game));
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
        dirTexture.dispose();
        diTexture.dispose();
        nextTexture.dispose();
        nextPressTexture.dispose();
        backTexture.dispose();
        backPressTexture.dispose();
        hTexture.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        clearScreen();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.circle(WORLD_WIDTH/ 2, WORLD_HEIGHT / 2, WORLD_HEIGHT/14);
        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(new ChallOpsScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
