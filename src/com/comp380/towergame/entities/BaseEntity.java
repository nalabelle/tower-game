package com.comp380.towergame.entities;

import java.util.HashMap;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.background.Tile;
import com.comp380.towergame.physics.Speed;
import com.comp380.towergame.physics.CollisionDetection.PointMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class BaseEntity {
	protected EntityManager manager;
	protected Bitmap texture;
	protected Bitmap originalTexture;
	private long lastUpdate;
	
	protected Point point;
	protected float speed = Speed.BASE;
	protected boolean facingRight = true;
	protected boolean onGround = false;
	protected boolean isFlying = false;
	protected boolean isJumping = false;
	protected boolean isWalking = false;
	protected boolean collisionFlag = false;
	
	protected float velocityY = 2;
	protected float velocityX;
	
	protected int health;
	
	private final int SPAWN_DISTANCE = 90*8; //px width of tile * offscreen render distance
	
	public BaseEntity(EntityManager manager, Bitmap texture, int x, int y) {
		this.point = new Point(x, y);
		this.texture = texture;
		this.originalTexture = texture;
		this.manager = manager;
		this.lastUpdate = 0;
	}
	
	private Point updatePosition() {
		float td = this.lastUpdate - System.currentTimeMillis();
		td = 1; //for testing
		Point newPoint = new Point(this.point);
		if(!this.onGround) {
			newPoint.y += this.velocityY * td;
			this.velocityY += Speed.GRAVITY * td;
		} else {
			newPoint.y = this.point.y;
		}
		if(Math.abs(this.velocityX) > 1) {
			newPoint.x += this.velocityX * td;
			if(!(this instanceof Flame)) {
				if(this.onGround) {
					this.velocityX -= Math.signum(this.velocityX)*Speed.GROUND_FRICTION * td;
				} else {
					this.velocityX -= Math.signum(this.velocityX)*Speed.AIR_FRICTION * td;
				}
			}
		}
		//damn you gravity
		if(this.isFlying)
			newPoint.y = this.point.y;
		return newPoint;
	}
	
	protected void moveJump() {
		this.onGround = false;
		this.isJumping = true;
		this.velocityY = Speed.JUMPING;
	}
	
	protected void moveWalk(int direction) {
		this.isWalking = true;
		this.velocityX = direction * this.speed;
	}
	
	protected void moveUpdate() {
		Point newPoint = this.updatePosition();
		if(!this.point.equals(newPoint))
			Log.v(this.getClass().getName(), "New " + this.point.toString() + "Velocity: ("+this.velocityX+","+this.velocityY+")");
		
		//Check entity collisions
		BaseEntity firstCollided = this.manager.checkEntityToEntityCollisions(this, newPoint);
		
		//Catch null pointers, Issue? null collision not with nonentity
		//O yes, andy wont move if 1stCollision == null
		if(firstCollided != null) {
			if(!this.collisionFlag)
				Log.v(this.getClass().getName(), " collided with " + firstCollided.getClass());
			this.collisionFlag = true;
			
			//Andy Collision
			if(this instanceof Andy) {
				if(firstCollided instanceof Goat) { 
					firstCollided.setHealth(-100); //Kill Goat
					this.manager.getAndy().setHealth(this.manager.getAndy().getHealth() - 20); //Hurt Andy
					Log.v("atest", "Andy v Goat");
				}
			}
			//Goat Collision
			else if(this instanceof Goat) {
				//this.health = -100; //Kill Goat
				if(firstCollided instanceof Andy)
					firstCollided.setHealth(firstCollided.getHealth() -20); //Hurt Andy
				//Bump Andy away?
				this.manager.getAndy().moveUpdate();
				Log.v("atest", "Goat v Andy");
			}
			//Flame Collision
			else if(this instanceof Flame) {
				this.health = -100; //Collide and DIE!
			//Goat hits
			if(this instanceof Goat) {
				this.health = -20;
				if(firstCollided instanceof Andy)
					firstCollided.setHealth(firstCollided.getHealth() -20);
			}
			
			//Flame hits
			if(this instanceof Flame) {
				this.health = -100;					
				if(firstCollided instanceof Goat) {
					firstCollided.setHealth(-100); //Kill Goat
					this.manager.getAndy().setScore(this.manager.getAndy().getScore() + 1);
					Log.v("atest", "Flame v Goat");
					//getMediaPlayer();
					//this.manager.getAll().remove(firstCollided);
				}
			}
		} //End Entity 
			
			//Andy hits
			if(this instanceof Andy) {
				if(firstCollided instanceof Flame) {
					firstCollided.health = -100;
				}
			}
		}
		
		//Check entity->tile collisions
		HashMap<PointMap, Tile> blocks = this.manager.checkEntityToTileCollisions(this, newPoint, this.velocityX, this.velocityY);
		
		//Flames ignore tile collisions
		if(this instanceof Flame)
			blocks = null;
		
		if(blocks != null) {
			//FEET
			if(blocks.get(PointMap.MIDDLE_BOTTOM_LEFT) == null && blocks.get(PointMap.MIDDLE_BOTTOM_RIGHT) == null) {
				//we lost tile collision on both feet, we're probably falling now.
				this.onGround = false;
			} else {
				//we have a collision on at least one of our feet. Pick the higher one.
				Tile higher = blocks.get(PointMap.MIDDLE_BOTTOM_LEFT);
				if(higher == null || ( blocks.get(PointMap.MIDDLE_BOTTOM_RIGHT) != null) &&
					(blocks.get(PointMap.MIDDLE_BOTTOM_RIGHT).getBounds().top > higher.getBounds().top))
						higher = blocks.get(PointMap.MIDDLE_BOTTOM_RIGHT);
				
				//put us on the higher block.
				if(this.velocityY > 0) {
					this.onGround = true;
					this.velocityY = 0;
					newPoint.y = higher.getBounds().top - this.getBounds().height();
				}
			}
			
			//FACE
			if(blocks.get(PointMap.LEFT_TOP_QUARTER) != null || blocks.get(PointMap.LEFT_BOTTOM_QUARTER) != null) {
				//one or more of our points on the left has hit something.
				//we have a collision on at least one of our feet. Pick the higher one.
				Tile righter = blocks.get(PointMap.LEFT_TOP_QUARTER);
				if(righter == null || ( blocks.get(PointMap.LEFT_BOTTOM_QUARTER) != null) &&
					(blocks.get(PointMap.LEFT_BOTTOM_QUARTER).getBounds().right > righter.getBounds().right))
						righter = blocks.get(PointMap.LEFT_BOTTOM_QUARTER);
				
				if(this.velocityX < 0) {
					this.velocityX = 0;
					newPoint.x = righter.getBounds().right + 1;
				}
			}
			
			//OTHER FACE
			if(blocks.get(PointMap.RIGHT_TOP_QUARTER) != null || blocks.get(PointMap.RIGHT_BOTTOM_QUARTER) != null) {
				//one or more of our points on the left has hit something.
				//we have a collision on at least one of our feet. Pick the higher one.
				Tile lefter = blocks.get(PointMap.RIGHT_TOP_QUARTER);
				if(lefter == null || ( blocks.get(PointMap.RIGHT_BOTTOM_QUARTER) != null) &&
					(blocks.get(PointMap.RIGHT_BOTTOM_QUARTER).getBounds().left < lefter.getBounds().left))
						lefter = blocks.get(PointMap.RIGHT_BOTTOM_QUARTER);
				
				if(this.velocityX > 0) {
					this.velocityX = 0;
					newPoint.x = lefter.getBounds().left -this.getBounds().width()- 1;
				}
			}
		} else {
			//we lost ALL tile collision, we're free-falling now.
			this.onGround = false;
		}
		
		//revert somewhere before here if you need to back up from a collision.
		this.point = newPoint;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.texture, this.point.x, this.point.y, null);
	}
	
	//This is called during the game loop, possibly multiple times per draw to screen.
	public void update() {
		this.moveUpdate();
		
		//Kill them at the edges.
		if(this.point.x > GameActivity.GAME_MAX_WIDTH + this.SPAWN_DISTANCE ||
			this.point.y > GameActivity.GAME_MAX_HEIGHT ||
			(this.point.x + this.getBounds().width() + this.SPAWN_DISTANCE) < 0 ) {
				this.health = -100;
		}
		this.lastUpdate = System.currentTimeMillis();
	}

	public Rect getBounds() {
		return new Rect(this.point.x, this.point.y,
			this.point.x + this.texture.getWidth(),
			this.point.y + this.texture.getHeight());
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getFacing() {
		return (this.facingRight) ? 1 : -1;
	}
	
	protected void reverseBitmap() {
		if(this.texture != this.originalTexture) {
			this.texture = this.originalTexture;
		} else {
			Matrix matrix = new Matrix();
			matrix.setScale(-1, 1);
			matrix.postTranslate(texture.getWidth(), 0);
			this.texture = Bitmap.createBitmap(this.texture, 0, 0, this.texture.getWidth(), this.texture.getHeight(), matrix, true);
		}
	}

}
