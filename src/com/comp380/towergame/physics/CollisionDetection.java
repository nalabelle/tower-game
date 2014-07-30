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
		Point point = new Point(moved.left, moved.top);
		//We could use these to only check one side of the box?
		float velocityX = point.x - newPoint.x;
		float velocityY = point.y - newPoint.y;
		
		//Move the bounding box to the new position for testing.
		moved.offsetTo(newPoint.x, newPoint.y);
		
		ArrayList<Tile> safeIter = this.context.getTileManager().getAllVisibleSolid();
		ArrayList<Tile> intersections = new ArrayList<Tile>();
		for(Tile other : safeIter) {
			if(other.getBounds().intersect(moved)) {
				intersections.add(other);
			}
		}
		
		Tile returnTile = null;
		if(Math.abs(velocityX) > Math.abs(velocityY)) {
			//more lateral motion
			if(velocityX > 0) {
				//moving right
				returnTile = this.findNearest(intersections, moved, RectangleEdge.LEFT);
			} else {
				returnTile = this.findNearest(intersections, moved, RectangleEdge.RIGHT);
			}
		} else {
			//vertical motion
			if(velocityY > 0) {
				returnTile = this.findNearest(intersections, moved, RectangleEdge.TOP);
			} else {
				returnTile = this.findNearest(intersections, moved, RectangleEdge.BOTTOM);
			}
		}
		
		return returnTile;
	}
	
	private enum RectangleEdge {
		TOP, RIGHT, BOTTOM, LEFT;
	}
	
	private Tile findNearest(ArrayList<Tile> tiles, Rect entity, RectangleEdge edge) {
		Tile nearest = null;
		int nearness = Integer.MAX_VALUE;
		for(Tile check : tiles) {
			int test = Integer.MAX_VALUE;
			switch(edge) {
			case TOP:
				test = entity.top - check.getBounds().bottom;
				break;
			case RIGHT:
				test = check.getBounds().left - entity.right;
				break;
			case BOTTOM:
				test = check.getBounds().bottom - entity.bottom;
				break;
			case LEFT:
				test = entity.left - check.getBounds().right;
				break;
			}
			if(test < nearness) {
				nearest = check;
				nearness = test;
			}
		}
		return nearest;
	}
}
