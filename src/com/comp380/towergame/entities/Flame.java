package com.comp380.towergame.entities;

import com.comp380.towergame.R;
import com.comp380.towergame.SoundManager;
import com.comp380.towergame.physics.Speed;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Flame extends BaseEntity {
	private float speed = Speed.PROJECTILE;
	private boolean soundOption;
	public static final String PREFS_NAME = "gameConfig";
	
	private int animationCounter;
	
	Bitmap fireballFrame1;
	Bitmap fireballFrame2;

	public Flame(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 
				//Here be dragons
				//IF(andy is facing left) THEN (x = (-bitmap.width + andy.left)) ELSE (x = (andy.left + andy.width))
				(manager.getAndy().getFacing() < 0) ? 
						(-1 * bitmap.getWidth() + manager.getAndy().getBounds().left -1) : 
							(manager.getAndy().getBounds().width() + manager.getAndy().getBounds().left + 1),
				manager.getAndy().getY()+45);
		this.fireballFrame1 = bitmap;
		this.fireballFrame2 = BitmapFactory.decodeResource(this.manager.getContext().getResources(), R.drawable.fireball_2);
		if(manager.getAndy().getFacing() < 0) {
			this.facingRight = false;
			this.fireballFrame1 = this.reverseBitmap(this.fireballFrame1);
			this.fireballFrame2 = this.reverseBitmap(this.fireballFrame2);
		}
		this.texture = this.fireballFrame1;
		
		this.velocityX = Math.signum(manager.getAndy().getFacing())*this.speed;
		
		this.animationCounter = 0;
		
		this.isFlying = true;
		
		SharedPreferences settings = this.manager.getContext().getSharedPreferences(PREFS_NAME, 0);
    	soundOption = settings.getBoolean("soundOption", true);
		
		if (soundOption) {
			this.manager.getContext().getSoundEffects().play(SoundManager.fireballID, 1, 1, 1, 0, 1);
		}
	}
	
	private void changeImage() {
		if(this.texture == this.fireballFrame1) {
			this.texture = this.fireballFrame2;
		} else {
			this.texture = this.fireballFrame1;
		}
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.texture, this.point.x, this.point.y, null);
		this.animationCounter--;
		if(animationCounter <= 0) {
			this.changeImage();
			this.animationCounter = 5;
		}
	}
}