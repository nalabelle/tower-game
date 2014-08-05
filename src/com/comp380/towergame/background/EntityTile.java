package com.comp380.towergame.background;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.entities.BaseEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class EntityTile extends Tile {
	//not sure why this isn't in base tile class
	private Context context;
	private Class<BaseEntity> entityType;
	private boolean hasSpawned;
	
	public EntityTile(Context context,int x, int y, int xInG, int yInG, boolean solid, Bitmap b, Class<BaseEntity> type) {
		super(context, x, y, xInG, yInG, solid, b);
		this.context = context;
		this.entityType = type;
		this.hasSpawned = false;
	}
	
	//Override: nothing to draw.
	public void draw(Canvas canvas) {
		//The logic here is that if our tile is being drawn, it must be on the screen, or close to it.
		//It's kind of a hacky place to put it, but here it is for now
		
		//if this is the first time our tile is drawn, we should spawn our entity
		if(!this.hasSpawned)
			((GameActivity) this.context).getEntityManager().spawn(entityType, getTileOnScreenX(), getTileOnScreenY() - 5);
	}
}



