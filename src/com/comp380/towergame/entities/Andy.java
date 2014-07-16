package com.comp380.towergame.entities;

import com.comp380.towergame.R;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Andy extends BaseEntity {
	public Andy(Context context) {
		super(BitmapFactory.decodeResource(context.getResources(), R.drawable.andy), 25, 25, 25, 25);
	}
}
