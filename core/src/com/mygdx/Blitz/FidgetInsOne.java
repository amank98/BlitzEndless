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

import javafx.stage.Screen;

/**
 * Created by Aman on 5/25/2017.
 */

public class FidgetInsOne extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;

    private float RADIUS = 1f;
    private ShapeRenderer shapeRenderer;

    private Texture dirTexture;
    private Texture diTexture;
    private Texture nextTexture;
    private Texture nextPressTexture;
    private Texture winnerTexture;

    private Game game;

    public FidgetInsOne(Game game) {
        this.game = game;
    }

    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        shapeRenderer = new ShapeRenderer();

        dirTexture = new Texture(Gdx.files.internal("FI1.png")); //80
        Image you = new Image(dirTexture);
        you.setPosition(WORLD_WIDTH /2, (3*WORLD_HEIGHT) / 4,
                Align.center);
        stage.addActor(you);

        diTexture = new Texture(Gdx.files.internal("spinRewardDate.png")); //80
        Image yo = new Image(diTexture);
        yo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT) / 2,
                Align.center);
        stage.addActor(yo);

        winnerTexture = new Texture(Gdx.files.internal("winner.png")); //80
        Image win = new Image(winnerTexture);
        win.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT) / 4,
                Align.center);
        stage.addActor(win);

        nextTexture = new Texture(Gdx.files.internal("nextL.png"));
        nextPressTexture = new Texture(Gdx.files.internal("nextS.png"));
        ImageButton shop = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(nextTexture)), new TextureRegionDrawable(new
                TextureRegion(nextPressTexture)));
        shop.setPosition(WORLD_WIDTH -nextTexture.getWidth()/2, nextTexture.getHeight()/2, Align.center);
        stage.addActor(shop);

        shop.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new FidgetInsTwo(game));
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
        dirTexture.dispose();
        nextTexture.dispose();
        nextPressTexture.dispose();
        diTexture.dispose();
        winnerTexture.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        clearScreen();
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
