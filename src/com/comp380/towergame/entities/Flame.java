package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;

import android.graphics.Bitmap;

public class Flame extends BaseEntity {
	private boolean ystop = false;
	private boolean xstop = false;

	public Flame(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 325, 500);
		this.setID(3);
	}
	
	public Flame(EntityManager manager, Bitmap bitmap, int x, int y) {
		super(manager, bitmap, x, y);
		this.setID(3);
	}

	public void update() {
		int x = this.getX();
		int y = this.getY();
		
		this.setX(x+5);
	}
}