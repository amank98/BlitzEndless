package com.mygdx.Blitz;
/*
	TEMPORARY UPGRADES ARE DEALT WITH HERE, PERMANENT ONES ARE IN START SCREEN
*/
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.mygdx.Blitz.StartScreen.customChar;
import static com.mygdx.Blitz.StartScreen.customObs;

public class MultiGameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1920;
    private static final float WORLD_HEIGHT = 1080;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private SpriteBatch batch;

    private boolean obsStop = false; //handles the death animation; removes scoreImage, pause, obstacles, points, and user input

    private MainBall mainBall; // = new MainBall();
    private HashMap<String, MainBall> players;
    private final Game game;

    private Array<com.mygdx.Blitz.Obstacle> Obstacles; //= new Array<com.mygdx.Blitz.Obstacle>();
    private static final float GAP_BETWEEN_Obstacles = (7*WORLD_HEIGHT) / 16;
    private static final float GAP_BETWEEN_POINTS = WORLD_HEIGHT / 3;
    private static final float EXTRA = WORLD_HEIGHT / 12f;
    private static int score = 0;
    private static int imageScore = 0;
    private static int finalScore = 0;
    private int count = 1;
    private boolean checkSpeed = false;
    private Stage stage; //pause button, play button, scoreImage, menu, blitz image

    private static Preferences prefs;
    private static Preferences charPrefs;
    private Preferences prefsTotal;
    private Preferences tempPrefs;
    private Preferences shopPrefs;
    private boolean magnet = true;

    private boolean imageCheck = false;
    private Vector3 touch = new Vector3();

    private Array<com.mygdx.Blitz.Point> points = new Array<com.mygdx.Blitz.Point>();

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFontIS;
    private GlyphLayout glyphLayoutIS;


    private float obsX;

    //150
    //100

    private Texture scoreTexture;
    private Image scoreImage;

    private Socket socket;

    public MultiGameScreen(Game game) {
        if (mainBall != null)
            restart();
        this.game = game;

        prefs = Gdx.app.getPreferences("color"); //obstacle colors
        charPrefs = Gdx.app.getPreferences ("charColor"); //character colors

        tempPrefs = Gdx.app.getPreferences("tColo");//represents colo2
        if (!tempPrefs.contains("tColo")) //shop upgrade one
            tempPrefs.putBoolean("tColo", false);

        shopPrefs = Gdx.app.getPreferences("colo"); // ONLY NEED TO ACCESS ONE (colo) TO GET ACCESS TO ALL (colo-colo3) !!!!
        if (!shopPrefs.contains("colo3"))
            shopPrefs.putBoolean("colo3", false);

        prefs = Gdx.app.getPreferences("highScore");
        if (!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        if (!prefs.contains("adCount"))
            prefs.putInteger("adCount",1);

        prefs.putInteger("adCount", prefs.getInteger("adCount")+1);
        prefs.flush();
        prefsTotal = Gdx.app.getPreferences("scoreCount");
        if (!prefsTotal.contains("scoreCount"))
            prefsTotal.putInteger("scoreCount", 0);

    }

    public MultiGameScreen(Game game, int sc) {
        if (mainBall != null)
            restart();
        score = sc;

        this.game = game;

        prefs = Gdx.app.getPreferences("color"); //obstacle colors
        charPrefs = Gdx.app.getPreferences ("charColor"); //character colors

        tempPrefs = Gdx.app.getPreferences("tColo");//represents colo2
        if (!tempPrefs.contains("tColo")) //shop upgrade one
            tempPrefs.putBoolean("tColo", false);

        shopPrefs = Gdx.app.getPreferences("colo"); // ONLY NEED TO ACCESS ONE (colo) TO GET ACCESS TO ALL (colo-colo3) !!!!
        if (!shopPrefs.contains("colo3"))
            shopPrefs.putBoolean("colo3", false);

        prefs = Gdx.app.getPreferences("highScore");
        if (!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        if (!prefs.contains("adCount"))
            prefs.putInteger("adCount",1);

        prefs.putInteger("adCount", prefs.getInteger("adCount")+1);
        prefs.flush();

        prefsTotal = Gdx.app.getPreferences("scoreCount");
        if (!prefsTotal.contains("scoreCount"))
            prefsTotal.putInteger("scoreCount", 0);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        players = new HashMap<String, MainBall>();
        connectSocket();
        configSocketEvents();

        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();
        bitmapFontIS = new BitmapFont();
        glyphLayoutIS = new GlyphLayout();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        setColor();

        scoreTexture = new Texture(Gdx.files.internal("GScore.png")); //65
        scoreImage = new Image(scoreTexture);
        scoreImage.setPosition(scoreTexture.getWidth() / 2, WORLD_HEIGHT - scoreTexture.getHeight()/2, Align.center);
        stage.addActor(scoreImage);

    }

    public void connectSocket() {
        try{
            socket = IO.socket("http://192.168.1.11:8080");
            socket.connect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO","connected");
                mainBall = new MainBall();
                mainBall.setPosition(stage.getViewport().getWorldWidth() / 2, WORLD_HEIGHT / (1/4));
                //Obstacles = new Array<com.mygdx.Blitz.Obstacle>();
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO","My ID: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO","Error getting ID");
                };
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO","New Player Connected: " + id);
                    MainBall temp = new MainBall();
                    temp.setPosition(stage.getViewport().getWorldWidth() / 2, WORLD_HEIGHT / (1/4));
                    temp.setPosition(MathUtils.clamp(mainBall.getX(), mainBall.getRadius(), stage.getViewport().getWorldWidth() - mainBall.getRadius()),
                            MathUtils.clamp(mainBall.getY(), mainBall.getRadius(), WORLD_HEIGHT- mainBall.getRadius()));
                    players.put(id, temp);

                } catch (JSONException e) {
                    Gdx.app.log("SocketIO","Error getting new player ID");
                };
                if ((players.size() > 0) && (Obstacles == null))
                    Obstacles = new Array<com.mygdx.Blitz.Obstacle>();
            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    players.remove(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO","Error connecting players");
                };
            }
        }).on("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                try {
                    for (int i = 0; i< objects.length(); i++){
                        MainBall temp = new MainBall();
                        temp.setPosition( ((Double) objects.getJSONObject(i).getDouble("x")).floatValue(),
                                          ((Double) objects.getJSONObject(i).getDouble("y")).floatValue());
                        players.put(objects.getJSONObject(i).getString("id"), temp);
                    }
                } catch (Exception e) {
                    Gdx.app.log("SocketIO","Error getting players");
                };
                if ((players.size() > 0) && (Obstacles == null))
                    Obstacles = new Array<com.mygdx.Blitz.Obstacle>();
            }
        }).on("playerMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String playerId = data.getString("id");
                    Double x = data.getDouble("x");
                    Double y = data.getDouble("y");
                    if (players.get(playerId) != null) {
                        players.get(playerId).setPosition(x.floatValue(), y.floatValue());
                    }
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO","Error moving player");
                };
            }
        }).on("getObsX", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    obsX = ((Double)data.getDouble("obsSpace")).floatValue();

                } catch (JSONException e) {
                    Gdx.app.log("SocketIO","Error getting obsX");
                };
            }
        });
    }

    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        bitmapFont.dispose();
        stage.dispose();
        scoreTexture.dispose();
        bitmapFontIS.dispose();
    }

    private void createNewFlower() {
        com.mygdx.Blitz.Obstacle newObstacle;
        socket.emit("getObsX");
        if (obsX ==0) {
            obsX = -778;
            newObstacle = new com.mygdx.Blitz.Obstacle(obsX);
        }
        else
            newObstacle = new com.mygdx.Blitz.Obstacle(obsX);
        System.out.println("ASDF: "+obsX);
        newObstacle.setPosition(-EXTRA);
        if (!obsStop)
            Obstacles.add(newObstacle);
    }

    private void createNewPoint() {
        com.mygdx.Blitz.Point newPoint = new com.mygdx.Blitz.Point();
        float x = MathUtils.random(stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/20) + stage.getViewport().getWorldWidth()/20;
        newPoint.setPosition(x,-2* com.mygdx.Blitz.Point.getRadius());
        if (!obsStop)
            points.add(newPoint);
    }

    private void checkIfNewFlowerIsNeeded() {
        if (Obstacles.size == 0) {
            createNewFlower();
        }
        else {
            com.mygdx.Blitz.Obstacle obstacle = Obstacles.peek();
            if (obstacle.getY() > WORLD_HEIGHT - GAP_BETWEEN_Obstacles) {
                createNewFlower();
            }
        }
    }

    private void checkIfNewPointIsNeeded() {
        if (points.size == 0) {
            createNewPoint();
        }
        else {
            com.mygdx.Blitz.Point point = points.peek();
            if ( (point.getY() > WORLD_HEIGHT - GAP_BETWEEN_POINTS) && (!point.getPlace())) {
                createNewPoint();
            }
        }
    }

    private void removeObstaclesIfPassed() {
        if (Obstacles.size > 0) {
            com.mygdx.Blitz.Obstacle firstObstacle = Obstacles.first();
            if (firstObstacle.getY() > WORLD_HEIGHT) { //dont want flower to disappear instantly
                Obstacles.removeValue(firstObstacle, true);
            }
        }
    }

    private void removePointsIfPassed() {
        if (points.size > 0) {
            com.mygdx.Blitz.Point firstPoint = points.first();
            if (firstPoint.getY() - firstPoint.getRadius() > WORLD_HEIGHT) {
                points.removeValue(firstPoint, true);
            }
        }
    }

    public void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.getData().setScale(3, 3);
        bitmapFont.draw(batch, scoreAsString, (4*scoreTexture.getWidth())/4, WORLD_HEIGHT - scoreTexture.getHeight()/3);
    }


    public static void setColor() {
        if (prefs.getString("color").equals("GOLDENROD")) {
            customObs = Color.GOLDENROD;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(4);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(22);
        }
        else if (prefs.getString("color").equals("RED")) {
            customObs = Color.RED;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(4);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(13);
        }
        else if (prefs.getString("color").equals("PURPLE")) {
            customObs = Color.PURPLE;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(4);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(4);
        }
        else if(prefs.getString("color").equals("CYAN")) {
            customObs = Color.CYAN;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(13);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(22);
        }
        else if(prefs.getString("color").equals("FIREBREAK")) {
            customObs = Color.FIREBRICK;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(13);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(13);
        }
        else if(prefs.getString("color").equals("ROYAL")) {
            customObs = Color.ROYAL;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(13);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(4);
        }
        else if(prefs.getString("color").equals("WHITE")) {
            customObs = Color.WHITE;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(22);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(22);
        }
        else if(prefs.getString("color").equals("LIME")) {
            customObs = Color.LIME;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(22);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(13);
        }
        else if (prefs.getString("color").equals("GRAY")) {
            customObs = Color.GRAY;
            com.mygdx.Blitz.ObstacleColorScreen.setColCol(22);
            com.mygdx.Blitz.ObstacleColorScreen.setColRow(4);
        }

        if (charPrefs.getString("charColor").equals("GOLDENROD")) {
            customChar = Color.GOLDENROD;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(1);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(5);
        }
        else if (charPrefs.getString("charColor").equals("RED")) {
            customChar = Color.RED;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(1);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(3);
        }
        else if (charPrefs.getString("charColor").equals("PURPLE")) {
            customChar = Color.PURPLE;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(1);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(1);
        }
        else if(charPrefs.getString("charColor").equals("CYAN")) {
            customChar = Color.CYAN;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(3);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(5);
        }
        else if(charPrefs.getString("charColor").equals("FIREBREAK")) {
            customChar = Color.FIREBRICK;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(3);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(3);
        }
        else if(charPrefs.getString("charColor").equals("ROYAL")) {
            customChar = Color.ROYAL;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(3);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(1);
        }
        else if(charPrefs.getString("charColor").equals("WHITE")) {
            customChar = Color.WHITE;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(5);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(5);
        }
        else if(charPrefs.getString("charColor").equals("LIME")) {
            customChar = Color.LIME;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(5);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(3);
        }
        else if (charPrefs.getString("charColor").equals("GRAY")) {
            customChar = Color.GRAY;
            com.mygdx.Blitz.CharacterColorScreen.setColCol(5);
            com.mygdx.Blitz.CharacterColorScreen.setColRow(1);
        }
    }

    @Override
    public void render(float delta) {
        clearScreen();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);

            if (mainBall != null)
                mainBall.drawDebug(shapeRenderer);

            shapeRenderer.setColor(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, Color.LIGHT_GRAY.a);
            for (HashMap.Entry<String, MainBall> entry : players.entrySet())
                entry.getValue().drawDebug(shapeRenderer);

            if (Obstacles != null) {
                shapeRenderer.setColor(customObs.r, customObs.g, customObs.b, customObs.a);
                for (com.mygdx.Blitz.Obstacle obstacle : Obstacles) {
                    obstacle.drawDebug(shapeRenderer);
                }
            }
            shapeRenderer.setColor(customChar.r, customChar.g, customChar.b, customChar.a);

            for (com.mygdx.Blitz.Point point : points) {
                if ((point.getHit() == false) && (point.getHere() == false) && (!point.getSmashed())) //&& (!point.getPlace()))
                    point.drawDebug(shapeRenderer);
            }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(stage.getViewport().getWorldWidth()/12, (7*WORLD_HEIGHT) /8, stage.getViewport().getWorldWidth()/6, (5*WORLD_HEIGHT)/6, stage.getViewport().getWorldWidth()/8,
                WORLD_HEIGHT/20, 10, 10, 45);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.projection);
            batch.setTransformMatrix(camera.view);
            batch.begin();
            if (!obsStop)
                drawScore();
        batch.end();

        update(delta);
        if (obsStop) {
            scoreImage.remove();
            if (mainBall !=null)
                setCircles();
            if ( (mainBall != null) && (mainBall.getRadius() >= (WORLD_HEIGHT/2) - 20) &&
                    (mainBall.getX() < ((stage.getViewport().getWorldWidth()/2) + 7) ) &&
                    (mainBall.getX() > ((stage.getViewport().getWorldWidth()/2) - 7)) ) {
                game.setScreen(new DeadScreen(game));
                dispose();
            }
        }

        if (Obstacles != null) {
            if (checkForCollision()) {
                socket.disconnect();
                Obstacles.clear();
                points.clear();
                obsStop = true;
                scoreImage.remove();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            prefsTotal.putInteger("scoreCount", prefsTotal.getInteger("scoreCount")+ score);
            prefsTotal.flush();
            socket.disconnect();
            game.setScreen(new StartScreen(game));
            dispose();
        }

        stage.act(delta);
        stage.draw();
    }

    private void update(float delta) {
        if (mainBall != null) {
            mainBall.update();

            if ((Gdx.input.isTouched()) && (!obsStop)) {
                JSONObject data = new JSONObject();
                try {
                    data.put("x", mainBall.getX());
                    data.put("y", mainBall.getY());
                    socket.emit("playerMoved", data);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error sending update data");
                }
            }

            if ((Gdx.input.isTouched()) && (!obsStop)) {
                //check for BL!TZ HIT
                if ((Gdx.input.getX() < Gdx.graphics.getWidth() / 2))
                    mainBall.flyLeft();
                else if ((Gdx.input.getX() > Gdx.graphics.getWidth() / 2))
                    mainBall.flyRight();
            }

            blockMainBallLeavingTheWorld();


            //check to make sure not too small
            if (mainBall.getRadius() < WORLD_WIDTH / 70f)
                mainBall.setCollisionRadius(WORLD_WIDTH / 70f);

            //blockPointsOffScreen();
            //updatePoints(delta);

            finalScore(score);

            if (count % 5 == 0) {
                mainBall.setSpeed();
                com.mygdx.Blitz.Obstacle.setSpeed();
                com.mygdx.Blitz.Point.setSpeed();
                checkSpeed = false;
                count = 1;
            }

            if (Obstacles != null)
                checkForPlacement();
            checkToShowPoint();

            if (imageScore > 0)
                imageCheck = false;
        }
        if (Obstacles!= null) {
            com.mygdx.Blitz.Obstacle.checkDistance();
            updateObstacles(delta);
            updateScore();
        }

    }

    public void setCircles() {
        if (mainBall.getY() > WORLD_HEIGHT/2)
            mainBall.setY(mainBall.getY()-7);
        if ((mainBall.getRadius()) <= WORLD_HEIGHT / 2 - 20f)
            mainBall.setCollisionRadius(mainBall.getRadius() + 7f);
        if (mainBall.getX() < ((stage.getViewport().getWorldWidth() /2) - 6))
            mainBall.setX(mainBall.getX()+7);
        else if (mainBall.getX() > ((stage.getViewport().getWorldWidth()/2) + 6))
            mainBall.setX(mainBall.getX()-7);

    }

    private void updateScore() {
        com.mygdx.Blitz.Obstacle obstacle;
        if (!obsStop) {
            obstacle = Obstacles.first();
            if ((obstacle.getY() + obstacle.getHeight() > mainBall.getY() - mainBall.getRadius()) && (!obstacle.isPointClaimed())) {
                obstacle.markPointClaimed();
                mainBall.setCollisionRadius(mainBall.getRadius() + (stage.getViewport().getWorldHeight()/390f));
                score++;
                imageScore++;
                count++;
            }
        }
    }


    public void finalScore(int score) {
        finalScore = score;
    }

    public static int getFinalScore() {
        return finalScore;
    }

    private boolean checkForCollision() {
        for (com.mygdx.Blitz.Obstacle obstacle : Obstacles) {
            if (obstacle.isMainBallColliding(mainBall)) {
                return true;
            }
        }
        return false;
    }

    private void checkToShowPoint() {
        for (com.mygdx.Blitz.Point point : points) {
            if (point.isMainBallColliding(mainBall))
                point.setSmashed(true);
            else
                point.setSmashed(false);
        }

    }

    private void checkForPlacement() {
        for (com.mygdx.Blitz.Obstacle obstacle : Obstacles) {
            for (int x=0; x<points.size; x++) {
                if (obstacle.isPointColliding(points.get(x))) {//regular for loop for index
                    //points.get(x).setPlace(true); //true means they are colliding
                    points.removeIndex(x);
                }
            }
        }
    }

    private void restart() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        mainBall.setPosition(stage.getViewport().getWorldWidth() / 2, WORLD_HEIGHT / (1/4));
        Obstacles.clear();
        points.clear();
        score = 0;
        mainBall.setCollisionRadius(WORLD_WIDTH / 30f);
        count = 1;

        mainBall.resetSpeed();
        com.mygdx.Blitz.Obstacle.resetSpeed();
        com.mygdx.Blitz.Point.resetSpeed();
    }

    private void updateObstacles(float delta) {
        for (com.mygdx.Blitz.Obstacle obstacle : Obstacles) {
            obstacle.update(delta);
        }
        checkIfNewFlowerIsNeeded();
        removeObstaclesIfPassed();
    }

    private void updatePoints(float delta) {
        for (com.mygdx.Blitz.Point point : points) {
            point.update(delta);
        }
        checkIfNewPointIsNeeded();
        removePointsIfPassed();
    }

    private void blockMainBallLeavingTheWorld() {
        mainBall.setPosition(MathUtils.clamp(mainBall.getX(), mainBall.getRadius(), stage.getViewport().getWorldWidth() - mainBall.getRadius()),
                MathUtils.clamp(mainBall.getY(), mainBall.getRadius(), WORLD_HEIGHT- mainBall.getRadius()));
        //bounds the movement to a lower and upper limit
    }

    private void blockPointsOffScreen() {
        for (com.mygdx.Blitz.Point point : points) {
            point.setPosition(MathUtils.clamp(point.getX(), point.getRadius(), stage.getViewport().getWorldWidth()-point.getRadius()), point.getY());
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
