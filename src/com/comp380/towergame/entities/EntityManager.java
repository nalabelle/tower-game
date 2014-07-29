package com.comp380.towergame.entities;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.R;
import com.comp380.towergame.background.Tile;

public class EntityManager {
	private ArrayList<BaseEntity> entityStorage;
	private GameActivity context;

	public EntityManager(GameActivity gameActivity) {
		this.setContext(gameActivity);
		this.entityStorage = new ArrayList<BaseEntity>();
		
		//temporary Andy creation
		BaseEntity andy = new Andy(this, BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.player_jump));
		this.entityStorage.add(andy);
		
		//temporary Baddie creation
		BaseEntity badguy = new Goat(this, BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.badguy));
		this.entityStorage.add(badguy);
	}
	
	//temporary
	public Andy getAndy() {
		for(BaseEntity test : this.entityStorage) {
			if(test instanceof Andy)
				return (Andy) test;
		}
		return null;
	}

	public ArrayList<BaseEntity> getAll() {
		return this.entityStorage;
	}
	
	public ArrayList<Integer> getAllKeys() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(BaseEntity entity : this.entityStorage){
			array.add(entity.getID());
		}
		return array;
	}

	public void updateAll() {
		ArrayList<BaseEntity> toRemove = new ArrayList<BaseEntity>();
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.entityStorage);
		for(BaseEntity entity : safeIter){
			entity.update();
			//this.context.getCollisionDetection().checkCollisions(entity);
			//this will be done when things move, rather than every time.
			if(entity.getHealth() < 0) {
				toRemove.add(entity);
			}
		}
		for(BaseEntity removing : toRemove) {
			this.entityStorage.remove(removing);
		}
	}

	public void drawAll(Canvas canvas) {
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.entityStorage);
		
		Paint myPaint = new Paint();
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setColor(Color.RED);
		myPaint.setStrokeWidth(2);
		
		for(BaseEntity entity : safeIter){
			entity.draw(canvas);
			if(this.getContext().DEV_MODE)
				canvas.drawRect(entity.getBounds(), myPaint);
		}
	}

	public BaseEntity checkEntityToEntityCollisions(BaseEntity baseEntity, Point newPoint) {
		return this.getContext().getCollisionDetection().checkCollisions(baseEntity, newPoint);
	}
	
	public Tile checkEntityToTileCollisions(BaseEntity baseEntity,
			Point newPoint) {
		return this.getContext().getCollisionDetection().checkTileCollisions(baseEntity, newPoint);
	}
	
	public GameActivity getContext() {
		return context;
	}

	public void setContext(GameActivity context) {
		this.context = context;
	}

}
