package com.comp380.towergame.entities;

import com.comp380.towergame.R;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

public class Andy extends BaseEntity {
	int score;
	
	static int ensmallenFactor = 50;
	int textureDrawDifference;
	int textureDrawOffset;
	
	//Textures
	Bitmap running1;
	Bitmap running2;
	Bitmap running3;
	Bitmap running4;
	
	Bitmap standing;
	
	Bitmap jumping;
	
	//Bitmap boundingTexture; this is the main texture in the parent class
	Bitmap reverseTexture; //for reversals
	Bitmap currentTexture;
	
	private boolean updateTexture = false;

	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, ensmallen(bitmap), 25, 25);
		this.currentTexture = bitmap;
		this.speed = Speed.PLAYER;
		this.health = 100;
		this.score = 0;
		
		this.running1 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run1);
		this.running2 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run2);
		this.running3 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run3);
		this.running4 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run4);
		
		this.standing = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_stand);
		this.jumping = bitmap;

		//this makes the hitbox position itself properly.
		this.textureDrawOffset = ensmallenFactor/4;
		this.textureDrawDifference = ensmallenFactor/2 + this.textureDrawOffset;
	}
	
	public static Bitmap ensmallen(Bitmap bitmap) {
		return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - ensmallenFactor, bitmap.getHeight(), false);
	}
	
	public int getX() {
		return this.point.x;
	}
	
	public int getY() {
		return this.point.y;
	}

	public int getScore() {
		return score;
		
	}
	
	public void setScore(int x) {
		score = x;
	}
	
	public enum MoveDirection {
		UP, DOWN, LEFT, RIGHT, JUMP, DIAGONAL_UP_RIGHT, DIAGONAL_DOWN_LEFT, DIAGONAL_UP_LEFT, DIAGONAL_DOWN_RIGHT;
	}
	
	/** Player input is accounted for here **/
	public void onMoveEvent(MoveDirection direction) {
		switch(direction) {
		case UP:
		case JUMP:
			if(this.onGround) {
				this.currentTexture = this.jumping;
				super.moveJump();
			}
			break;
		case DOWN:
			break;
		case LEFT:
			if(this.facingRight)
				this.reverseBitmap();
			this.facingRight = false;
			super.moveWalk(-1);
			break;
		case RIGHT:
			if(!this.facingRight)
				this.reverseBitmap();
			this.facingRight = true;
			super.moveWalk(1);
			break;
		default:
			break;
		}
	}
	

	public int getVelocityX() {
		return (int) this.velocityX;
	}
	
	public void update() {
		if(this.onGround && (this.currentTexture == this.jumping || this.currentTexture == this.reverseBitmap(this.jumping))) {
			this.currentTexture = this.standing;
		}
		super.update();
	}
	
	protected void moveUpdate() {
		Point oldPoint = this.point;
		super.moveUpdate();
		
		
		//System.out.println("inGame pos: " + this.manager.getContext().getTileEngine().getInGamePos());
		if(this.getX() >= 700 && this.manager.getContext().getTileEngine().getInGamePos() < 177) {
			this.manager.getContext().getTileEngine().setSpeed((int) (this.velocityX * -1));
			this.manager.normalizeMovement(-1*this.velocityX);
			this.point.x = oldPoint.x;
		} 
		else {
			this.manager.getContext().getTileEngine().setSpeed(0);
		}
		
		if(this.getX() < 0 && this.velocityX < 0) {
			this.velocityX = 0;
			this.point.x = oldPoint.x;
		}
	}
	
	//Andy's hitbox is slightly different, so we have to tweak this a bit.
	protected void reverseBitmap() {
		//super.reverseBitmap();
		this.currentTexture = this.reverseBitmap(this.currentTexture);
		if(this.facingRight) {
			//position the hitbox back to the original
			this.textureDrawDifference = ensmallenFactor/2 + this.textureDrawOffset;
		} else {
			//big ol hat aww dang
			this.textureDrawDifference = ensmallenFactor/2 - this.textureDrawOffset;
		}
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.currentTexture, this.point.x - this.textureDrawDifference, this.point.y, null);
	}

}
