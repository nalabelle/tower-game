package com.comp380.towergame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
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

import com.comp380.towergame.background.Background;
import com.comp380.towergame.background.Levels;
import com.comp380.towergame.background.TileEngine;
import com.comp380.towergame.entities.EntityManager;
import com.comp380.towergame.entities.Flame;
import com.comp380.towergame.entities.Goat;
import com.comp380.towergame.physics.CollisionDetection;
////temp

public class GameActivity extends Activity {
	private String tag = this.getClass().toString();
	protected 	GameSurfaceView	gameSurfaceView	= null;
	protected 	EntityManager	entityManager	= null;
	protected 	GameThread 		gameThread = null;
	protected	CollisionDetection	collisionDetection = null;
	protected Background[][] backgroundArray = null;
	
	public final boolean DEV_MODE = true;
	//temp
	protected TileEngine tileEngine;
	protected Levels levels;
	
	private SoundManager soundEffects = null;
	private MediaPlayer gameMusic = null;

	public static int GAME_MAX_WIDTH = 1920;
	public static int GAME_MAX_HEIGHT = 1200;
	public static int GRAVITY_SPEED = 15;

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
		this.gameSurfaceView.setKeepScreenOn(true);
		
		//Add gameSurface
		game.addView(this.gameSurfaceView);
		
		//Add Buttons
		LayoutInflater factory = LayoutInflater.from(this);
		View myView = null;
		
		if(true){
			myView = factory.inflate(R.layout.defaultcontrols, null);
		}
		else {
			myView = factory.inflate(R.layout.activity_gameview, null);
		}
		
		game.addView(myView);
		setContentView(game);
		Log.v(tag, "Waiting for Surface View");
		Log.v(tag, "Starting Game Thread");
		Log.v(tag, "Starting Entity Manager");
		this.entityManager = new EntityManager(this);
		this.collisionDetection = new CollisionDetection(this);
		this.backgroundArray = new Background[6][3];
		
		
		for(int i = 0; i < 6; i++) //init bg, move to game thread when level manager is done
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j] = new Background(this,i*400,j*400,1);
		
		
		levels = new Levels(this, 1);
		this.tileEngine = new TileEngine(this, levels.getLevel(),levels.getlevelLength());
		
		soundEffects = new SoundManager(this, 1);
		gameMusic = MediaPlayer.create(this, R.raw.music_level_1);
		gameMusic.setLooping(true);
		
	    if(true) {
	    	ImageButton left = (ImageButton) findViewById(R.id.leftButton);
		    touchButton(left.getId(), com.comp380.towergame.entities.Andy.MoveDirection.LEFT);
		    
		    ImageButton right = (ImageButton) findViewById(R.id.rightButton);
		    touchButton(right.getId(), com.comp380.towergame.entities.Andy.MoveDirection.RIGHT);
		    
		    ImageButton jump = (ImageButton) findViewById(R.id.jump);
		    touchButton(jump.getId(), com.comp380.towergame.entities.Andy.MoveDirection.JUMP);
		    
		    ImageButton spawn = (ImageButton) findViewById(R.id.spawn);
		    spawnButton(spawn.getId());
		    
		    ImageButton fire = (ImageButton) findViewById(R.id.fire);
		    spawnButton(fire.getId());
	    }
	    else {
	    	ImageButton jump = (ImageButton) findViewById(R.id.jump);
		    touchButton(jump.getId(), com.comp380.towergame.entities.Andy.MoveDirection.JUMP);
		    
		    ImageButton upRight = (ImageButton) findViewById(R.id.upRight);
		    touchButton(upRight.getId(), com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_UP_RIGHT);
	    	
		    ImageButton right = (ImageButton) findViewById(R.id.rightButton);
		    touchButton(right.getId(), com.comp380.towergame.entities.Andy.MoveDirection.RIGHT);
		    
		    ImageButton downRight = (ImageButton) findViewById(R.id.downRight);
		    touchButton(downRight.getId(), com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_DOWN_RIGHT);
		    
		    ImageButton down = (ImageButton) findViewById(R.id.down);
		    touchButton(down.getId(), com.comp380.towergame.entities.Andy.MoveDirection.DOWN);
		    
		    ImageButton downLeft = (ImageButton) findViewById(R.id.downLeft);
		    touchButton(downLeft.getId(), com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_DOWN_LEFT);
		    
		    ImageButton left = (ImageButton) findViewById(R.id.leftButton);
		    touchButton(left.getId(), com.comp380.towergame.entities.Andy.MoveDirection.LEFT);
		    
		    ImageButton upLeft = (ImageButton) findViewById(R.id.upLeft);
		    touchButton(upLeft.getId(), com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_UP_LEFT);
		    
		    ImageButton spawn = (ImageButton) findViewById(R.id.spawn);
		    spawnButton(spawn.getId());
		    
		    ImageButton fire = (ImageButton) findViewById(R.id.fire);
		    spawnButton(fire.getId());
	    }
		
		
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public SoundManager getSoundEffects(){
		return soundEffects;
	}

	public CollisionDetection getCollisionDetection() {
		return this.collisionDetection;
	}
	

	public void toggleGameThread(boolean b) {
		this.gameThread = new GameThread(this);
		gameThread.setName("Game Thread");
		Log.v(tag, "Game Thread created");
		if(b){
			Log.v(tag, "Starting Game Thread");
			this.gameThread.start();
			this.gameThread.setRunning(b);
		}
	}
	
	@Override
	public void onPause() {
		if (gameThread != null){
			try {
				gameThread.setRunning(false);
				gameThread.interrupt();				
				soundEffects.autoPause();
				Log.v(tag, "Game Thread Interrupted");
			} catch (Exception e) {
				Log.v(tag, "Thread interrupt failed");
			}
		}
		gameMusic.pause();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.toggleGameThread(true);
		soundEffects.autoResume();
		gameMusic.start();		
	}

	@Override
	protected void onDestroy() {
		if (gameMusic != null){
			gameMusic.release();
		}
		soundEffects.release();
		super.onDestroy();
	}

	public TileEngine getTileManager() {
		return this.getTileEngine();
	}
	
	public int scalingFactor() {
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.badguy);
		return bm.getHeight() / 90;
	}
        

	public TileEngine getTileEngine() {
		return tileEngine;
	}
	
	private void touchButton(final int id, final com.comp380.towergame.entities.Andy.MoveDirection direction) {
        Log.v(tag, "touchy");
		final int sleep = 50;
		final ImageButton left = (ImageButton) findViewById(id);
        left.setOnTouchListener(new View.OnTouchListener() {
                private Handler handlr;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                        Rect rect = new Rect();
                        left.getHitRect(rect);
                        float x = event.getX() + rect.left;
                        float y = event.getY() + rect.top;
           
                        switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                                if (handlr != null) return true;
                                handlr = new Handler();
                                if(id == R.id.fire || id == R.id.spawn) {
                                	handlr.postDelayed(buttonAction, sleep*10);
                                }
                                handlr.postDelayed(buttonAction, sleep);
                                break;
                        case MotionEvent.ACTION_UP:
                                if (handlr == null) return true;
                                handlr.removeCallbacks(buttonAction);
                                handlr = null;
                                break;
                        case MotionEvent.ACTION_MOVE:
                                if (handlr == null) return true;
                                if(!rect.contains((int) x, (int) y)) {
                                        handlr.removeCallbacks(buttonAction);
                                        handlr = null;
                                        break;
                                }
                        }
                        return false;
                }
                Runnable buttonAction = new Runnable() {
                        @Override
                        public void run() {
                                if (entityManager.getAndy() == null ) return;
                                
                                switch (direction) {
                                case RIGHT:
                                	entityManager.getAndy().onMoveEvent(direction);
                                	handlr.postDelayed(this, sleep);
                                	break;
                                case JUMP:
                                	entityManager.getAndy().onMoveEvent(direction);
                            		handlr.postDelayed(this, sleep*10);
                            		break;
                                default: 
                                		entityManager.getAndy().onMoveEvent(direction);
                                		handlr.postDelayed(this, sleep);
                                		break;
                                }
                        }
                };
        });
	}
	
	private void spawnButton(final int id) {
        Log.v(tag, "spawny");
		final int sleep = 50;
		final ImageButton left = (ImageButton) findViewById(id);
        left.setOnTouchListener(new View.OnTouchListener() {
                private Handler handlr;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                        Rect rect = new Rect();
                        left.getHitRect(rect);
                        float x = event.getX() + rect.left;
                        float y = event.getY() + rect.top;
           
                        switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                                if (handlr != null) return true;
                                handlr = new Handler();
                                handlr.postDelayed(buttonAction, sleep*10);
                                break;
                        case MotionEvent.ACTION_UP:
                                if (handlr == null) return true;
                                handlr.removeCallbacks(buttonAction);
                                handlr = null;
                                break;
                        case MotionEvent.ACTION_MOVE:
                                if (handlr == null) return true;
                                if(!rect.contains((int) x, (int) y)) {
                                        handlr.removeCallbacks(buttonAction);
                                        handlr = null;
                                        break;
                                }
                        }
                        return false;
                }
                Runnable buttonAction = new Runnable() {
                        @Override
                        public void run() {
                                if (entityManager.getAndy() == null ) return;
                                                               	
                                if (id == R.id.fire){
                                	entityManager.getAll().add(new Flame(entityManager, 
                    						BitmapFactory.decodeResource(getResources(), R.drawable.flame)));
                    				handlr.postDelayed(this, sleep*10);
                    			
                                	}
                                	
                               if (id == R.id.spawn) {
                                	entityManager.getAll().add(new Goat(entityManager, 
                    						BitmapFactory.decodeResource(getResources(), R.drawable.badguy)));
                    				handlr.postDelayed(this, sleep*10);
                               }
                        }
                };
        });
	}
	
}
