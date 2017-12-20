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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Aman on 5/14/2016.
 */
public class ObstacleColorScreen extends ScreenAdapter{
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private static int colRow=4;
    private static int colCol=22;
    private Texture oneOneTexture;
    private Texture oneTwoTexture;
    private Texture oneThreeTexture;
    private Texture twoOneTexture;
    private Texture twoTwoTexture;
    private Texture twoThreeTexture;
    private Texture threeOneTexture;
    private Texture threeTwoTexture;
    private Texture pointsTexture;
    private Image oneOne;
    private Image oneTwo;
    private Image oneThree;
    private Image twoOne;
    private Image twoTwo;
    private Image twoThree;
    private Image threeOne;
    private Image threeTwo;
    private Image points;

    private Preferences prefs; //for obstacles
    private Preferences prefsTotal; // for total points

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private SpriteBatch batch;

    public ObstacleColorScreen(Game game) {
        this.game = game;
    }

    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        prefsTotal = Gdx.app.getPreferences("scoreCount");
        if (!prefsTotal.contains("scoreCount"))
            prefsTotal.putInteger("scoreCount", 0);

        prefs = Gdx.app.getPreferences("color");
        if (!prefs.contains("color"))
            prefs.putString("color", "GRAY");

        if (!prefs.contains("obsCheck1"))
            prefs.putBoolean("obsCheck1", false);
        if (!prefs.contains("obsCheck2"))
            prefs.putBoolean("obsCheck2", false);
        if (!prefs.contains("obsCheck3"))
            prefs.putBoolean("obsCheck3", false);
        if (!prefs.contains("obsCheck4"))
            prefs.putBoolean("obsCheck4", false);
        if (!prefs.contains("obsCheck5"))
            prefs.putBoolean("obsCheck5", false);
        if (!prefs.contains("obsCheck6"))
            prefs.putBoolean("obsCheck6", false);
        if (!prefs.contains("obsCheck7"))
            prefs.putBoolean("obsCheck7", false);
        if (!prefs.contains("obsCheck8"))
            prefs.putBoolean("obsCheck8", false);

        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();

        pointsTexture = new Texture(Gdx.files.internal("DPoints.png"));
        points = new Image(pointsTexture);
        points.setPosition(pointsTexture.getWidth()/2, WORLD_HEIGHT- (pointsTexture.getHeight()/2), Align.center);
        stage.addActor(points);

        oneOneTexture = new Texture(Gdx.files.internal("100.png"));
        oneOne = new Image(oneOneTexture);
        oneOne.setPosition(WORLD_WIDTH /6, (5*WORLD_HEIGHT)/6,
                Align.center);
        if (!prefs.getBoolean("obsCheck1"))
            stage.addActor(oneOne);

        oneTwoTexture = new Texture(Gdx.files.internal("100.png"));
        oneTwo = new Image(oneTwoTexture);
        oneTwo.setPosition(WORLD_WIDTH /2, (5*WORLD_HEIGHT)/6,
                Align.center);
        if (!prefs.getBoolean("obsCheck4"))
            stage.addActor(oneTwo);

        oneThreeTexture = new Texture(Gdx.files.internal("100.png"));
        oneThree = new Image(oneThreeTexture);
        oneThree.setPosition((5*WORLD_WIDTH) /6, (5*WORLD_HEIGHT)/6,
                Align.center);
        if (!prefs.getBoolean("obsCheck7"))
            stage.addActor(oneThree);

        twoOneTexture = new Texture(Gdx.files.internal("100.png"));
        twoOne = new Image(twoOneTexture);
        twoOne.setPosition(WORLD_WIDTH /6, (WORLD_HEIGHT)/2,
                Align.center);
        if (!prefs.getBoolean("obsCheck2"))
            stage.addActor(twoOne);

        twoTwoTexture = new Texture(Gdx.files.internal("100.png"));
        twoTwo = new Image(twoTwoTexture);
        twoTwo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT)/2,
                Align.center);
        if (!prefs.getBoolean("obsCheck5"))
            stage.addActor(twoTwo);

        twoThreeTexture = new Texture(Gdx.files.internal("100.png"));
        twoThree = new Image(twoThreeTexture);
        twoThree.setPosition((5*WORLD_WIDTH) /6, (WORLD_HEIGHT)/2,
                Align.center);
        if (!prefs.getBoolean("obsCheck8"))
            stage.addActor(twoThree);

        threeOneTexture = new Texture(Gdx.files.internal("100.png"));
        threeOne = new Image(threeOneTexture);
        threeOne.setPosition(WORLD_WIDTH /6, (WORLD_HEIGHT)/6,
                Align.center);
        if (!prefs.getBoolean("obsCheck3"))
            stage.addActor(threeOne);

        threeTwoTexture = new Texture(Gdx.files.internal("100.png"));
        threeTwo = new Image(threeTwoTexture);
        threeTwo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT)/6,
                Align.center);
        if (!prefs.getBoolean("obsCheck6"))
            stage.addActor(threeTwo);
    }

    public void render(float delta) {
        clearScreen();

        int gx = Gdx.input.getX();
        int gy = Gdx.input.getY();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        //colors
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //column 1

        if ( (prefs.getBoolean("obsCheck1")) || (prefs.getBoolean("obsCheck2")) || (prefs.getBoolean("obsCheck3")) ||
                (prefs.getBoolean("obsCheck4")) || (prefs.getBoolean("obsCheck5")) || (prefs.getBoolean("obsCheck6")) ||
                        (prefs.getBoolean("obsCheck7")) || (prefs.getBoolean("obsCheck8")) )
            if (MyBlitzGame.getPS().isSignedIn())
                MyBlitzGame.getPS().unlockAchievementFive();


            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck1")))
                shapeRenderer.setColor(Color.GOLDENROD.r, Color.GOLDENROD.g, Color.GOLDENROD.b, (Color.GOLDENROD.a)/2);
            else
                shapeRenderer.setColor(Color.GOLDENROD.r, Color.GOLDENROD.g, Color.GOLDENROD.b, Color.GOLDENROD.a);
            if (Gdx.input.isTouched())
                if ( (gx < WORLD_WIDTH/3) && (gy < WORLD_HEIGHT/3) && prefs.getBoolean("obsCheck1")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 22;
                    colCol = 4;
                }
            shapeRenderer.rect(WORLD_WIDTH/9, (7*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck2")))
                shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, (Color.RED.a)/2);
            else
                shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, Color.RED.a);
            if (Gdx.input.isTouched())
                if ( (gx < WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && prefs.getBoolean("obsCheck2")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 13;
                    colCol = 4;
                }
            shapeRenderer.rect(WORLD_WIDTH/9, (4*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck3")))
                shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, (Color.PURPLE.a)/2);
            else
                shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
            if (Gdx.input.isTouched())
                if ( (gx < WORLD_WIDTH/3) && (gy > (2*WORLD_HEIGHT)/3) && (gy < WORLD_HEIGHT) && prefs.getBoolean("obsCheck3")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 4;
                    colCol = 4;
                }
            shapeRenderer.rect(WORLD_WIDTH/9, WORLD_HEIGHT/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            //column 2
            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck4")))
                shapeRenderer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, (Color.CYAN.a)/2);
            else
                shapeRenderer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, Color.CYAN.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && prefs.getBoolean("obsCheck4")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 22;
                    colCol = 13;
                }
            shapeRenderer.rect((4*WORLD_WIDTH)/9, (7*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck5")))
                shapeRenderer.setColor(Color.FIREBRICK.r, Color.FIREBRICK.g, Color.FIREBRICK.b, (Color.FIREBRICK.a)/2);
            else
                shapeRenderer.setColor(Color.FIREBRICK.r, Color.FIREBRICK.g, Color.FIREBRICK.b, Color.FIREBRICK.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && prefs.getBoolean("obsCheck5")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 13;
                    colCol = 13;
                }
            shapeRenderer.rect((4*WORLD_WIDTH)/9, (4*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck6")))
                shapeRenderer.setColor(Color.ROYAL.r, Color.ROYAL.g, Color.ROYAL.b, (Color.ROYAL.a)/2);
            else
                shapeRenderer.setColor(Color.ROYAL.r, Color.ROYAL.g, Color.ROYAL.b, Color.ROYAL.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && prefs.getBoolean("obsCheck6")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 4;
                    colCol = 13;
                }
            shapeRenderer.rect((4*WORLD_WIDTH)/9, WORLD_HEIGHT/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            //column 3
            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck7")))
                shapeRenderer.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, (Color.WHITE.a)/2);
            else
                shapeRenderer.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && prefs.getBoolean("obsCheck7")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 22;
                    colCol = 22;
                }
            shapeRenderer.rect((7*WORLD_WIDTH)/9, (7*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!prefs.getBoolean("obsCheck8")))
                shapeRenderer.setColor(Color.LIME.r, Color.LIME.g, Color.LIME.b, (Color.LIME.a)/2);
            else
                shapeRenderer.setColor(Color.LIME.r, Color.LIME.g, Color.LIME.b, Color.LIME.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3)) && prefs.getBoolean("obsCheck8")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 13;
                    colCol = 22;
                }
            shapeRenderer.rect((7*WORLD_WIDTH)/9, (4*WORLD_HEIGHT)/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            shapeRenderer.setColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, Color.GRAY.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3)) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 4;
                    colCol = 22;
                }
            shapeRenderer.rect((7*WORLD_WIDTH)/9, WORLD_HEIGHT/9, WORLD_WIDTH/9, WORLD_HEIGHT/9);

            shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
            shapeRenderer.rect((colCol*WORLD_WIDTH)/27, (colRow*WORLD_HEIGHT)/27, WORLD_WIDTH/27, WORLD_HEIGHT/27);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
            drawPoints();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new OptionsScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();

        updateCol(delta);
    }

    public void drawPoints() {
        String highScoreAsString = Integer.toString(prefsTotal.getInteger("scoreCount"));
        glyphLayout.setText(bitmapFont, highScoreAsString);
        bitmapFont.getData().setScale(4,4);
        bitmapFont.draw(batch, highScoreAsString, (pointsTexture.getWidth()), stage.getViewport().getWorldHeight() - (3*pointsTexture.getHeight())/8);
    }

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        oneOneTexture.dispose();
        oneTwoTexture.dispose();
        oneThreeTexture.dispose();
        twoOneTexture.dispose();
        twoTwoTexture.dispose();
        twoThreeTexture.dispose();
        threeOneTexture.dispose();
        threeTwoTexture.dispose();
        pointsTexture.dispose();
        bitmapFont.dispose();
    }

    private void updateCol(float delta) {
        if (Gdx.input.isTouched()) {
            //column1
            //Checks for goldrenrod without the back button
            int gx = Gdx.input.getX();
            int gy = Gdx.input.getY();

            if ( (gx < WORLD_WIDTH/3) && (gy < WORLD_HEIGHT/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck1"))) {
                prefs.putString("color", "GOLDENROD");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck1")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneOne.remove();
                    prefs.putBoolean("obsCheck1",true);
                    prefs.flush();
                }
            }

            else if ( (gx < WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck2"))) {
                prefs.putString("color", "RED");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck2")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoOne.remove();
                    prefs.putBoolean("obsCheck2",true);
                    prefs.flush();
                }
            }

            else if ( (gx < WORLD_WIDTH/3) && (gy > (2*WORLD_HEIGHT)/3) && (gy < WORLD_HEIGHT) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck3"))) {
                prefs.putString("color", "PURPLE");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck3")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    threeOne.remove();
                    prefs.putBoolean("obsCheck3",true);
                    prefs.flush();
                }
            }

            //column2
            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck4"))) {
                prefs.putString("color","CYAN");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck4")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneTwo.remove();
                    prefs.putBoolean("obsCheck4",true);
                    prefs.flush();
                }
            }
            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck5"))) {
                prefs.putString("color","FIREBREAK");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck5")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoTwo.remove();
                    prefs.putBoolean("obsCheck5",true);
                    prefs.flush();
                }
            }

            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck6"))){
                prefs.putString("color","ROYAL");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck6")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    threeTwo.remove();
                    prefs.putBoolean("obsCheck6",true);
                    prefs.flush();
                }
            }

            //column3
            else if ( (gx > (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3)&& ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck7"))){
                prefs.putString("color","WHITE");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck7")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneThree.remove();
                    prefs.putBoolean("obsCheck7",true);
                    prefs.flush();
                }
            }

            else if ( (gx > (2*WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3)) && ( (prefsTotal.getInteger("scoreCount")>=100) || prefs.getBoolean("obsCheck8"))){
                prefs.putString("color", "LIME");
                prefs.flush();
                if (!prefs.getBoolean("obsCheck8")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoThree.remove();
                    prefs.putBoolean("obsCheck8",true);
                    prefs.flush();
                }
            }

            else if ( (gx > (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3)){
                prefs.putString("color", "GRAY");
                prefs.flush();
            }
        }
    }

    public static void setColRow( int x) {
        colRow = x;
    }
    public static void setColCol(int x) {
        colCol = x;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}

/*
        backTexture = new Texture(Gdx.files.internal("Back.png"));
        backPressTexture = new Texture(Gdx.files.internal("BackPress.png"));
        ImageButton back = new ImageButton(new TextureRegionDrawable(new
                TextureRegion(backTexture)), new TextureRegionDrawable(new
                TextureRegion(backPressTexture)));
        back.setPosition(backTexture.getWidth() / 2, WORLD_HEIGHT - backTexture.getHeight() / 2, Align.center);
        stage.addActor(back);

        back.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new OptionsScreen(game));
                dispose();
            }
        });

    public void dispose() {
        backTexture.dispose();
        backPressTexture.dispose();
    }
*/