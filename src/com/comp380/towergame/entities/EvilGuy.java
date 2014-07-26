package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;

import android.graphics.Bitmap;

public class EvilGuy extends BaseEntity {
	private boolean ystop = false;
	private boolean xstop = false;
	
	private int attacking = 0;
	private int jumpSwitch = 0;

	public EvilGuy(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 325, 500, 108, 105);
		this.setID(2);
	}
	
	private void jumpUp(int y) {
		int inc = 3;
		if((y - inc) > 0) {
			this.setY((int) (y - inc));
		} else {
			this.ystop = false;
		}		
	}
	
	private void jumpDown(int y) {
		int inc = 3;
		if((y + inc) < GameActivity.GAME_MAX_HEIGHT) {
			this.setY((int) (y + inc));
		} else {
			this.ystop = true;
		}
	}
	
	private void attackPlayer(int x) {
		this.attacking = 1;
		int inc = 8;
		if((x - inc) > 0) {
			this.setX((int) (x - inc));
		} else {
			this.xstop = true;
		}
	}

	public void update() {
		int x = this.getX();
		int y = this.getY();
		
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
			this.attackPlayer(x);
		}
		
		if(this.xstop) {
			this.setHealth(-1);
		}
	}
	
	private boolean canSeePlayer() {
		int andyx = this.getManager().getAndy().getX();
		int andyy = this.getManager().getAndy().getY();
		if(Math.abs(andyy - this.getY()) < 50) {
			return true;
		}
		return false;
	}
} 
