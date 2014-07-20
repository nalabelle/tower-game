package com.comp380.towergame;

import com.comp380.towergame.entities.Andy;
import com.comp380.towergame.entities.EntityManager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	private String tag = this.getClass().toString();
	protected GameSurfaceView	gameSurfaceView	= null;
	private EntityManager	entityManager	= null;
	protected GameThread gameThread = null;

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
		this.toggleGameThread(true);
	}
	
	//Temporary
	public Andy getAndy() {
		return this.entityManager.getAndy();
	}
	
	//Temporary texture loader
	public Bitmap getAndyTexture() {
		return BitmapFactory.decodeResource(this.getResources(), R.drawable.player_jump);
	}

	protected void toggleGameThread(boolean b) {
		Log.v(tag, "Starting Game Thread");
		this.gameThread.setRunning(b);
		this.gameThread.start();
	}
	
}
