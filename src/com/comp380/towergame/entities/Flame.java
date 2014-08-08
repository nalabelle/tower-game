package com.comp380.towergame.entities;

import com.comp380.towergame.SoundManager;
import com.comp380.towergame.physics.Speed;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class Flame extends BaseEntity {
	private float speed = Speed.PROJECTILE;
	private boolean soundOption;
	public static final String PREFS_NAME = "gameConfig";

	public Flame(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 
				//Here be dragons
				//IF(andy is facing left) THEN (x = (-bitmap.width + andy.left)) ELSE (x = (andy.left + andy.width))
				(manager.getAndy().getFacing() < 0) ? 
						(-1 * bitmap.getWidth() + manager.getAndy().getBounds().left -1) : 
							(manager.getAndy().getBounds().width() + manager.getAndy().getBounds().left + 1),
				manager.getAndy().getY());
		this.velocityX = Math.signum(manager.getAndy().getFacing())*this.speed;
		
		this.isFlying = true;
		
		SharedPreferences settings = this.manager.getContext().getSharedPreferences(PREFS_NAME, 0);
    	soundOption = settings.getBoolean("soundOption", true);
		
		if (soundOption) {
			this.manager.getContext().getSoundEffects().play(SoundManager.fireballID, 1, 1, 1, 0, 1);
		}
	}
}