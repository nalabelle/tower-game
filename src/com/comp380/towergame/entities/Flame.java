package com.comp380.towergame.entities;

import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;
import android.util.Log;

public class Flame extends BaseEntity {
	private float speed = Speed.PROJECTILE;

	public Flame(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 
				//This takes the center and adds half an andy in a positive or negative direction depending on which way we're looking.
				(int) (manager.getAndy().getBounds().centerX() 
				+ ((manager.getAndy().getBounds().width()/2)*Math.signum(manager.getAndy().getFacing()))),
				manager.getAndy().getY());
		this.velocityX = Math.signum(manager.getAndy().getFacing())*this.speed;
		Log.v(this.getClass().toString(), "Fireball Velocity" + this.velocityX);
		
		this.isFlying = true;
	}
}