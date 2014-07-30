package com.comp380.towergame.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
	
	public Tile checkTileCollisions(BaseEntity entityMoved, Point newPoint, float velocityX, float velocityY) {
		//Get the current position and velocity.
		Rect moved = entityMoved.getBounds();
		
		//Move the bounding box to the new position for testing.
		moved.offsetTo(newPoint.x, newPoint.y);
		
		HashMap<CollisionDetection.PointMap, Point> checkPoints = this.generateBoundingPoints(moved);
		HashMap<PointMap, Tile> tilesTouched = this.getTilesTouched(checkPoints);
		
		//Intelligently return a tile being touched based on our direction.
		Tile returnTile = null;
		if(Math.abs(velocityX) > Math.abs(velocityY)) {
			//more lateral motion
			if(velocityX == 0) {
				//Both are zero? this shouldn't get hit.
			} else if(velocityX > 0) {
				//moving right
				returnTile = tilesTouched.get(PointMap.RIGHT_TOP_QUARTER);
				if(returnTile == null) {
					returnTile = tilesTouched.get(PointMap.RIGHT_BOTTOM_QUARTER);
				}
			} else {
				returnTile = tilesTouched.get(PointMap.LEFT_TOP_QUARTER);
				if(returnTile == null) {
					returnTile = tilesTouched.get(PointMap.LEFT_BOTTOM_QUARTER);
				}
			}
		} else {
			//vertical motion
			if(velocityY == 0) {
				//both are zero.
			} else if(velocityY > 0) {
				returnTile = tilesTouched.get(PointMap.MIDDLE_BOTTOM);
			} else {
				returnTile = tilesTouched.get(PointMap.MIDDLE_TOP);
			}
		}
		
		return returnTile;
	}
	
	private HashMap<PointMap, Tile> getTilesTouched(HashMap<PointMap, Point> checkPoints) {
		HashMap<PointMap, Tile> map = new HashMap<PointMap, Tile>();
		//Gather all visible tiles and check the bound intersections.
		ArrayList<Tile> safeIter = this.context.getTileManager().getAllVisibleSolid();
		for(Tile other : safeIter) {
			for(Entry<PointMap, Point> entry : checkPoints.entrySet()) {
				if(other.getBounds().contains(entry.getValue().x, entry.getValue().y)) {
					map.put(entry.getKey(), other);
				}
			}
		}
		return map;
	}

	private HashMap<PointMap, Point> generateBoundingPoints(Rect moved) {
		HashMap<PointMap, Point> map = new HashMap<PointMap, Point>();
		
		//Generate points on boundaries
		map.put(PointMap.LEFT_TOP_QUARTER, new Point(moved.left, moved.centerY()-(moved.height()/4)));
		map.put(PointMap.LEFT_BOTTOM_QUARTER, new Point(moved.left, moved.centerY()+(moved.height()/4)));
		map.put(PointMap.RIGHT_TOP_QUARTER, new Point(moved.right, moved.centerY()-(moved.height()/4)));
		map.put(PointMap.RIGHT_BOTTOM_QUARTER, new Point(moved.right, moved.centerY() + (moved.height()/4)));
		map.put(PointMap.MIDDLE_TOP, new Point(moved.centerX(), moved.top));
		map.put(PointMap.MIDDLE_BOTTOM, new Point(moved.centerX(), moved.bottom));
		
		return map;
	}
	
	private enum PointMap {
		LEFT_TOP_QUARTER,
		LEFT_BOTTOM_QUARTER,
		RIGHT_TOP_QUARTER,
		RIGHT_BOTTOM_QUARTER,
		MIDDLE_TOP,
		MIDDLE_BOTTOM
	}
}
