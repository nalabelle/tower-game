package com.comp380.towergame;

import physics.CollisionDetection;

import com.comp380.towergame.entities.EntityManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

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
		//Removes the Title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Removes the Notification bar.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		//Loads the graphic box
		Log.v(tag, "Opening Surface View");
		this.gameSurfaceView = new GameSurfaceView(this);
		setContentView(this.gameSurfaceView);
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
	
}
