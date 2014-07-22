package com.comp380.towergame.physics;

import android.graphics.Rect;
import android.util.Log;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.entities.BaseEntity;

public class CollisionDetection {
	GameActivity context;
	
	public CollisionDetection(GameActivity context) {
		this.context = context;
	}
	
	public void checkCollisions(BaseEntity entityMoved) {
		Rect moved = entityMoved.getBounds();
		for(BaseEntity other : this.context.getEntityManager().getAll()) {
			if(other == entityMoved) continue;
			if(Rect.intersects(other.getBounds(), moved)) {
				entityMoved.setX((int) (0+Math.random()*1000));
				entityMoved.setY((int) (0+Math.random()*1000));
				Log.v("Collision", "Collision Detected");
			}
		}
	}
}
