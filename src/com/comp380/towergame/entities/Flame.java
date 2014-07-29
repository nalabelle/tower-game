package com.comp380.towergame.entities;

import com.comp380.towergame.physics.MoveDirection;
import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;

public class Flame extends BaseEntity {
	private boolean ystop = false;
	private boolean xstop = false;
	private MoveDirection angle;
	private float speed = Speed.PROJECTILE;

	public Flame(EntityManager manager, Bitmap bitmap, MoveDirection direct) {
		super(manager, bitmap, manager.getAndy().getBounds().right+5, manager.getAndy().getY());
		this.angle = direct;
		this.setID(3);
	}
	
	public Flame(EntityManager manager, Bitmap bitmap, int x, int y, MoveDirection direct) {
		super(manager, bitmap, x, y);
		this.angle = direct;
		this.setID(3);
	}

	public void update() {
		this.move(this.angle);
	}
}