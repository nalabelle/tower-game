package com.comp380.towergame.entities;

import com.comp380.towergame.R;

import android.content.Context;
import android.graphics.BitmapFactory;

public class EvilGuy extends BaseEntity {

	public EvilGuy(Context context) {
		//bitmap needs to be sliced up, and this is the wrong size.
		super(BitmapFactory.decodeResource(context.getResources(), R.drawable.badguy), 25, 25, 25, 25);
	}

}
