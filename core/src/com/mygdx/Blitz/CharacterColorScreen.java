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

import static com.mygdx.Blitz.StartScreen.customChar;

/**
 * Created by Aman on 5/14/2016.
 */
public class CharacterColorScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private Stage stage;

    private static int colRow=1;
    private static int colCol=1;
    private Texture oneOneTexture;
    private Texture oneTwoTexture;
    private Texture oneThreeTexture;
    private Texture twoOneTexture;
    private Texture twoTwoTexture;
    private Texture twoThreeTexture;
    private Texture threeTwoTexture;
    private Texture threeThreeTexture;
    private Texture pointsTexture;
    private Image oneOne;
    private Image oneTwo;
    private Image oneThree;
    private Image twoOne;
    private Image twoTwo;
    private Image twoThree;
    private Image threeTwo;
    private Image threeThree;
    private Image points;

    private Preferences prefsTotal;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private Viewport viewport;
    private SpriteBatch batch;
    private Camera camera;

    private Preferences charPrefs;

    public CharacterColorScreen(Game game) {
        this.game = game;
    }

    public void show() {

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        prefsTotal = Gdx.app.getPreferences("scoreCount");
        if (!prefsTotal.contains("scoreCount"))
            prefsTotal.putInteger("scoreCount", 0);
        charPrefs = Gdx.app.getPreferences ("charColor"); //character colors

        //prefsTotal.putInteger("scoreCount",99);
        //prefsTotal.flush();

        if (!charPrefs.contains("charColor"))
            charPrefs.putString("charColor", "PURPLE");

        if (!charPrefs.contains("charCheck1"))
            charPrefs.putBoolean("charCheck1", false);
        if (!charPrefs.contains("charCheck2"))
            charPrefs.putBoolean("charCheck2", false);
        if (!charPrefs.contains("charCheck3"))
            charPrefs.putBoolean("charCheck3", false);
        if (!charPrefs.contains("charCheck4"))
            charPrefs.putBoolean("charCheck4", false);
        if (!charPrefs.contains("charCheck5"))
            charPrefs.putBoolean("charCheck5", false);
        if (!charPrefs.contains("charCheck6"))
            charPrefs.putBoolean("charCheck6", false);
        if (!charPrefs.contains("charCheck7"))
            charPrefs.putBoolean("charCheck7", false);
        if (!charPrefs.contains("charCheck8"))
            charPrefs.putBoolean("charCheck8", false);

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
        if (!charPrefs.getBoolean("charCheck1"))
            stage.addActor(oneOne);

        oneTwoTexture = new Texture(Gdx.files.internal("100.png"));
        oneTwo = new Image(oneTwoTexture);
        oneTwo.setPosition(WORLD_WIDTH /2, (5*WORLD_HEIGHT)/6,
                Align.center);
        if (!charPrefs.getBoolean("charCheck3"))
            stage.addActor(oneTwo);

        oneThreeTexture = new Texture(Gdx.files.internal("100.png"));
        oneThree = new Image(oneThreeTexture);
        oneThree.setPosition((5*WORLD_WIDTH) /6, (5*WORLD_HEIGHT)/6,
                Align.center);
        if (!charPrefs.getBoolean("charCheck6"))
            stage.addActor(oneThree);

        twoOneTexture = new Texture(Gdx.files.internal("100.png"));
        twoOne = new Image(twoOneTexture);
        twoOne.setPosition(WORLD_WIDTH /6, (WORLD_HEIGHT)/2,
                Align.center);
        if (!charPrefs.getBoolean("charCheck2"))
            stage.addActor(twoOne);

        twoTwoTexture = new Texture(Gdx.files.internal("100.png"));
        twoTwo = new Image(twoTwoTexture);
        twoTwo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT)/2,
                Align.center);
        if (!charPrefs.getBoolean("charCheck4"))
            stage.addActor(twoTwo);

        twoThreeTexture = new Texture(Gdx.files.internal("100.png"));
        twoThree = new Image(twoThreeTexture);
        twoThree.setPosition((5*WORLD_WIDTH) /6, (WORLD_HEIGHT)/2,
                Align.center);
        if (!charPrefs.getBoolean("charCheck7"))
            stage.addActor(twoThree);


        threeTwoTexture = new Texture(Gdx.files.internal("100.png"));
        threeTwo = new Image(threeTwoTexture);
        threeTwo.setPosition(WORLD_WIDTH /2, (WORLD_HEIGHT)/6,
                Align.center);
        if (!charPrefs.getBoolean("charCheck5"))
        stage.addActor(threeTwo);

        threeThreeTexture = new Texture(Gdx.files.internal("100.png"));
        threeThree = new Image(threeThreeTexture);
        threeThree.setPosition((5*WORLD_WIDTH) /6, (WORLD_HEIGHT)/6,
                Align.center);
        if (!charPrefs.getBoolean("charCheck8"))
        stage.addActor(threeThree);
    }

    public void render(float delta) {
        clearScreen();

        int gx = Gdx.input.getX();
        int gy = Gdx.input.getY();

        if ((charPrefs.getBoolean("charCheck1")) || (charPrefs.getBoolean("charCheck2")) || (charPrefs.getBoolean("charCheck3")) ||
                (charPrefs.getBoolean("charCheck4")) || (charPrefs.getBoolean("charCheck5")) || (charPrefs.getBoolean("charCheck6")) ||
                (charPrefs.getBoolean("charCheck7")) || (charPrefs.getBoolean("charCheck8")) )
            if (MyBlitzGame.getPS().isSignedIn())
                MyBlitzGame.getPS().unlockAchievementFive();

                //colors
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //column 1
            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck1")))
                shapeRenderer.setColor(Color.GOLDENROD.r, Color.GOLDENROD.g, Color.GOLDENROD.b, (Color.GOLDENROD.a)/2);
            else
                shapeRenderer.setColor(Color.GOLDENROD.r, Color.GOLDENROD.g, Color.GOLDENROD.b, Color.GOLDENROD.a);
            if (Gdx.input.isTouched())
                if ( (gx < WORLD_WIDTH/3) && (gy < WORLD_HEIGHT/3) && charPrefs.getBoolean("charCheck1")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    //oneOne.remove();
                    colRow = 5;
                    colCol = 1;
                }
            shapeRenderer.circle(WORLD_WIDTH/6,(5*WORLD_HEIGHT)/6, WORLD_HEIGHT/10f);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck2")))
                shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, (Color.RED.a)/2);
            else
                shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, Color.RED.a);
            if (Gdx.input.isTouched() && charPrefs.getBoolean("charCheck2"))
                if ( (gx < WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3)  ) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 3;
                    colCol = 1;
                }
            shapeRenderer.circle(WORLD_WIDTH/6,WORLD_HEIGHT/2, WORLD_HEIGHT/10f);


            shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
            if (Gdx.input.isTouched())
                if ( (gx < WORLD_WIDTH/3) && (gy > (2*WORLD_HEIGHT)/3) && (gy < WORLD_HEIGHT)) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 1;
                    colCol = 1;
                }
            shapeRenderer.circle(WORLD_WIDTH / 6, WORLD_HEIGHT / 6, WORLD_HEIGHT / 10f);

            //column 2
            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck3")))
                shapeRenderer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, (Color.CYAN.a)/2);
            else
                shapeRenderer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, Color.CYAN.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && charPrefs.getBoolean("charCheck3")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 5;
                    colCol = 3;
                }
            shapeRenderer.circle(WORLD_WIDTH / 2, (5 * WORLD_HEIGHT) / 6, WORLD_HEIGHT / 10f);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck4")))
                shapeRenderer.setColor(Color.FIREBRICK.r, Color.FIREBRICK.g, Color.FIREBRICK.b, (Color.FIREBRICK.a)/2);
            else
                shapeRenderer.setColor(Color.FIREBRICK.r, Color.FIREBRICK.g, Color.FIREBRICK.b, Color.FIREBRICK.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && charPrefs.getBoolean("charCheck4")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 3;
                    colCol = 3;
                }
            shapeRenderer.circle(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, WORLD_HEIGHT / 10f);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck5")))
                shapeRenderer.setColor(Color.ROYAL.r, Color.ROYAL.g, Color.ROYAL.b, (Color.ROYAL.a)/2);
            else
                shapeRenderer.setColor(Color.ROYAL.r, Color.ROYAL.g, Color.ROYAL.b, Color.ROYAL.a);
            if (Gdx.input.isTouched())
                if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && charPrefs.getBoolean("charCheck5")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 1;
                    colCol = 3;
                }
            shapeRenderer.circle(WORLD_WIDTH/2, WORLD_HEIGHT/6, WORLD_HEIGHT/10f);

            //column 3
            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck6")))
                shapeRenderer.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, (Color.WHITE.a)/2);
            else
                shapeRenderer.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && charPrefs.getBoolean("charCheck6")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 5;
                    colCol = 5;
                }
            shapeRenderer.circle((5*WORLD_WIDTH/6),(5*WORLD_HEIGHT)/6, WORLD_HEIGHT/10f);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck7")))
                shapeRenderer.setColor(Color.LIME.r, Color.LIME.g, Color.LIME.b, (Color.LIME.a)/2);
            else
                shapeRenderer.setColor(Color.LIME.r, Color.LIME.g, Color.LIME.b, Color.LIME.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3)) && charPrefs.getBoolean("charCheck7")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 3;
                    colCol = 5;
                }
            shapeRenderer.circle((5*WORLD_WIDTH/6),WORLD_HEIGHT/2, WORLD_HEIGHT/10f);

            if ( (prefsTotal.getInteger("scoreCount") < 100) && (!charPrefs.getBoolean("charCheck8")))
                shapeRenderer.setColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, (Color.GRAY.a)/2);
            else
                shapeRenderer.setColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, Color.GRAY.a);
            if (Gdx.input.isTouched())
                if ( (gx > (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && charPrefs.getBoolean("charCheck8")) {
                    shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                    colRow = 1;
                    colCol = 5;
                }
            shapeRenderer.circle((5 * WORLD_WIDTH / 6), WORLD_HEIGHT / 6, WORLD_HEIGHT / 10f);

            shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
            //shapeRenderer.rect((colCol*WORLD_WIDTH)/3, (colRow*WORLD_HEIGHT)/3, WORLD_WIDTH/6, WORLD_HEIGHT/6);
            shapeRenderer.circle((colCol*WORLD_WIDTH)/6,(colRow*WORLD_HEIGHT)/6,WORLD_HEIGHT/20f);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
            drawPoints();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
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

    public static void setColRow( int x) {
        colRow = x;
    }
    public static void setColCol(int x) {
        colCol = x;
    }

    private void updateCol(float delta) {
        if (Gdx.input.isTouched()) {
            //column1
            int gx = Gdx.input.getX();
            int gy = Gdx.input.getY();

            if ( (gx < WORLD_WIDTH/3) && (gy < WORLD_HEIGHT/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck1")) ) {
                customChar = Color.GOLDENROD;
                charPrefs.putString("charColor", "GOLDENROD");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck1")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneOne.remove();
                    charPrefs.putBoolean("charCheck1",true);
                    charPrefs.flush();
                }
            }

            else if ( (gx < WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck2"))) {
                customChar = Color.RED;
                charPrefs.putString("charColor", "RED");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck2")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoOne.remove();
                    charPrefs.putBoolean("charCheck2",true);
                    charPrefs.flush();
                }
            }

            else if ( (gx < WORLD_WIDTH/3) && (gy > (2*WORLD_HEIGHT)/3) && (gy < WORLD_HEIGHT)) {
                customChar = Color.PURPLE;
                charPrefs.putString("charColor", "PURPLE");
                charPrefs.flush();
            }
            //column2
            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck3"))) {
                customChar = Color.CYAN;
                charPrefs.putString("charColor","CYAN");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck3")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneTwo.remove();
                    charPrefs.putBoolean("charCheck3",true);
                    charPrefs.flush();
                }
            }
            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck4"))) {
                customChar = Color.FIREBRICK;
                charPrefs.putString("charColor","FIREBRICK");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck4")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoTwo.remove();
                    charPrefs.putBoolean("charCheck4",true);
                    charPrefs.flush();
                }
            }

            else if ( (gx > WORLD_WIDTH/3) && (gx < (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck5"))){
                customChar = Color.ROYAL;
                charPrefs.putString("charColor","ROYAL");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck5")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    threeTwo.remove();
                    charPrefs.putBoolean("charCheck5",true);
                    charPrefs.flush();
                }
            }

            //column3
            else if ( (gx > (2*WORLD_WIDTH)/3) && (gy < WORLD_HEIGHT/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck6"))){
                customChar = Color.WHITE;
                charPrefs.putString("charColor","WHITE");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck6")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    oneThree.remove();
                    charPrefs.putBoolean("charCheck6",true);
                    charPrefs.flush();
                }
            }

            else if ( (gx > (2*WORLD_WIDTH/3) && (gy > WORLD_HEIGHT/3) && (gy < (2*WORLD_HEIGHT)/3)) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck7"))){
                customChar = Color.LIME;
                charPrefs.putString("charColor","LIME");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck7")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    twoThree.remove();
                    charPrefs.putBoolean("charCheck7",true);
                    charPrefs.flush();
                }
            }

            else if ( (gx > (2*WORLD_WIDTH)/3) && (gy > (2*WORLD_HEIGHT)/3) && ( (prefsTotal.getInteger("scoreCount")>=100) || charPrefs.getBoolean("charCheck8"))){
                customChar = Color.GRAY;
                charPrefs.putString("charColor","GRAY");
                charPrefs.flush();
                if (!charPrefs.getBoolean("charCheck8")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-100);
                    prefsTotal.flush();
                    threeThree.remove();
                    charPrefs.putBoolean("charCheck8",true);
                    charPrefs.flush();
                }
            }
        }
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
        threeTwoTexture.dispose();
        threeThreeTexture.dispose();
        pointsTexture.dispose();
        bitmapFont.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }



}