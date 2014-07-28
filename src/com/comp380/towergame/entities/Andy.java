package com.comp380.towergame.entities;

import android.graphics.Bitmap;

public class Andy extends BaseEntity {
	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 25, 25);
		this.setID(1);
	}
}
