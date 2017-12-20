package com.mygdx.Blitz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;

public class Point {
	private Camera camera;
	private static final float WORLD_WIDTH = 1920;
	private static final float WORLD_HEIGHT = 1080;
	private static float COLLISION_RADIUS = WORLD_WIDTH / 60;
	private final Circle collisionCircle;
	private float x = 0;
	private float y = COLLISION_RADIUS;

	private static float MAX_SPEED_PER_SECOND = (2*WORLD_HEIGHT / 5); //to start at 20 points speed
	private boolean hit = false;
	private boolean place = false;
	private boolean smashed = false;
	
	public Point() {
		this.x = MathUtils.random(WORLD_WIDTH - 4*COLLISION_RADIUS) + 2*COLLISION_RADIUS;
		collisionCircle = new Circle(x,y,COLLISION_RADIUS);
	}

	public boolean getSmashed() {
		return smashed;
	}

	public void setSmashed(boolean x) {
		smashed = x;
	}

	public void drawDebug(ShapeRenderer shapeRenderer) {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);
		shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		updateCollisionCircle();
	}
	
	public static void setSpeed() {
		MAX_SPEED_PER_SECOND += WORLD_HEIGHT/24f;
	}

	public static void setSpeed(float x) {
		MAX_SPEED_PER_SECOND =x;
	}

	public static float getSpeed() {return MAX_SPEED_PER_SECOND;}
	
	public static void resetSpeed() {
		MAX_SPEED_PER_SECOND = (2*WORLD_HEIGHT / 5);
	}
	
	private void updateCollisionCircle() {
		collisionCircle.setX(x);
		collisionCircle.setY(y);
	}

	public void update(float delta) {
		setPosition(x,y + (MAX_SPEED_PER_SECOND * delta));
	}

	public float getX() {
		return x;
	}

	public void setX(float n) { x=n; }
	
	public float getY() {
		return y;
	}

	public Circle getCollisionCircle() {
		return collisionCircle;
	}

	public static float getRadius() {
		return COLLISION_RADIUS;
	}
	
	public boolean isMainBallColliding(MainBall mainBall) {
		Circle MainBallCollisionCircle = mainBall.getCollisionCircle();
		if (Intersector.overlaps(MainBallCollisionCircle,collisionCircle)){
			hit = true;
			mainBall.setCollisionRadius(mainBall.getRadius()- 0.5f);
		}
		return
		Intersector.overlaps(MainBallCollisionCircle,collisionCircle);
	}

	public boolean getPlace() {
		return place;
	}
	
	public boolean getHere() {
		return place;
	}
	
	public boolean getHit() {
		return hit;
	}
}
