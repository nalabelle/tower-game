package com.comp380.towergame.physics;

import com.comp380.towergame.entities.BaseEntity;

public class Gravity {
	public static void gravityAdjustment(BaseEntity ent) {
		ent.move(MoveDirection.FALL, Speed.GRAVITY);
	}
}
