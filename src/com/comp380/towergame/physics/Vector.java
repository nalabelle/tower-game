package com.comp380.towergame.physics;

import android.graphics.Point;

public class Vector {
	private Point point;
	private int speed;
	
	public Vector(Point xy, int speed) {
		this.point = xy;
		this.speed = speed;
	}
	
}
