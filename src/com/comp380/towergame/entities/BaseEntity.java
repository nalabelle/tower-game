package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.physics.MoveDirection;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class BaseEntity {
	private int ID;
	protected EntityManager manager;
	private Bitmap texture;
	
	protected Point point;
	protected float speed = Speed.BASE;
	protected boolean onGround = false;
	protected boolean collisionFlag = false;
	
	protected int health;
	
	public BaseEntity(EntityManager manager, Bitmap texture, int x, int y) {
		this.point = new Point(x, y);
		this.texture = texture;
		this.manager = manager;
	}
	
	public void move(MoveDirection direction) {
		//create an X,Y for where we want to go based on our old one.
		Point newPoint = new Point(this.point);
		
		//If we collided with something, we better make a bigger jump for now.
		float oldSpeed = this.speed;
		if(this.collisionFlag) {
			this.speed = this.speed * this.speed;
		}
		
		switch(direction) {
		case UP:
		case JUMP:
			newPoint.offset(0, (int) (-1 *this.speed));
			break;
		case DOWN:
		case FALL:
			newPoint.offset(0, (int) (1 *this.speed));
			break;
		case LEFT:
			newPoint.offset((int) (-1 *this.speed), 0);
			break;
		case RIGHT:
			newPoint.offset((int) (1 *this.speed), 0);
			break;
		default:
			break;
		}
		
		//Check entity collisions
		BaseEntity firstCollided = this.manager.checkEntityToEntityCollisions(this, newPoint);
		if(firstCollided == null) {
			this.point.set(newPoint.x, newPoint.y);
		} else {
			if(!this.collisionFlag)
				Log.v(this.getClass().getName(), " collided with " + firstCollided.getClass());
			this.collisionFlag = true;
			//this.collisionAction(newPoint, direction);
			if(!(this instanceof Andy))
				this.health = -10;
		}
		
		//Check entity->tile collisions
		if(this.manager.checkEntityToTileCollisions(this, newPoint, direction) != null) {
			if(direction == MoveDirection.DOWN || direction == MoveDirection.FALL) {
				this.onGround = true;
			}
		}
		this.speed = oldSpeed;
	}
	
	//for applying gravity
	public void move(MoveDirection direction, float speed) {
		float oldSpeed = this.speed;
		this.speed = speed;
		this.move(direction);
		this.speed = oldSpeed;
	}
	
	private void collisionAction(Point newPoint, MoveDirection direction) {
			switch(direction) {
			case UP:
				this.move(MoveDirection.DOWN);
				break;
			case DOWN:
				this.move(MoveDirection.UP);
				break;
			case FALL:
				this.move(MoveDirection.JUMP);
				break;
			case JUMP:
				this.move(MoveDirection.FALL);
				break;
			case LEFT:
				this.move(MoveDirection.RIGHT);
				break;
			case RIGHT:
				this.move(MoveDirection.LEFT);
				break;
			default:
				break;
			}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Bitmap getTexture() {
		return texture;
	}

	public void setTexture(Bitmap texture) {
		this.texture = texture;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.texture, this.point.x, this.point.y, null);
	}
	
	public void update() {
		if(!this.onGround) {
			
		}
		if(this.point.x > GameActivity.GAME_MAX_WIDTH || this.point.y > GameActivity.GAME_MAX_HEIGHT ||
			this.point.x < 0 || this.point.y < 0) {
			this.health = -100;
		}
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

}
