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
			
			if(Rect.intersects(other.getBounds(), moved))
				return other;
		}
		
		return null;
	}
	
	public Tile checkTileCollisions(BaseEntity entityMoved, Point newPoint, MoveDirection direction) {
		Rect moved = entityMoved.getBounds();
		moved.offsetTo(newPoint.x, newPoint.y);
		ArrayList<Tile> safeIter = this.context.getTileManager().getAllVisible();
		
		int traceX = Integer.MIN_VALUE;
		int traceY = Integer.MIN_VALUE;
		//true for top or left.
		int half = -1;
		
		switch(direction) {
		case UP:
		case JUMP:
			traceY = moved.top;
			half = 1;
			break;
		case DOWN:
		case FALL:
			traceY = moved.bottom;
			half = 0;
			break;
		case LEFT:
			traceX = moved.left;
			half = 1;
			break;
		case RIGHT:
			traceX = moved.right;
			half = 0;
			break;
		default:
			break;
		}
		
		for(Tile other : safeIter) {
			Rect otherBound = other.getBounds();
			if(half > 0) {
				//top and left
				if(traceY > Integer.MIN_VALUE && otherBound.top > traceY) {
					//top
					if(this.tileIntersection(moved, otherBound))
						return other;
				}
				if(traceX > Integer.MIN_VALUE && otherBound.left < traceX) {
					//left
					if(this.tileIntersection(moved, otherBound))
						return other;
				}
			} else {
				//bottom and right
				if(traceY > Integer.MIN_VALUE && otherBound.bottom < traceY) {
					//bottom
					if(this.tileIntersection(moved, otherBound))
						return other;
				}
				if(traceX > Integer.MIN_VALUE && otherBound.right > traceX) {
					//right
					if(this.tileIntersection(moved, otherBound))
						return other;
				}
			}
			
		}
		
		return null;
	}
	
	private boolean tileIntersection(Rect moved, Rect tile) {
		return Rect.intersects(moved, tile);
	}
}
