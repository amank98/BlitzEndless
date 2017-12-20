package com.mygdx.Blitz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
	private Camera camera;
	private static final float WORLD_WIDTH = 1920;
	private static final float WORLD_HEIGHT = 1080;
	private static final float COLLISION_RECTANGLE_WIDTH = WORLD_WIDTH;
	private static final float COLLISION_RECTANGLE_HEIGHT = WORLD_HEIGHT  / 24f;
	private static float MAX_SPEED_PER_SECOND = (2*WORLD_HEIGHT  / 5f);
	private static final float WIDTH_OFFSET_LEFT = -COLLISION_RECTANGLE_WIDTH;
	private static float WIDTH_OFFSET_RIGHT = -(WORLD_WIDTH/5);
	private float x = 0;
	private float y = 0;
	
	private static float DISTANCE_BETWEEN_LEFT_AND_RIGHT = -WIDTH_OFFSET_RIGHT;
	private static float OrigDistance = (WORLD_WIDTH/5);
	private final Rectangle floorCollisionRectangle;
	private final Rectangle ceilingCollisionRectangle;
	
	private boolean pointClaimed = false;
	
	public Obstacle() {
		this.x = MathUtils.random(WIDTH_OFFSET_LEFT, WIDTH_OFFSET_RIGHT);
		this.floorCollisionRectangle = new Rectangle(x, y,
				COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
		this.ceilingCollisionRectangle = new Rectangle(x
				+ COLLISION_RECTANGLE_WIDTH + DISTANCE_BETWEEN_LEFT_AND_RIGHT,
				y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
	}

	public Obstacle(float x) {
		this.x = x;
		this.floorCollisionRectangle = new Rectangle(x, y,
				COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
		this.ceilingCollisionRectangle = new Rectangle(x
				+ COLLISION_RECTANGLE_WIDTH + DISTANCE_BETWEEN_LEFT_AND_RIGHT,
				y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
	}

	public static float getDistance() {
		return DISTANCE_BETWEEN_LEFT_AND_RIGHT;
	}

	public void setDistance(float d) {
		DISTANCE_BETWEEN_LEFT_AND_RIGHT = d;
	}

	public static void setSpeed() {
		MAX_SPEED_PER_SECOND += WORLD_HEIGHT/24f;//)50f;
	}

	public static void setSpeed(float x) {
		MAX_SPEED_PER_SECOND =x;
	}

	public static void resetDistanceBetweenLeftAndRight(){
		WIDTH_OFFSET_RIGHT = -(WORLD_WIDTH/5);
		DISTANCE_BETWEEN_LEFT_AND_RIGHT = OrigDistance;
	}
	
	public static void resetSpeed() {
		MAX_SPEED_PER_SECOND = (2*WORLD_HEIGHT / 5f);}

	public static float getSpeed(){return MAX_SPEED_PER_SECOND;}
	
	public void setPosition(float y) {
		this.y = y;
		updateCollisionRectangle();
	}
	
	private void updateCollisionRectangle() {
		floorCollisionRectangle.setY(y);
		ceilingCollisionRectangle.setY(y);
	}
	
	public boolean isMainBallColliding(MainBall mainBall) {
		Circle MainBallCollisionCircle = mainBall.getCollisionCircle();
		return
		Intersector.overlaps(MainBallCollisionCircle,
		ceilingCollisionRectangle) ||
		Intersector.overlaps(MainBallCollisionCircle,
		floorCollisionRectangle);
	}
	
	public boolean isPointColliding(Point point) {
		Circle pointCollisionCircle = point.getCollisionCircle();
		return
		Intersector.overlaps(pointCollisionCircle,
		ceilingCollisionRectangle) ||
		Intersector.overlaps(pointCollisionCircle,
		floorCollisionRectangle);
	}
	
	public boolean isPointClaimed() {
		return pointClaimed;
	}
	
	public void markPointClaimed() {
		pointClaimed = true;
	}
	
	public float getHeight() {
		return COLLISION_RECTANGLE_HEIGHT;
	}

	public void update(float delta) {
		checkDistance();
		setPosition(y + (MAX_SPEED_PER_SECOND * delta));
	}
	
	public float getX() {
		return x;
	}

	public void setX(float n) {x=n;}
	
	public float getY() {
		return y;
	}

	public static void setDistanceBetweenLeftAndRight(float x) {
		WIDTH_OFFSET_RIGHT = -(WORLD_WIDTH/4);
		DISTANCE_BETWEEN_LEFT_AND_RIGHT+=x;
	}

	public static void checkDistance() { //checks update from shop screen to increment only once
		if (DISTANCE_BETWEEN_LEFT_AND_RIGHT > OrigDistance + WORLD_WIDTH/20)
			DISTANCE_BETWEEN_LEFT_AND_RIGHT = OrigDistance + WORLD_WIDTH/20;
	}

	public void drawDebug(ShapeRenderer shapeRenderer) {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);

		checkDistance();
		shapeRenderer.rect(floorCollisionRectangle.x,
		floorCollisionRectangle.y, floorCollisionRectangle.width,
		floorCollisionRectangle.height);
		shapeRenderer.rect(ceilingCollisionRectangle.x,
		ceilingCollisionRectangle.y, ceilingCollisionRectangle.width,
		ceilingCollisionRectangle.height);
	}
}