package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.SoundManager;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;

public class Goat extends BaseEntity {
	private int lurking = 50;
	private int attacking = 0;
	private int attackDirection;

	public Goat(EntityManager manager, Bitmap bitmap) {
		this(manager, bitmap, 
				(int) (Math.random()*GameActivity.GAME_MAX_WIDTH),
				(int) (Math.random()*GameActivity.GAME_MAX_HEIGHT));
		this.facingRight = false;
	}
	
	public Goat(EntityManager manager, Bitmap bitmap, int tileOnScreenX, int tileOnScreenY) {
		super(manager, bitmap, tileOnScreenX, tileOnScreenY);
		this.facingRight = false;
	}

	private void attackPlayer() {
		if(this.attacking < 1)
			this.manager.getContext().getSoundEffects().play(SoundManager.bleetID, 1, 1, 1, 0, 1);
			this.attackDirection = (int) Math.signum(this.manager.getAndy().getX() - this.point.x);
		this.attacking = 1;
		//this.isWalking = true;

		this.velocityX =  this.attackDirection * Speed.CHARGING;
	}

	public void update() {
		if(attacking == 0 && !this.canSeePlayer()) {
			//Jump every X updates.
			if(this.lurking < 0) {
				this.moveJump();
				this.lurking = 100;
			}
			this.lurking--;
			
		} else {
				this.attackPlayer();
		}
		
		super.update();
	}
	
	private boolean canSeePlayer() {
		if(this.manager.getAndy() == null) return false;
		int andyy = this.manager.getAndy().getY();
		if(Math.abs(andyy - this.point.y) < 20) {
			return true;
		}
		return false;
	}
} 
