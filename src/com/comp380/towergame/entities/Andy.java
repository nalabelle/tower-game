package com.comp380.towergame.entities;

import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;

public class Andy extends BaseEntity {
	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 25, 25);
		this.setID(1);
		this.speed = Speed.PLAYER;
		this.health = 20;
	}
	
	public int getX() {
		return this.point.x;
	}
	
	public int getY() {
		return this.point.y;
	}
}
