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

public class RewardScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private float RADIUS = 1f;
    private final Circle bgCircle;
    private ShapeRenderer shapeRenderer;

    private Texture dirTexture;
    private Texture diTexture;

    private Game game;

    public RewardScreen(Game game) {
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

        //Words
        dirTexture = new Texture(Gdx.files.internal("rewardInfo.png")); //80
        Image you = new Image(dirTexture);
        you.setPosition(WORLD_WIDTH /2, (3*WORLD_HEIGHT) / 4,
                Align.center);
        stage.addActor(you);

        //Image
        diTexture = new Texture(Gdx.files.internal("spinner.png")); //80
        Image yo = new Image(diTexture);
        yo.setPosition(WORLD_WIDTH /2, (3*WORLD_HEIGHT) / 8,
                Align.center);
        stage.addActor(yo);

    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        dirTexture.dispose();
        diTexture.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        clearScreen();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            setRadius();
            shapeRenderer.circle(WORLD_WIDTH/2, (3*WORLD_HEIGHT)/8, RADIUS);
        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(new ChallOpsScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
    }

    public void setRadius() {
        if (RADIUS < (WORLD_HEIGHT)/5 )
            RADIUS += (Gdx.graphics.getHeight()/216f);
        bgCircle.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, RADIUS);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
