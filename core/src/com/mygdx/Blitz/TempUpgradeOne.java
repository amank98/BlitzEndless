package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.input.GestureDetector.GestureListener;

/**
 * Created by Aman on 1/8/2017.
 */

public class TempUpgradeOne extends ScreenAdapter implements GestureListener{
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private float RADIUS = 1f;
    private final Circle bgCircle;
    private ShapeRenderer shapeRenderer;

    private Texture dirTexture;

    private Game game;

    public TempUpgradeOne(Game game) {
        this.game = game;
        bgCircle = new Circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
    }

    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        InputProcessor inputProcessorOne = stage;
        InputProcessor inputProcessorTwo = new GestureDetector(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        shapeRenderer = new ShapeRenderer();

        dirTexture = new Texture(Gdx.files.internal("tempUpOne.png"));
        Image you = new Image(dirTexture);
        you.setPosition(WORLD_WIDTH /2, WORLD_HEIGHT / 2,
                Align.center);
        stage.addActor(you);
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        dirTexture.dispose();
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
            setRadius();
            shapeRenderer.circle(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new TempShopScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
    }

    public void setRadius() {
        if (RADIUS < WORLD_HEIGHT/2 - 20f)
            RADIUS += 5f;
        bgCircle.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,RADIUS);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        game.setScreen(new TempShopScreen(game));
        dispose();
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
