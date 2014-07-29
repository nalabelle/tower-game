package com.comp380.towergame.physics;

import java.util.ArrayList;

import android.graphics.Point;
import android.graphics.Rect;
import com.comp380.towergame.GameActivity;
import com.comp380.towergame.entities.BaseEntity;

public class CollisionDetection {
	GameActivity context;

	public CollisionDetection(GameActivity context) {
		this.context = context;
		
	}
	
	public BaseEntity checkCollisions(BaseEntity entityMoved, Point newPoint) {
		Rect moved = entityMoved.getBounds();
		moved.offsetTo(newPoint.x, newPoint.y);
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.context.getEntityManager().getAll());		
		
		for(BaseEntity other : safeIter) {
			//entity compared with self
			if(other == entityMoved) continue;
			
			if(Rect.intersects(other.getBounds(), moved))
				return other;
		}
		
		return null;
	}
}
