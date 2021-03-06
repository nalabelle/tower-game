package com.comp380.towergame.entities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.comp380.towergame.CreditsActivity;
import com.comp380.towergame.GameActivity;
import com.comp380.towergame.R;
import com.comp380.towergame.SoundManager;
import com.comp380.towergame.background.Tile;
import com.comp380.towergame.physics.CollisionDetection.PointMap;

public class EntityManager {
	private ArrayList<BaseEntity> entityStorage;
	private GameActivity context;
	private String tag = ""+ this.getClass();
	private boolean soundOption;
	private boolean devOption;
	public static final String PREFS_NAME = "gameConfig";
	
	public enum entityTypes {
		ANDY, GOAT, FLAME;
	}

	public EntityManager(GameActivity gameActivity) {
		this.context = gameActivity;
		this.entityStorage = new ArrayList<BaseEntity>();
		SharedPreferences settings = this.getContext().getSharedPreferences(PREFS_NAME, 0);
    	soundOption = settings.getBoolean("soundOption", true);
    	devOption = settings.getBoolean("devOption", false);
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

	public void updateAll() {
		ArrayList<BaseEntity> toRemove = new ArrayList<BaseEntity>();
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.entityStorage);
		for(BaseEntity entity : safeIter){
			entity.update();
			if(entity.getHealth() < 0) {				
				toRemove.add(entity);
			}
		}
		//this specific number is used so we don't play sounds for things that die offscreen.
		//this number needs to match the number we kill them with in BaseEntity
		int deathNumber = -143;
		
		for(BaseEntity removing : toRemove) {
			this.entityStorage.remove(removing);

			if(removing instanceof Andy) {
				//Andy Died
				Log.v(tag, "Adny died, Hp =" +removing.getHealth());
				//Andy Scream
				if(soundOption) {
					Log.v("ALEX -- EM, remove andy", "Scream");
					this.context.getSoundEffects().play(SoundManager.screamID, 1, 1, 1, 0, 1);
				}
				Intent intent = new Intent(this.context, CreditsActivity.class);
				intent.putExtra("death", true);
		    	this.context.startActivity(intent);
		    	context.onPause();
		    	context.finish();
		    	this.context.toggleGameThread(false);
		    	break;
			}
			if(removing instanceof Goat) {
				//Goat Died poor goat.  NO SCREW THE GOAT, IT HAD IT COMING!!!
				if(soundOption && removing.getHealth() != deathNumber) {
					this.context.getSoundEffects().play(SoundManager.goatHowlID, 1, 1, 1, 0, 1);
				}
		    	break;
			}
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
			if(devOption)
				canvas.drawRect(entity.getBounds(), myPaint);
		}
	}

	public BaseEntity checkEntityToEntityCollisions(BaseEntity baseEntity, Point newPoint) {
		return this.getContext().getCollisionDetection().checkCollisions(baseEntity, newPoint);
	}
	
	public HashMap<PointMap, Tile> checkEntityToTileCollisions(BaseEntity baseEntity,
			Point newPoint, float velocityX, float velocityY) {
		return this.getContext().getCollisionDetection().checkTileCollisions(baseEntity, newPoint, velocityX, velocityY);
	}
	
	public GameActivity getContext() {
		return context;
	}
	
	public void normalizeMovement(float f) {
		ArrayList<BaseEntity> safeIter = new ArrayList<BaseEntity>(this.entityStorage);
		for(BaseEntity entity : safeIter){
			if(entity instanceof Andy)
				continue;				
			entity.point.x = (int) (entity.point.x + f);
		}
	}

	public void spawn(EntityManager.entityTypes entityType, int tileOnScreenX, int tileOnScreenY) {
		if(entityType == EntityManager.entityTypes.ANDY) {
			BaseEntity andy = new Andy(this, BitmapFactory.decodeResource(this.context.getResources(), R.drawable.player_jump));
			this.entityStorage.add(andy);
		}
		
		if(entityType == EntityManager.entityTypes.GOAT) {
			BaseEntity badguy = new Goat(this, BitmapFactory.decodeResource(this.context.getResources(), R.drawable.badguy), tileOnScreenX, tileOnScreenY);
			this.entityStorage.add(badguy);
		}
	}

}
