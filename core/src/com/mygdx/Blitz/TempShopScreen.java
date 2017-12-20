package com.mygdx.Blitz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mygdx.Blitz.StartScreen.customChar;
import static com.mygdx.Blitz.StartScreen.customObs;

/**
 * Created by Aman on 12/29/2015.
 */

public class TempShopScreen extends ScreenAdapter implements GestureListener{
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private Preferences tempPrefs;
    private Preferences prefs; //obstacles
    private Preferences prefsTotal;
    private Preferences charPrefs; //character color
    private Texture upOneTexture;
    private Texture upTwoTexture;
    private Texture pointsTexture;
    private Image upTwo;
    private Image points;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private Viewport viewport;
    private SpriteBatch batch;

    private Vector3 touch = new Vector3();

    //  THIS SHOP IS FOR TEMPORARY UPGRADES

    public TempShopScreen(Game game) {
        this.game = game;
    }

    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();
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

        Gdx.input.setCatchBackKey(true);
        prefs = Gdx.app.getPreferences("color"); //obstacle colors
        prefsTotal = Gdx.app.getPreferences("scoreCount");
        charPrefs = Gdx.app.getPreferences("charColor"); //character colors
        tempPrefs = Gdx.app.getPreferences("tColo"); //stand for temp Color (colo cuz was messed up before)
        if (!tempPrefs.contains("tColo"))
            tempPrefs.putBoolean("tColo", false);
        if (!tempPrefs.contains("coloT1"))
            tempPrefs.putBoolean("coloT1", false);
        if (!tempPrefs.contains("coloT2"))
            tempPrefs.putBoolean("coloT2", false);

        upOneTexture = new Texture(Gdx.files.internal("500.png"));
        Image upOne = new Image(upOneTexture);
        upOne.setPosition(WORLD_WIDTH /4, WORLD_HEIGHT / 5,
                Align.center);
        stage.addActor(upOne);

        upTwoTexture = new Texture(Gdx.files.internal("250.png"));
        upTwo = new Image(upTwoTexture);
        upTwo.setPosition( (3*WORLD_WIDTH) /4, WORLD_HEIGHT / 5,
                Align.center);
        stage.addActor(upTwo);

        pointsTexture = new Texture(Gdx.files.internal("DPoints.png"));
        points = new Image(pointsTexture);
        points.setPosition(pointsTexture.getWidth()/2, WORLD_HEIGHT- (pointsTexture.getHeight()/2), Align.center);
        stage.addActor(points);
    }

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        upOneTexture.dispose();
        upTwoTexture.dispose();
        pointsTexture.dispose();
        bitmapFont.dispose();
    }

    public void render(float delta) {
        clearScreen();

        if (prefs.getString("color").equals("GOLDENROD"))
            customObs = Color.GOLDENROD;
        else if (prefs.getString("color").equals("RED"))
            customObs = Color.RED;
        else if (prefs.getString("color").equals("PURPLE"))
            customObs = Color.PURPLE;
        else if(prefs.getString("color").equals("CYAN"))
            customObs = Color.CYAN;
        else if(prefs.getString("color").equals("FIREBREAK"))
            customObs = Color.FIREBRICK;
        else if(prefs.getString("color").equals("ROYAL"))
            customObs = Color.ROYAL;
        else if(prefs.getString("color").equals("WHITE"))
            customObs = Color.WHITE;
        else if(prefs.getString("color").equals("LIME"))
            customObs = Color.LIME;
        else if (prefs.getString("color").equals("GRAY"))
            customObs = Color.GRAY;

        if (charPrefs.getString("charColor").equals("GOLDENROD"))
            customChar = Color.GOLDENROD;
        else if (charPrefs.getString("charColor").equals("RED"))
            customChar = Color.RED;
        else if (charPrefs.getString("charColor").equals("PURPLE"))
            customChar = Color.PURPLE;
        else if(charPrefs.getString("charColor").equals("CYAN"))
            customChar = Color.CYAN;
        else if(charPrefs.getString("charColor").equals("FIREBREAK"))
            customChar = Color.FIREBRICK;
        else if(charPrefs.getString("charColor").equals("ROYAL"))
            customChar = Color.ROYAL;
        else if(charPrefs.getString("charColor").equals("WHITE"))
            customChar = Color.WHITE;
        else if(charPrefs.getString("charColor").equals("LIME"))
            customChar = Color.LIME;
        else if (charPrefs.getString("charColor").equals("GRAY"))
            customChar = Color.GRAY;


        if ((tempPrefs.getBoolean("coloT1")) || (tempPrefs.getBoolean("coloT2")))
            if (MyBlitzGame.getPS().isSignedIn())
                MyBlitzGame.getPS().unlockAchievementFour();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
            //OUTLINE
            shapeRenderer.line(1,1,WORLD_WIDTH,1);
            shapeRenderer.line(1,WORLD_HEIGHT-1,WORLD_WIDTH,WORLD_HEIGHT-1);
            shapeRenderer.line(1,1,1,WORLD_HEIGHT-1);
            shapeRenderer.line(WORLD_WIDTH-1,1,WORLD_WIDTH-1,WORLD_HEIGHT-1);

            //INNER LINES
            shapeRenderer.line(WORLD_WIDTH/2, WORLD_HEIGHT, WORLD_WIDTH/2, 0);
        shapeRenderer.end();
        //lives

        if (prefsTotal.getInteger("scoreCount") < 500) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a / 2);
                shapeRenderer.circle(WORLD_WIDTH / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 10);
                if (tempPrefs.getBoolean("coloT1"))
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                else
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                shapeRenderer.rect((WORLD_WIDTH / 4) - (WORLD_WIDTH / 20), (WORLD_HEIGHT / 2) - (WORLD_HEIGHT / 50), WORLD_WIDTH / 10, WORLD_HEIGHT / 25);
                shapeRenderer.rect((WORLD_WIDTH / 4) - (WORLD_HEIGHT / 50), (WORLD_HEIGHT / 2) - (WORLD_WIDTH / 20), WORLD_HEIGHT / 25, WORLD_WIDTH / 10);

                if (tempPrefs.getBoolean("coloT1")) {
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);//(x,y,width,height)
                    shapeRenderer.rect((WORLD_WIDTH/2)- (WORLD_WIDTH/15), 1, WORLD_WIDTH/15, WORLD_HEIGHT/15);
                }
                else {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    shapeRenderer.rect((WORLD_WIDTH/2)- (WORLD_WIDTH/15), 1, WORLD_WIDTH/15, WORLD_HEIGHT/15);
                }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        if (prefsTotal.getInteger("scoreCount") >= 500) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
                shapeRenderer.circle(WORLD_WIDTH / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 10);
                if (tempPrefs.getBoolean("coloT1"))
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                else
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                shapeRenderer.rect((WORLD_WIDTH / 4) - (WORLD_WIDTH / 20), (WORLD_HEIGHT / 2) - (WORLD_HEIGHT / 50), WORLD_WIDTH / 10, WORLD_HEIGHT / 25);
                shapeRenderer.rect((WORLD_WIDTH / 4) - (WORLD_HEIGHT / 50), (WORLD_HEIGHT / 2) - (WORLD_WIDTH / 20), WORLD_HEIGHT / 25, WORLD_WIDTH / 10);

                if (tempPrefs.getBoolean("coloT1")) {
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);//(x,y,width,height)
                    shapeRenderer.rect((WORLD_WIDTH/2)- (WORLD_WIDTH/15), 1, WORLD_WIDTH/15, WORLD_HEIGHT/15);
                }
                else {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    shapeRenderer.rect((WORLD_WIDTH/2)- (WORLD_WIDTH/15), 1, WORLD_WIDTH/15, WORLD_HEIGHT/15);
                }
            shapeRenderer.end();
        }

        if (prefsTotal.getInteger("scoreCount") < 250) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //invincibility
                shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a/2);
                shapeRenderer.circle((3 * WORLD_WIDTH) / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 10);
                if (tempPrefs.getBoolean("coloT2"))
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                else
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                shapeRenderer.circle((3 * WORLD_WIDTH) / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 20);

                if (tempPrefs.getBoolean("coloT2")) {
                    shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                    shapeRenderer.rect((WORLD_WIDTH) - (WORLD_WIDTH / 15), 0, WORLD_WIDTH / 15, WORLD_HEIGHT / 15);
                } else {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    shapeRenderer.rect((WORLD_WIDTH) - (WORLD_WIDTH / 15), 0, WORLD_WIDTH / 15, WORLD_HEIGHT / 15);
                }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        if (prefsTotal.getInteger("scoreCount") >= 250) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
            shapeRenderer.circle((3 * WORLD_WIDTH) / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 10);
            if (tempPrefs.getBoolean("coloT2"))
                shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
            else
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
            shapeRenderer.circle((3 * WORLD_WIDTH) / 4, WORLD_HEIGHT / 2, WORLD_WIDTH / 20);

            if (tempPrefs.getBoolean("coloT2")) {
                shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                shapeRenderer.rect((WORLD_WIDTH) - (WORLD_WIDTH / 15), 0, WORLD_WIDTH / 15, WORLD_HEIGHT / 15);
            } else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                shapeRenderer.rect((WORLD_WIDTH) - (WORLD_WIDTH / 15), 0, WORLD_WIDTH / 15, WORLD_HEIGHT / 15);
            }
            shapeRenderer.end();
        }
        //shapeRenderer.end();
        //RECT OUTLINES

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
            //shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);
            shapeRenderer.rect((WORLD_WIDTH/2)- (WORLD_WIDTH/15), 0, WORLD_WIDTH/15, WORLD_HEIGHT/15);
            shapeRenderer.rect((WORLD_WIDTH)- (WORLD_WIDTH/15), 0, WORLD_WIDTH/15, WORLD_HEIGHT/15);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
            drawPoints();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(new ShoptionScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
    }

    public void drawPoints() {
        String highScoreAsString = Integer.toString(prefsTotal.getInteger("scoreCount"));
        glyphLayout.setText(bitmapFont, highScoreAsString);
        bitmapFont.getData().setScale(4,4);
        bitmapFont.draw(batch, highScoreAsString, (pointsTexture.getWidth()), viewport.getWorldHeight() - (3*pointsTexture.getHeight())/8);
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
        //y coordinate is like Q1
        camera.unproject(touch.set(x, y, 0));

        if ((touch.x < stage.getViewport().getWorldWidth() / 2) && (prefsTotal.getInteger("scoreCount") >= 500) ) {
            if (!tempPrefs.getBoolean("coloT1")){
                tempPrefs.putBoolean("coloT1", true);
                prefsTotal.putInteger("scoreCount",prefsTotal.getInteger("scoreCount")-500);
                tempPrefs.flush();
                prefsTotal.flush();
            }
        }
        else if((touch.x > stage.getViewport().getWorldWidth()/2)  && (prefsTotal.getInteger("scoreCount") >= 250)){
            if (!tempPrefs.getBoolean("coloT2")){
                tempPrefs.putBoolean("coloT2", true);
                prefsTotal.putInteger("scoreCount",prefsTotal.getInteger("scoreCount")-250);//prefsTotal.getInteger("scoreCount")-500);
                tempPrefs.flush();
                prefsTotal.flush();
            }
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //y coordinate is like Q4
        camera.unproject(touch.set(x, y, 0));

        if (touch.x < stage.getViewport().getWorldWidth() / 2)
            game.setScreen(new com.mygdx.Blitz.TempUpgradeOne(game));
        else if (touch.x > stage.getViewport().getWorldWidth() / 2)
            game.setScreen(new com.mygdx.Blitz.TempUpgradeTwo(game));
        dispose();
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
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
