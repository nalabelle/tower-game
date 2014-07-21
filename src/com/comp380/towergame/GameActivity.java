package com.comp380.towergame;

import physics.CollisionDetection;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.comp380.towergame.entities.EntityManager;

public class GameActivity extends Activity {
	private String tag = this.getClass().toString();
	protected 	GameSurfaceView	gameSurfaceView	= null;
	protected 	EntityManager	entityManager	= null;
	protected 	GameThread 		gameThread = null;
	protected	CollisionDetection	collisionDetection = null;
	
	public static int GAME_MAX_WIDTH = 1920;
	public static int GAME_MAX_HEIGHT = 1200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Setup Layout
		//Removes the Title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Removes the Notification bar.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//Save Layout
		FrameLayout game = new FrameLayout(this);
		
		//Loads the graphic box
		Log.v(tag, "Opening Surface View");
		this.gameSurfaceView = new GameSurfaceView(this);
		
		//Add gameSurface
		game.addView(this.gameSurfaceView);
		
		//Add Buttons
		LayoutInflater factory = LayoutInflater.from(this);
		View myView = factory.inflate(R.layout.activity_gameview, null);
		game.addView(myView);
		
		setContentView(game);
		Log.v(tag, "Waiting for Surface View");
		Log.v(tag, "Starting Game Thread");
		this.gameThread = new GameThread(this);
		Log.v(tag, "Starting Entity Manager");
		this.entityManager = new EntityManager(this);
		this.collisionDetection = new CollisionDetection(this);
		this.toggleGameThread(true);
	}

	protected void toggleGameThread(boolean b) {
		Log.v(tag, "Starting Game Thread");
		this.gameThread.setRunning(b);
		this.gameThread.start();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public CollisionDetection getCollisionDetection() {
		return this.collisionDetection;
	}
	
	public void moveLeft(View view) {
		entityManager.getAndy().setX(entityManager.getAndy().getX() - 10);
	}
	
	public void moveRight(View view) {
		entityManager.getAndy().setX(entityManager.getAndy().getX() + 10);
	}
	
	public void moveUp(View view) {
		entityManager.getAndy().setY(entityManager.getAndy().getY() - 10);
	}
	
	public void moveDown(View view) {
		entityManager.getAndy().setY(entityManager.getAndy().getY() + 10);
	}
	
}
