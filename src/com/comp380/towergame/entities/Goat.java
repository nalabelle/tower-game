package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.physics.MoveDirection;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

public class Goat extends BaseEntity {
	private boolean ystop = false;
	private boolean xstop = false;
	
	private int attacking = 0;
	private boolean andyLeft = true;
	private int jumpSwitch = 0;

	public Goat(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 
				(int) (Math.random()*GameActivity.GAME_MAX_WIDTH),
				(int) (Math.random()*GameActivity.GAME_MAX_HEIGHT));
		this.setID(2);
	}
	
	private void jumpUp(int y) {
		int inc = 3;
		if((y - inc) > 0) {
			this.move(MoveDirection.JUMP);
		} else {
			this.ystop = false;
		}		
	}
	
	private void jumpDown(int y) {
		int inc = 3;
		if((y + inc) < GameActivity.GAME_MAX_HEIGHT) {
			this.move(MoveDirection.FALL);
		} else {
			this.ystop = true;
		}
	}
	
	private void attackPlayer(int x, boolean left) {
		if(this.attacking < 1)
			this.manager.getContext().getBleeter().start();
		this.attacking = 1;
		this.speed = Speed.CHARGING;

		if(left) {
			this.move(MoveDirection.LEFT);
		} else {
			this.move(MoveDirection.RIGHT);	
		}
	}

	public void update() {
		super.update();
		int x = this.point.x;
		int y = this.point.y;
		
		if(attacking == 0 && !this.canSeePlayer()) {
			jumpSwitch++;
			if(jumpSwitch < 50 && jumpSwitch >= 30) {
				//5 frames of jumping.
				this.jumpUp(y);
			}
			if(jumpSwitch < 70 && jumpSwitch >= 50) {
				//5 frames of jumping down.
				this.jumpDown(y);
			}
			if(jumpSwitch >= 100) {
				this.jumpSwitch = 0;
			}
		} else {
			if(this.attacking < 1) {
				if(this.manager.getAndy().getX() - this.point.x > 0)
					andyLeft = false;
			}
			this.attackPlayer(x, andyLeft);
		}
		
		if(this.xstop) {
			this.setHealth(-1);
		}
	}
	
	private boolean canSeePlayer() {
		if(this.manager.getAndy() == null) return false;
		int andyx = this.manager.getAndy().getX(); //should check for facing too?
		int andyy = this.manager.getAndy().getY();
		if(Math.abs(andyy - this.point.y) < 20) {
			return true;
		}
		return false;
	}
	
	protected void collisionAction() {
	}
} 
