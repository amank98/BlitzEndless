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
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Aman on 12/29/2015.
 * This is the permanent shop screen
 */



public class ShopScreen extends ScreenAdapter implements GestureListener{
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private Stage stage;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private boolean backPress = false;
    private Preferences shopPrefs;
    private Preferences tempPrefs;
    private Preferences prefsTotal;

    private Texture upOneTexture;
    private Texture upTwoTexture;
    private Texture upThreeTexture;
    private Texture upFourTexture;
    private Texture upFiveTexture;
    private Texture upSixTexture;
    private Texture pointsTexture;

    private Preferences prefs; //obstacles
    private Preferences charPrefs; //character color

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private Viewport viewport;
    private SpriteBatch batch;

    private Image upOne;
    private Image upTwo;
    private Image upThree;
    private Image upFour;
    private Image upFive;
    private Image upSix;
    private Image points;

    private Vector3 touch = new Vector3();

    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //  IMPORTANT!!!!
    //  THIS SHOP IS FOR PERMANENT UPGRADES
    //  UPGRADE 1 (1,1) IS IN STARTSCREEN AS SHOPPREFS
    //  UPGRADE 2 (2,1) IS IN GAMESCREEN AS TEMPPREFS // tColo IS colo2 !!!!!
    //  UPGRADE 3 (3,1) IS IN GAMESCREEN AS SHOPPREFS
    //  UPGRADE 4 (1,2) IS IN GAMESSCREEN AS SHOPPREFS
    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public ShopScreen(Game game) {
        this.game = game;
    }

    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();

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
        charPrefs = Gdx.app.getPreferences("charColor"); //character colors
        shopPrefs = Gdx.app.getPreferences("colo");
        prefsTotal = Gdx.app.getPreferences("scoreCount");
        if (!shopPrefs.contains("colo"))
            shopPrefs.putBoolean("colo", false);
        tempPrefs = Gdx.app.getPreferences("tColo"); //stand for temp Color (colo cuz was messed up before)
        if (!tempPrefs.contains("tColo"))
            tempPrefs.putBoolean("tColo", false);
        if (!shopPrefs.contains("colo3"))
            shopPrefs.putBoolean("colo3", false);
        if (!shopPrefs.contains("colo4"))
            shopPrefs.putBoolean("colo4", false);
        if (!shopPrefs.contains("colo5"))
            shopPrefs.putBoolean("colo5", false);
        if (!shopPrefs.contains("colo6"))
            shopPrefs.putBoolean("colo6", false);
        if (!prefsTotal.contains("scoreCount"))
            prefsTotal.putInteger("scoreCount", 0);
        if (!prefs.contains("color"))
            prefs.putString("color", "GRAY");

        if (!shopPrefs.contains("coloCheck1"))
            shopPrefs.putBoolean("coloCheck1", false);
        if (!shopPrefs.contains("coloCheck2"))
            shopPrefs.putBoolean("coloCheck2", false);
        if (!shopPrefs.contains("coloCheck3"))
            shopPrefs.putBoolean("coloCheck3", false);
        if (!shopPrefs.contains("coloCheck4"))
            shopPrefs.putBoolean("coloCheck4", false);
        if (!shopPrefs.contains("coloCheck5"))
            shopPrefs.putBoolean("coloCheck5", false);
        if (!shopPrefs.contains("coloCheck6"))
            shopPrefs.putBoolean("coloCheck6", false);

        if (prefs.getString("color").equals("GOLDENROD"))
            StartScreen.customObs = Color.GOLDENROD;
        else if (prefs.getString("color").equals("RED"))
            StartScreen.customObs = Color.RED;
        else if (prefs.getString("color").equals("PURPLE"))
            StartScreen.customObs = Color.PURPLE;
        else if(prefs.getString("color").equals("CYAN"))
            StartScreen.customObs = Color.CYAN;
        else if(prefs.getString("color").equals("FIREBREAK"))
            StartScreen.customObs = Color.FIREBRICK;
        else if(prefs.getString("color").equals("ROYAL"))
            StartScreen.customObs = Color.ROYAL;
        else if(prefs.getString("color").equals("WHITE"))
            StartScreen.customObs = Color.WHITE;
        else if(prefs.getString("color").equals("LIME"))
            StartScreen.customObs = Color.LIME;
        else if (prefs.getString("color").equals("GRAY"))
            StartScreen.customObs = Color.GRAY;

        if (charPrefs.getString("charColor").equals("GOLDENROD"))
            StartScreen.customChar = Color.GOLDENROD;
        else if (charPrefs.getString("charColor").equals("RED"))
            StartScreen.customChar = Color.RED;
        else if (charPrefs.getString("charColor").equals("PURPLE"))
            StartScreen.customChar = Color.PURPLE;
        else if(charPrefs.getString("charColor").equals("CYAN"))
            StartScreen.customChar = Color.CYAN;
        else if(charPrefs.getString("charColor").equals("FIREBREAK"))
            StartScreen.customChar = Color.FIREBRICK;
        else if(charPrefs.getString("charColor").equals("ROYAL"))
            StartScreen.customChar = Color.ROYAL;
        else if(charPrefs.getString("charColor").equals("WHITE"))
            StartScreen.customChar = Color.WHITE;
        else if(charPrefs.getString("charColor").equals("LIME"))
            StartScreen.customChar = Color.LIME;
        else if (charPrefs.getString("charColor").equals("GRAY"))
            StartScreen.customChar = Color.GRAY;

        GestureDetector gd = new GestureDetector(this);
        upOneTexture = new Texture(Gdx.files.internal("250.png"));// ALL SIZES ARE 70 (COOLTEXT)
        upOne = new Image(upOneTexture);
        upOne.setPosition(stage.getViewport().getWorldWidth()  /6, ( (2*stage.getViewport().getWorldHeight()) / 3) + (stage.getViewport().getWorldHeight()/15),
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck1"))
            stage.addActor(upOne);

        upTwoTexture = new Texture(Gdx.files.internal("500.png"));
        upTwo = new Image(upTwoTexture);
        upTwo.setPosition(stage.getViewport().getWorldWidth()  /6, (stage.getViewport().getWorldHeight()/3) + (stage.getViewport().getWorldHeight()/15),
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck2"))
            stage.addActor(upTwo);

        upThreeTexture = new Texture(Gdx.files.internal("500.png"));
        upThree = new Image(upThreeTexture);
        upThree.setPosition(stage.getViewport().getWorldWidth()  /6, stage.getViewport().getWorldHeight() / 15,
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck3"))
            stage.addActor(upThree);

        upFourTexture = new Texture(Gdx.files.internal("1000.png"));
        upFour = new Image(upFourTexture);
        upFour.setPosition(stage.getViewport().getWorldWidth()  /2, ( (2*stage.getViewport().getWorldHeight()) / 3) + (stage.getViewport().getWorldHeight()/15),
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck4"))
            stage.addActor(upFour);

        upFiveTexture = new Texture(Gdx.files.internal("1000.png"));
        upFive = new Image(upFiveTexture);
        upFive.setPosition( (5*stage.getViewport().getWorldWidth() ) /6, ( (2*stage.getViewport().getWorldHeight()) / 3) + (stage.getViewport().getWorldHeight()/15),
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck5"))
            stage.addActor(upFive);

        upSixTexture = new Texture(Gdx.files.internal("2000.png"));
        upSix = new Image(upSixTexture);
        upSix.setPosition((2*stage.getViewport().getWorldWidth() ) /3, stage.getViewport().getWorldHeight() / 15,
                Align.center);
        if (!shopPrefs.getBoolean("coloCheck6"))
            stage.addActor(upSix);

        pointsTexture = new Texture(Gdx.files.internal("DPoints.png"));
        points = new Image(pointsTexture);
        points.setPosition(pointsTexture.getWidth()/2, stage.getViewport().getWorldHeight()- (pointsTexture.getHeight()/2), Align.center);
        stage.addActor(points);
    }

    public void render(float delta) {
        clearScreen();

        //CHECKS FOURTH ACHIEVEMENT
        if ((shopPrefs.getBoolean("colo")) || (shopPrefs.getBoolean("tColo")) || (shopPrefs.getBoolean("colo3")) ||
                (shopPrefs.getBoolean("colo4")) || (shopPrefs.getBoolean("colo5")) || (shopPrefs.getBoolean("colo6")) )
            if (MyBlitzGame.getPS().isSignedIn())
                MyBlitzGame.getPS().unlockAchievementFour();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            if (shopPrefs.getBoolean("colo")) {//checks counter to set box color (1,1)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect(0, (2 * stage.getViewport().getWorldHeight()) / 3, WORLD_WIDTH / 3, stage.getViewport().getWorldHeight() / 3); //(x,y,width,height)
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, (2*stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect(0, (2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 3, stage.getViewport().getWorldHeight() / 3);
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, (2*stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }

            if (tempPrefs.getBoolean("tColo")) {//checks counter to set box color (2,1)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect(0, WORLD_HEIGHT / 3, stage.getViewport().getWorldWidth()  / 3, WORLD_HEIGHT / 3);
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, (stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect(0, WORLD_HEIGHT / 3, stage.getViewport().getWorldWidth()  / 3, WORLD_HEIGHT / 3);
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, (stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }

            if (shopPrefs.getBoolean("colo3")) {//checks counter to set box color (3,1)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect(0, 0, WORLD_WIDTH / 3, WORLD_HEIGHT/3);
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect(0, 0, stage.getViewport().getWorldWidth()  / 3, stage.getViewport().getWorldHeight()/3);
                shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            }

            if (shopPrefs.getBoolean("colo4")) {//checks counter to set box color (1,3)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect(stage.getViewport().getWorldWidth() /3,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 3, stage.getViewport().getWorldHeight() / 3);
                shapeRenderer.rect((9*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect(WORLD_WIDTH/3,(2 * WORLD_HEIGHT) / 3, stage.getViewport().getWorldWidth()  / 3, WORLD_HEIGHT / 3);
                shapeRenderer.rect((9*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }

            if (shopPrefs.getBoolean("colo5")) {//checks counter to set box color (1,3)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect((2*stage.getViewport().getWorldWidth() )/3,(2 * WORLD_HEIGHT) / 3, stage.getViewport().getWorldWidth()  / 3, WORLD_HEIGHT / 3);
                shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect((2*WORLD_WIDTH)/3,(2 * WORLD_HEIGHT) / 3, stage.getViewport().getWorldWidth()  / 3, WORLD_HEIGHT / 3);
                shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }

            if (shopPrefs.getBoolean("colo6")) {//checks counter to set box color (1,3)
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
                //shapeRenderer.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
                //shapeRenderer.rect(WORLD_WIDTH/3, 0, (2*WORLD_WIDTH) / 3, (2*WORLD_HEIGHT)/3);
                shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }
            else {
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
                //shapeRenderer.rect(stage.getViewport().getWorldWidth() /3, 0, (2*stage.getViewport().getWorldWidth() stage.getViewport().getWorldWidth() ) / 3, (2*WORLD_HEIGHT)/3);
                shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            }
        shapeRenderer.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            if ( (prefsTotal.getInteger("scoreCount") < 250) && (!shopPrefs.getBoolean("coloCheck1")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);

            shapeRenderer.rect(0, (5 * stage.getViewport().getWorldHeight() / 6), stage.getViewport().getWorldWidth()  / 9, stage.getViewport().getWorldHeight() / 20);// draws rectangles for first upgrade (1.1)
            shapeRenderer.rect((2 * stage.getViewport().getWorldWidth() ) / 9, (5 * stage.getViewport().getWorldHeight() / 6), stage.getViewport().getWorldWidth()  / 9, stage.getViewport().getWorldHeight() / 20);

            if ( (prefsTotal.getInteger("scoreCount") < 500)  && (!shopPrefs.getBoolean("coloCheck2")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.circle(stage.getViewport().getWorldWidth()  / 12, (stage.getViewport().getWorldHeight()) / 2, Gdx.graphics.getWidth() / 30f); //draws circles for second upgrade (2,1)
            shapeRenderer.circle(stage.getViewport().getWorldWidth()  / 4, (stage.getViewport().getWorldHeight()) / 2, Gdx.graphics.getWidth() / 40f);

            if ( (prefsTotal.getInteger("scoreCount") < 500) && (!shopPrefs.getBoolean("coloCheck3")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.circle(stage.getViewport().getWorldWidth()  / 12, (stage.getViewport().getWorldHeight()) / 6, Gdx.graphics.getWidth() / 40f); //draws circles for third upgrade (3,1)
            shapeRenderer.circle(stage.getViewport().getWorldWidth()  / 4, (stage.getViewport().getWorldHeight()) / 6, Gdx.graphics.getWidth() / 30f);

            if ( (prefsTotal.getInteger("scoreCount") < 1000) && (!shopPrefs.getBoolean("coloCheck4")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.rect((7*stage.getViewport().getWorldWidth() )/16, (11*stage.getViewport().getWorldHeight())/15, stage.getViewport().getWorldHeight()/20, stage.getViewport().getWorldWidth() /9); //left bar //draws rects for fourth upgrade (1,2)
            shapeRenderer.rect((17*stage.getViewport().getWorldWidth() )/32, (11*stage.getViewport().getWorldHeight())/15, stage.getViewport().getWorldHeight()/20, stage.getViewport().getWorldWidth() /9); //right bar
            shapeRenderer.rect(((7*stage.getViewport().getWorldWidth() )/16) + (stage.getViewport().getWorldHeight()/20), (11*stage.getViewport().getWorldHeight())/15, ((17*stage.getViewport().getWorldWidth() )/32) - ((7*stage.getViewport().getWorldWidth() )/16)-(stage.getViewport().getWorldHeight()/20), stage.getViewport().getWorldHeight()/20); //bottom bar


            if ( (prefsTotal.getInteger("scoreCount") < 1000) && (!shopPrefs.getBoolean("coloCheck5")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.circle((9*stage.getViewport().getWorldWidth() ) / 12, (5*stage.getViewport().getWorldHeight()) / 6, Gdx.graphics.getWidth() / 50f); //draws circles for fifth upgrade (1,3)
            shapeRenderer.circle((11*stage.getViewport().getWorldWidth() ) / 12, (5*stage.getViewport().getWorldHeight()) / 6, Gdx.graphics.getWidth() / 50f);

            if ( (prefsTotal.getInteger("scoreCount") < 2000) && (!shopPrefs.getBoolean("coloCheck6")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);

            shapeRenderer.circle((2*stage.getViewport().getWorldWidth() )/3, stage.getViewport().getWorldHeight()/3, stage.getViewport().getWorldHeight()/6); //draws circle for last (6th) upgrade (2,2)
            if (shopPrefs.getBoolean("colo6"))
                shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
            else
                shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
            shapeRenderer.rect((2*stage.getViewport().getWorldWidth() )/3 - stage.getViewport().getWorldWidth() /50, stage.getViewport().getWorldHeight()/5, stage.getViewport().getWorldWidth() /25, (2*stage.getViewport().getWorldHeight())/7); //! part
            if ( (prefsTotal.getInteger("scoreCount") < 2000) && (!shopPrefs.getBoolean("coloCheck6")))
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, (StartScreen.customChar.a)/2);
            else
                shapeRenderer.setColor(StartScreen.customChar.r, StartScreen.customChar.g, StartScreen.customChar.b, StartScreen.customChar.a);
            shapeRenderer.rect((2*stage.getViewport().getWorldWidth() )/3 - stage.getViewport().getWorldWidth() /50, stage.getViewport().getWorldHeight()/4, stage.getViewport().getWorldWidth() /25, (stage.getViewport().getWorldHeight())/70); //space between

            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(StartScreen.customObs.r, StartScreen.customObs.g, StartScreen.customObs.b, StartScreen.customObs.a);
            //OUTLINE
            shapeRenderer.line(1,1,stage.getViewport().getWorldWidth() ,1);
            shapeRenderer.line(1,stage.getViewport().getWorldHeight()-1,stage.getViewport().getWorldWidth() ,stage.getViewport().getWorldHeight()-1);
            shapeRenderer.line(1,1,1,stage.getViewport().getWorldHeight()-1);
            shapeRenderer.line(stage.getViewport().getWorldWidth() -1,1,stage.getViewport().getWorldWidth() -1,stage.getViewport().getWorldHeight()-1);

            //INNER LINES
            shapeRenderer.line(stage.getViewport().getWorldWidth() /3, stage.getViewport().getWorldHeight(), stage.getViewport().getWorldWidth() /3, 0);
            shapeRenderer.line(0, (2*stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() , (2*stage.getViewport().getWorldHeight())/3);
            shapeRenderer.line((2*stage.getViewport().getWorldWidth() )/3, stage.getViewport().getWorldHeight(), (2*stage.getViewport().getWorldWidth() )/3, (2*stage.getViewport().getWorldHeight())/3);
            shapeRenderer.line(0, stage.getViewport().getWorldHeight()/3, stage.getViewport().getWorldWidth() /3, stage.getViewport().getWorldHeight()/3);

            //activation rectangles
            shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, stage.getViewport().getWorldHeight()/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, (2*stage.getViewport().getWorldHeight())/3, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            shapeRenderer.rect((4*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth() /15, stage.getViewport().getWorldHeight()/15);
            shapeRenderer.rect((9*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15,(2 * stage.getViewport().getWorldHeight()) / 3, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
            shapeRenderer.rect((14*stage.getViewport().getWorldWidth() )/15, 0, stage.getViewport().getWorldWidth()  / 15, stage.getViewport().getWorldHeight() / 15);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
            drawPoints();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            backPress = true;
        if (backPress) {
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

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        upOneTexture.dispose();
        upTwoTexture.dispose();
        upThreeTexture.dispose();
        upFourTexture.dispose();
        upFiveTexture.dispose();
        upSixTexture.dispose();
        pointsTexture.dispose();
        bitmapFont.dispose();
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
        // TREAT TOUCH.X AND TOUCH.Y AS IF YOU ARE IN Q1 ( (0,0) is in bottom left )
        camera.unproject(touch.set(x, y, 0));

        if ((touch.x < stage.getViewport().getWorldWidth() / 3) && (touch.y > (2*stage.getViewport().getWorldHeight()) / 3) && ( (prefsTotal.getInteger("scoreCount")>=250) || shopPrefs.getBoolean("coloCheck1"))){
            if (shopPrefs.getBoolean("colo")) {
                shopPrefs.putBoolean("colo",false);
                shopPrefs.flush();
            }
            else if (!shopPrefs.getBoolean("colo")){
                shopPrefs.putBoolean("colo", true);
                shopPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck1")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-250);
                    prefsTotal.flush();
                    upOne.remove();
                    shopPrefs.putBoolean("coloCheck1",true);
                    shopPrefs.flush();
                }

            }

            //game.setScreen(new PermUpgradeOne(game));
        }

        if ((touch.x < stage.getViewport().getWorldWidth()  / 3) && (touch.y > (stage.getViewport().getWorldHeight()) / 3) && (touch.y < (2*stage.getViewport().getWorldHeight() / 3)) && ( (prefsTotal.getInteger("scoreCount")>=500) || shopPrefs.getBoolean("coloCheck2"))) {
            //used to determine whether or not the second (2,1) upgrade will be used in the game screen
            if (tempPrefs.getBoolean("tColo")) {
                tempPrefs.putBoolean("tColo", false);
                tempPrefs.flush();
            } else if (!tempPrefs.getBoolean("tColo")) {
                tempPrefs.putBoolean("tColo", true);
                tempPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck2")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-500);
                    prefsTotal.flush();
                    upTwo.remove();
                    shopPrefs.putBoolean("coloCheck2",true);
                    shopPrefs.flush();
                }
            }
            //game.setScreen(new PermUpgradeTwo(game));
        }

        if ( (touch.x < (stage.getViewport().getWorldWidth() )/3) && (touch.y < (stage.getViewport().getWorldHeight()) / 3) && ( (prefsTotal.getInteger("scoreCount")>=500) || shopPrefs.getBoolean("coloCheck3"))) {
            //used to determine whether or not the third (3,1) upgrade will be used in the game screen
            if (shopPrefs.getBoolean("colo3")) {
                shopPrefs.putBoolean("colo3",false);
                shopPrefs.flush();
            }
            else if (!shopPrefs.getBoolean("colo3")){
                shopPrefs.putBoolean("colo3", true);
                shopPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck3")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-500);
                    prefsTotal.flush();
                    upThree.remove();
                    shopPrefs.putBoolean("coloCheck3",true);
                    shopPrefs.flush();
                }
            }
            //game.setScreen(new PermUpgradeThree(game));
        }

        if ((touch.x > stage.getViewport().getWorldWidth()  / 3) && (touch.x < (2*stage.getViewport().getWorldWidth() )/3) && (touch.y > (2*stage.getViewport().getWorldHeight() / 3)) && ( (prefsTotal.getInteger("scoreCount")>=1000) || shopPrefs.getBoolean("coloCheck4"))) {
            //used to determine whether or not the 4th (1,2) upgrade will be used in the game screen
            if (shopPrefs.getBoolean("colo4")) {
                shopPrefs.putBoolean("colo4",false);
                shopPrefs.flush();
            }
            else if (!shopPrefs.getBoolean("colo4")){
                shopPrefs.putBoolean("colo4", true);
                shopPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck4")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-1000);
                    prefsTotal.flush();
                    upFour.remove();
                    shopPrefs.putBoolean("coloCheck4",true);
                    shopPrefs.flush();
                }
            }
            //game.setScreen(new PermUpgradeFour(game));
        }

        if ((touch.x > (2*stage.getViewport().getWorldWidth() ) / 3) && (touch.y > (2*(stage.getViewport().getWorldHeight()) / 3)) && ( (prefsTotal.getInteger("scoreCount")>=1000) || shopPrefs.getBoolean("coloCheck5"))) {
            //used to determine whether or not the fifth (1,3) upgrade will be used in the game screen
            if (shopPrefs.getBoolean("colo5")) {
                shopPrefs.putBoolean("colo5",false);
                shopPrefs.flush();
            }
            else if (!shopPrefs.getBoolean("colo5")){
                shopPrefs.putBoolean("colo5", true);
                shopPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck5")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-1000);
                    prefsTotal.flush();
                    upFive.remove();
                    shopPrefs.putBoolean("coloCheck5",true);
                    shopPrefs.flush();
                }
            }
            //game.setScreen(new PermUpgradeFive(game));
        }

        if ((touch.x > stage.getViewport().getWorldWidth()  / 3) && (touch.y < (2*stage.getViewport().getWorldHeight()/ 3)) && ( (prefsTotal.getInteger("scoreCount")>=2000) || shopPrefs.getBoolean("coloCheck6"))) {
            //used to determine whether or not the 6th (2,3) upgrade will be used in the game screen
            if (shopPrefs.getBoolean("colo6")) {
                shopPrefs.putBoolean("colo6",false);
                shopPrefs.flush();
            }
            else if (!shopPrefs.getBoolean("colo6")){
                shopPrefs.putBoolean("colo6", true);
                shopPrefs.flush();
                if (!shopPrefs.getBoolean("coloCheck6")) {
                    prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")-2000);
                    prefsTotal.flush();
                    upSix.remove();
                    shopPrefs.putBoolean("coloCheck6",true);
                    shopPrefs.flush();
                }
            }
            //game.setScreen(new PermUpgradeSix(game));
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TREAT TOUCH.X AND TOUCH.Y AS IF YOU ARE IN Q1 ( (0,0) is in bottom left )
        camera.unproject(touch.set(x, y, 0));

        if ((touch.x < stage.getViewport().getWorldWidth()  / 3) && (touch.y > (2*stage.getViewport().getWorldHeight()) / 3))
            game.setScreen(new PermUpgradeOne(game));
        if ((touch.x < stage.getViewport().getWorldWidth()  / 3) && (touch.y > (stage.getViewport().getWorldHeight()) / 3) && (touch.y < (2*stage.getViewport().getWorldHeight()) / 3))
            game.setScreen(new PermUpgradeTwo(game));
        if ( (touch.x < (stage.getViewport().getWorldWidth() )/3) && (touch.y < (stage.getViewport().getWorldHeight()) / 3))
            game.setScreen(new PermUpgradeThree(game));
        if ((touch.x > stage.getViewport().getWorldWidth()  / 3) && (touch.x < (2*stage.getViewport().getWorldWidth() )/3) && (touch.y > (2*stage.getViewport().getWorldHeight()) / 3))
            game.setScreen(new com.mygdx.Blitz.PermUpgradeFour(game));
        if ((touch.x > (2*stage.getViewport().getWorldWidth() ) / 3) && (touch.y > (2*stage.getViewport().getWorldHeight()) / 3) )
            game.setScreen(new PermUpgradeFive(game));
        if ((touch.x > stage.getViewport().getWorldWidth()  / 3) && (touch.y < (2*stage.getViewport().getWorldHeight())/ 3) )
            game.setScreen(new com.mygdx.Blitz.PermUpgradeSix(game));
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

