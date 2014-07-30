package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.background.Tile;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class BaseEntity {
	protected EntityManager manager;
	protected Bitmap texture;
	protected Bitmap originalTexture;
	private float scale;
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
	
	public BaseEntity(EntityManager manager, Bitmap texture, int x, int y) {
		this.point = new Point(x, y);
		this.texture = texture;
		this.originalTexture = texture;
		this.manager = manager;
		this.lastUpdate = 0;
		
		//scaling due to DPI changes.
		this.scale = this.manager.getContext().scalingFactor();
	}
	
	private Point updatePosition() {
		float td = this.lastUpdate - System.currentTimeMillis();
		td = 1; //for testing
		Point newPoint = new Point(this.point);
		if(!this.onGround) {
			newPoint.y += this.velocityY * td;
			this.velocityY += Speed.GRAVITY * td;
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
	
	private void moveUpdate() {
		Point newPoint = this.updatePosition();
		if(!this.point.equals(newPoint))
			Log.v(this.getClass().getName(), "New " + this.point.toString() + "Velocity: ("+this.velocityX+","+this.velocityY+")");
		
		//Check entity collisions
		BaseEntity firstCollided = this.manager.checkEntityToEntityCollisions(this, newPoint);
		
		if(firstCollided != null) {
			if(!this.collisionFlag)
				Log.v(this.getClass().getName(), " collided with " + firstCollided.getClass());
			this.collisionFlag = true;
			if(!(this instanceof Andy))
				this.health = -10;
				if(firstCollided instanceof Andy)
					firstCollided.setHealth(firstCollided.getHealth() -10);
			if(this instanceof Flame)
				this.health = -100;
				if(firstCollided instanceof Goat) {
					firstCollided.setHealth(-100);
					this.manager.getAndy().setScore(this.manager.getAndy().getScore() + 1);
					//getMediaPlayer();
					//this.manager.getAll().remove(firstCollided);
				}
		}
		
		//Check entity->tile collisions
		Tile block = this.manager.checkEntityToTileCollisions(this, newPoint, this.velocityX, this.velocityY);
		if(block != null) {
			if(this.velocityY > 0) {
				this.point.y = block.getBounds().top - this.getBounds().height();
				Log.v(this.getClass().getName(), "on ground at" + this.point.y);
				this.onGround = true;
				this.velocityY = 0;
			} else {
				if(this.velocityX > 0) {
					this.point.x = block.getBounds().left - this.getBounds().width();
					//Log.v(this.getClass().getName(), "hit right at" + this.point.x);
				} else {
					this.point.x = block.getBounds().right + 1;
					//Log.v(this.getClass().getName(), "hit left at" + this.point.x);
				}
				this.velocityY = 0;
			}
		} else {
			//we lost tile collision, we're probably falling now.
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
		if(this.point.x > GameActivity.GAME_MAX_WIDTH ||
			this.point.y > GameActivity.GAME_MAX_HEIGHT ||
			(this.point.x + this.getBounds().width()) < 0 ||
			(this.point.y + this.getBounds().height()) < 0) {
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
		return ((this.facingRight) ? 1 : -1);
	}

}
