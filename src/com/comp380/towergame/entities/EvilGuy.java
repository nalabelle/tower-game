package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;

import android.graphics.Bitmap;

public class EvilGuy extends BaseEntity {
	private boolean ystop = false;
	private boolean xstop = false;

	public EvilGuy(Bitmap bitmap) {
		super(bitmap, 325, 500, 108, 105);
		this.setID(2);
	}

	public void update() {
		int x = this.getX();
		int y = this.getY();
		
		if(!ystop) {
			if((y + 10) < GameActivity.GAME_MAX_HEIGHT) {
				this.setY((int) (y + Math.random()*10));
			} else {
				ystop = true;
			}
		} else {
			if((y - 10) > 0) {
				this.setY((int) (y - Math.random()*10));
			} else {
				ystop = false;
			}			
		}
		
		if(!xstop) {
			if((x + 10) < GameActivity.GAME_MAX_WIDTH) {
				this.setX((int) (x + Math.random()*10));
			} else {
				xstop = true;
			}
		} else {
			if((x - 10) > 0) {
				this.setX((int) (x - Math.random()*10));
			} else {
				xstop = false;
			}			
		}
	}
} 
