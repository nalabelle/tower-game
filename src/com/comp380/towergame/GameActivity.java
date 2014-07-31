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
import com.comp380.towergame.entities.Andy.MoveDirection;
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
	

	private MediaPlayer goatBleet = null;
	private MediaPlayer goatDeath = null;

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
		//View myView = factory.inflate(R.layout.activity_gameview, null);
		View myView = factory.inflate(R.layout.defaultcontrols, null);
		game.addView(myView);
		
		setContentView(game);
		Log.v(tag, "Waiting for Surface View");
		Log.v(tag, "Starting Game Thread");
		this.gameThread = new GameThread(this);
		Log.v(tag, "Starting Entity Manager");
		this.entityManager = new EntityManager(this);
		this.collisionDetection = new CollisionDetection(this);
		this.backgroundArray = new Background[6][3];
		
		
		for(int i = 0; i < 6; i++) //init bg,, move to game thread when level manager is done
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j] = new Background(this,i*400,j*400,1);
		
		
		levels = new Levels(this, 1);
		this.tileEngine = new TileEngine(this, levels.getLevel(),levels.getlevelLength());
		
		this.toggleGameThread(true);
		
		goatBleet = MediaPlayer.create(this, R.raw.bleet);
		goatDeath = MediaPlayer.create(this, R.raw.scream);
		
		
		//wireButtons();
	    
		
		 //* Not Ready quite yet, will do soon.
		ImageButton left = (ImageButton) findViewById(R.id.leftButton);
	    touchButton(left.getId(), com.comp380.towergame.entities.Andy.MoveDirection.LEFT);
	    
	    ImageButton right = (ImageButton) findViewById(R.id.rightButton);
	    moveButtonR(right.getId(), com.comp380.towergame.entities.Andy.MoveDirection.RIGHT);
	    
	    ImageButton jump = (ImageButton) findViewById(R.id.jump);
	    touchButton(jump.getId(), com.comp380.towergame.entities.Andy.MoveDirection.JUMP);
	    
	    ImageButton spawn = (ImageButton) findViewById(R.id.spawn);
	    spawnButton(spawn.getId(), 2);
	    
	    ImageButton fire = (ImageButton) findViewById(R.id.fire);
	    spawnButton(fire.getId(), 1);
	}

	public void toggleGameThread(boolean b) {
		Log.v(tag, "Starting Game Thread");
		this.gameThread.setRunning(b);
		if(b) this.gameThread.start();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public MediaPlayer getBleeter() {
		return goatBleet;
	}
	
	public MediaPlayer getDeathCry() {
		return goatDeath;
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
				Rect rect = new Rect();
				leftButton.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
	            
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					Log.v(tag, "Left Down");
					break;
				case MotionEvent.ACTION_UP:
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					Log.v(tag, "Left UP");
					break;
				case MotionEvent.ACTION_MOVE:
					if (handlr == null) return true;
					if(!rect.contains((int) x, (int) y)) {
						handlr.removeCallbacks(buttonAction);
						handlr = null;
						Log.v(tag, "Left Move");
						break;
					}
				}
				return false;
			}
			Runnable buttonAction = new Runnable() {
				@Override 
				public void run() {
					if (entityManager.getAndy() == null ) return;
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.LEFT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		//Right Button 
		final ImageButton rightButton = (ImageButton) findViewById(R.id.rightButton);
		rightButton.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			boolean canMove = false;
			
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				rightButton.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
	            
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					//canMove = false;
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep);
					break;
				case MotionEvent.ACTION_UP:
					getTileEngine().setSpeed(0);
					//canMove = true;
					if (handlr == null) return true;
					handlr.removeCallbacks(buttonAction);
					handlr = null;
					break;
				case MotionEvent.ACTION_MOVE:
					getTileEngine().setSpeed(0);
					//canMove = true;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.RIGHT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton jump = (ImageButton) findViewById(R.id.jump);
		jump.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				jump.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
	            
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.JUMP);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton down = (ImageButton) findViewById(R.id.down);
		down.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				down.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.DOWN);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton spawn = (ImageButton) findViewById(R.id.spawn);
		spawn.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				spawn.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep*15);
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
					entityManager.getAll().add(new Goat(entityManager, BitmapFactory.decodeResource(getResources(), R.drawable.badguy)));
					handlr.postDelayed(this, sleep*15);
				}
			};
		});
		
		final ImageButton fire = (ImageButton) findViewById(R.id.fire);
		fire.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				fire.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (handlr != null) return true;
					handlr = new Handler();
					handlr.postDelayed(buttonAction, sleep*6);
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
					entityManager.getAll().add(new Flame(entityManager, 
							BitmapFactory.decodeResource(getResources(), R.drawable.flame)));
					handlr.postDelayed(this, sleep*6);
				}
			};
		});
		
		final ImageButton downRight = (ImageButton) findViewById(R.id.downRight);
		downRight.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				downRight.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_DOWN_RIGHT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton downLeft = (ImageButton) findViewById(R.id.downLeft);
		downLeft.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				downLeft.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_DOWN_LEFT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton upRight = (ImageButton) findViewById(R.id.upRight);
		upRight.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				upRight.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_UP_RIGHT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
		
		final ImageButton upLeft = (ImageButton) findViewById(R.id.upLeft);
		upLeft.setOnTouchListener(new View.OnTouchListener() {
			private Handler handlr;
			@Override 
			public boolean onTouch(View v, MotionEvent event) {
				Rect rect = new Rect();
				upLeft.getHitRect(rect);
				float x = event.getX() + rect.left;
	            float y = event.getY() + rect.top;
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
					entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.DIAGONAL_UP_LEFT);
					handlr.postDelayed(this, sleep);
				}
			};
		});
	}

	@Override
	public void onPause() {
		if (gameThread != null){
			try {
				gameThread.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPause();
	}

	public TileEngine getTileManager() {
		return this.getTileEngine();
	}
	
	public int scalingFactor() {
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.badguy);
		return bm.getHeight() / 90;
	}
	
	private void touchButton(int id, final com.comp380.towergame.entities.Andy.MoveDirection direction) {
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
                                entityManager.getAndy().onMoveEvent(direction);
                                handlr.postDelayed(this, sleep);
                        }
                };
        });
	}
        
        private void moveButtonR(int id, final com.comp380.towergame.entities.Andy.MoveDirection direction) {
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
                                    if((entityManager.getAndy().getX() >= 700) && (tileEngine.getInGamePos() <= 180) && (tileEngine.getSpeed() != -8))
                            		{
                            			//if(canMove == false)
                            			//{
                            				tileEngine.setSpeed(-8);
                            			//}
                            		}
                            		else
                            		{
                            			tileEngine.setSpeed(0);
                            			entityManager.getAndy().onMoveEvent(direction);
                            		}
                            		if (entityManager.getAndy() == null ) return;
                            		//entityManager.getAndy().onMoveEvent(com.comp380.towergame.entities.Andy.MoveDirection.RIGHT);
                            		handlr.postDelayed(this, sleep);
                            }
                    };
            });
        }
            
            private void spawnButton(int id, final int type) {
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
                                	if(type == 2)  {                              
                                		entityManager.getAll().add(new Goat(entityManager, 
                							BitmapFactory.decodeResource(getResources(), R.drawable.badguy)));
                                	}
                                	else
                                	{
                                        entityManager.getAll().add(new Flame(entityManager, 
                    							BitmapFactory.decodeResource(getResources(), R.drawable.flame)));
                    					handlr.postDelayed(this, sleep*6);
                                	}
                                }
                        };
                });
        
    	
}

	public TileEngine getTileEngine() {
		return tileEngine;
	}
	
}
