package com.mygdx.Blitz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.json.JSONException;
import org.json.JSONObject;

public class MainBall {
	private Camera camera;
	private final float WORLD_WIDTH = 1920;
	private final float WORLD_HEIGHT = 1080;
	private float COLLISION_RADIUS = WORLD_WIDTH / 30f;
	private final Circle collisionCircle;
	private float x = WORLD_WIDTH/2;
	private float y = WORLD_HEIGHT/2;
	private float FLY_ACCEL = (Gdx.graphics.getWidth() / 175f) + (1.7f*5); //to start at 20 points speed
	private float ySpeed = 0;
	
	public MainBall() {
		collisionCircle = new Circle(x,y,COLLISION_RADIUS);
	}

	public void drawDebug(ShapeRenderer shapeRenderer) {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);
		shapeRenderer.circle(collisionCircle.x, collisionCircle.y, COLLISION_RADIUS);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		updateCollisionCircle();
	}

	public void setSpeed() {
		FLY_ACCEL+=(Gdx.graphics.getWidth()/1100f);
	}
	
	public void resetSpeed() {
		FLY_ACCEL = (Gdx.graphics.getWidth() / 100f);
	}
	
	private void updateCollisionCircle() {
		collisionCircle.setX(x);
		collisionCircle.setY(y);
	}
	
	public void setCollisionRadius(float radius) {
		COLLISION_RADIUS = radius;
		collisionCircle.set(x,y,radius);
	}

	public void resetCollisionRadius() {
		COLLISION_RADIUS = WORLD_WIDTH / 30f;
		collisionCircle.set(x,y,COLLISION_RADIUS);
	}
	
	public void update() {
		setPosition(x, y);
	}

	public void flyLeft() {
		ySpeed = FLY_ACCEL;
		setPosition(x-ySpeed, y);
	}
	
	public void flyRight() {
		ySpeed = FLY_ACCEL;
		setPosition(x+ySpeed, y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x=x;
	}

	public void setY(float y) {
		this.y=y;
	}

	public Circle getCollisionCircle() {
		return collisionCircle;
	}

	public float getRadius() {
		return COLLISION_RADIUS;
	}
}
