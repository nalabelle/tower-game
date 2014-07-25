package com.comp380.towergame.physics;

import java.util.ArrayList;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.R;
import com.comp380.towergame.entities.BaseEntity;

public class CollisionDetection {
	GameActivity context;
	MediaPlayer goatBleet;
	
	
	
	public CollisionDetection(GameActivity context) {
		this.context = context;
		
	}
	
	public void checkCollisions(BaseEntity entityMoved) {
		Rect moved = entityMoved.getBounds();
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.context.getEntityManager().getAll());
		int entityMovedID = entityMoved.getID();
		goatBleet = this.context.getBleeter();
		
		for(BaseEntity other : safeIter) {
			//entity compared with self
			if(other == entityMoved) continue;
			//
			switch(entityMovedID) {
			case (1):
				//Andy!
				if(Rect.intersects(other.getBounds(), moved)) {
					entityMoved.setX((int) (0+Math.random()*1000));
					entityMoved.setY((int) (0+Math.random()*1000));
					Log.v("Collision", "Andy Collided");
				}
			case (2):
				//Goat
				if(Rect.intersects(other.getBounds(), moved)) {
					entityMoved.setX((int) (0+Math.random()*1000));
					entityMoved.setY((int) (0+Math.random()*1000));
					Log.v("Collision", "Collision Detected");
					goatBleet.start(); 
				}
			default:
				//Occurring A LOT!!!
				//Log.v("Collision", "UnknownID");
			}
		}
	}
}
