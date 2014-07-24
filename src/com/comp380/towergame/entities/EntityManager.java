package com.comp380.towergame.entities;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.R;

public class EntityManager {
	private ArrayList<BaseEntity> entityStorage;
	private GameActivity context;

	public EntityManager(GameActivity gameActivity) {
		this.context = gameActivity;
		this.entityStorage = new ArrayList<BaseEntity>();
		
		//temporary Andy creation
		BaseEntity andy = new Andy(this, BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.player_jump));
		this.entityStorage.add(andy);
		
		//temporary Baddie creation
		BaseEntity badguy = new EvilGuy(this, BitmapFactory.decodeResource(gameActivity.getResources(), R.drawable.badguy));
		this.entityStorage.add(badguy);
	}
	
	//temporary
	public Andy getAndy() {
		if(this.entityStorage.get(0) instanceof Andy)
			return (Andy) this.entityStorage.get(0);
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
		for(BaseEntity entity : this.entityStorage){
			entity.update();
			this.context.getCollisionDetection().checkCollisions(entity);
			if(entity.getHealth() < 0) {
				toRemove.add(entity);
			}
		}
		for(BaseEntity removing : toRemove) {
			this.entityStorage.remove(removing);
		}
	}

	public void drawAll(Canvas canvas) {
		for(BaseEntity entity : this.entityStorage){
			entity.draw(canvas);
		}
	}

}
