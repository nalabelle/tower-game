package com.comp380.towergame.physics;

import java.util.ArrayList;

import android.graphics.Point;
import android.graphics.Rect;
import com.comp380.towergame.GameActivity;
import com.comp380.towergame.background.Tile;
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
			//Hot Goat on Goat Action... Or Flames.... Or ANDYS
			if(other.getClass() == entityMoved.getClass()) continue;
			
			if(Rect.intersects(other.getBounds(), moved))
				return other;
		}
		
		return null;
	}
	
	public Tile checkTileCollisions(BaseEntity entityMoved, Point newPoint) {
		//Get the current position and velocity.
		Rect moved = entityMoved.getBounds();
		Point point = new Point(moved.top, moved.left);
		//We could use these to only check one side of the box?
		float velocityX = point.x - newPoint.x;
		float velocityY = point.y - newPoint.y;
		
		//Move the bounding box to the new position for testing.
		moved.offsetTo(newPoint.x, newPoint.y);
		
		ArrayList<Tile> safeIter = this.context.getTileManager().getAllVisibleSolid();
		for(Tile other : safeIter) {
			if(Rect.intersects(moved, other.getBounds())) {
				return other;
			}
		}
		
		return null;
	}
}
