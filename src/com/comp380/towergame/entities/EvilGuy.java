package com.comp380.towergame.entities;

import android.content.Context;

public class EvilGuy extends BaseEntity {

	public EvilGuy(Context context) {
		//bitmap needs to be sliced up, and this is the wrong size.
		super(25, 25, 25, 25);
	}

}
