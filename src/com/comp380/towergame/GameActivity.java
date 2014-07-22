package com.comp380.towergame;

import physics.CollisionDetection;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.comp380.towergame.entities.EntityManager;
import com.comp380.towergame.entities.EvilGuy;

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
		
		wireButtons();
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
	
	public void wireButtons() {
		final int mv = 20;
		final int sleep = 50;
		
		final ImageButton leftButton = (ImageButton) findViewById(R.id.leftButton);
		leftButton.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					entityManager.getAndy().setX(entityManager.getAndy().getX() - mv);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		//Right Button 
		final ImageButton rightButton = (ImageButton) findViewById(R.id.rightButton);
		rightButton.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				case MotionEvent.ACTION_OUTSIDE:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					entityManager.getAndy().setX(entityManager.getAndy().getX() + mv);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton jump = (ImageButton) findViewById(R.id.jump);
		jump.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					entityManager.getAndy().setY(entityManager.getAndy().getY() - mv);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton down = (ImageButton) findViewById(R.id.down);
		down.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					entityManager.getAndy().setY(entityManager.getAndy().getY() + mv);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton spawn = (ImageButton) findViewById(R.id.spawn);
		spawn.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep*20);
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					entityManager.getAll().add(new EvilGuy(BitmapFactory.decodeResource(getResources(), R.drawable.badguy)));
					handlr.postDelayed(this, sleep*20);
				}
			};
		});
	}

	@Override
	protected void onPause() {
		if (gameThread != null){
			try {
				gameThread.interrupt();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onPause();
	}
	
	
}
