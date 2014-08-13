package com.comp380.towergame.entities;

import com.comp380.towergame.R;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

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
	
	//Reverse Textures, we're storing these for comparison. This is lazy.
	Bitmap running1Reverse;
	Bitmap running2Reverse;
	Bitmap running3Reverse;
	Bitmap running4Reverse;
	
	Bitmap standingReverse;
	Bitmap jumpingReverse;
	
	//Bitmap boundingTexture; this is the main texture in the parent class
	Bitmap currentTexture;
	
	private int runRate = 6;
	private int runFrames = 4;
	private int walkUpdate = this.runFrames * this.runRate;

	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, ensmallen(bitmap), 25, 25);
		this.currentTexture = bitmap;
		this.speed = Speed.PLAYER;
		this.health = 100;
		this.score = 0;
		
		this.running1 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run1);
		this.running1Reverse = this.generateReverseBitmap(this.running1);
		this.running2 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run2);
		this.running2Reverse = this.generateReverseBitmap(this.running2);
		this.running3 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run3);
		this.running3Reverse = this.generateReverseBitmap(this.running3);
		this.running4 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_run4);
		this.running4Reverse = this.generateReverseBitmap(this.running4);
		
		this.standing = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.player_stand);
		this.standingReverse = this.generateReverseBitmap(this.standing);
		this.jumping = bitmap;
		this.jumpingReverse = this.generateReverseBitmap(this.jumping);

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
				if(this.facingRight)
					this.currentTexture = this.jumping;
				else
					this.currentTexture = this.jumpingReverse;
				
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
		super.update();
		Log.v(this.getClass().getName(), "New " + this.point.toString() + "Velocity: ("+this.velocityX+","+this.velocityY+") "+this.walkUpdate/this.runRate);

		if(this.onGround && (this.currentTexture == this.jumping || this.currentTexture == this.jumpingReverse)) {
			this.currentTexture = this.standing;
			if(!this.facingRight)
				this.currentTexture = this.standingReverse;
		} else if(this.onGround && this.isWalking) {
			this.walkUpdate--;
			
			switch(Math.abs(this.walkUpdate/this.runRate)) {
			case 4:
				if(this.facingRight)
					this.currentTexture = this.running1;
				else
					this.currentTexture = this.reverseBitmap(running1);
				break;
			case 3:
				if(this.facingRight)
					this.currentTexture = this.running2;
				else
					this.currentTexture = this.reverseBitmap(running2);
				break;
			case 2:
				if(this.facingRight)
					this.currentTexture = this.running3;
				else
					this.currentTexture = this.reverseBitmap(running3);
				break;
			case 1:
				if(this.facingRight)
					this.currentTexture = this.running4;
				else
					this.currentTexture = this.reverseBitmap(running4);
				break;
			case 0:
				this.walkUpdate = this.runFrames*this.runRate;
				break;
			}
		} else {
			
			this.walkUpdate = this.runFrames*this.runRate;
			//NOOO :(
			if(this.currentTexture == this.running1 || this.currentTexture == this.running1Reverse
					|| this.currentTexture == this.running2 || this.currentTexture == this.running2Reverse
					|| this.currentTexture == this.running3 || this.currentTexture == this.running3Reverse
					|| this.currentTexture == this.running4 || this.currentTexture == this.running4Reverse){
				if(this.facingRight)
					this.currentTexture = this.standing;
				else
					this.currentTexture = this.reverseBitmap(standing);
			}
			
		}
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
		this.currentTexture = this.reverseBitmap(this.currentTexture);
		if(this.facingRight) {
			//position the hitbox back to the original
			this.textureDrawDifference = ensmallenFactor/2 - this.textureDrawOffset;
		} else {
			//big ol hat aww dang
			this.textureDrawDifference = ensmallenFactor/2 + this.textureDrawOffset;
		}
	}
	
	protected Bitmap reverseBitmap(Bitmap bitmap) {
		//oh wow this is horrible.
		if(bitmap == this.jumping)
			bitmap = this.jumpingReverse;
		else if(bitmap == this.jumpingReverse)
			bitmap = this.jumping;
		
		else if(bitmap == this.standing)
			bitmap = this.standingReverse;
		else if(bitmap == this.standingReverse)
			bitmap = this.standing;
		
		else if(bitmap == this.running1)
			bitmap = this.running1Reverse;
		else if(bitmap == this.running1Reverse)
			bitmap = this.running1;
		
		else if(bitmap == this.running2)
			bitmap = this.running2Reverse;
		else if(bitmap == this.running2Reverse)
			bitmap = this.running2;
		
		else if(bitmap == this.running3)
			bitmap = this.running3Reverse;
		else if(bitmap == this.running3Reverse)
			bitmap = this.running3;
		
		else if(bitmap == this.running4)
			bitmap = this.running4Reverse;
		else if(bitmap == this.running4Reverse)
			bitmap = this.running4;
		
		return bitmap;
	}
	
	private Bitmap generateReverseBitmap(Bitmap bitmap) {
		return super.reverseBitmap(bitmap);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.currentTexture, this.point.x - this.textureDrawDifference, this.point.y, null);
	}

}
