package com.comp380.towergame.entities;

import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;

public class Andy extends BaseEntity {
	int score;
	
	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 25, 25);
		this.setID(1);
		this.speed = Speed.PLAYER;
		this.health = 20;
		this.score = 0;
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
}
