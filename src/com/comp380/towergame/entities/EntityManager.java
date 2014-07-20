package com.comp380.towergame.entities;

import java.util.ArrayList;

import com.comp380.towergame.GameActivity;

public class EntityManager {
	private ArrayList<BaseEntity> entityStorage;

	public EntityManager(GameActivity gameActivity) {
		this.entityStorage = new ArrayList<BaseEntity>();
		
		//temporary Andy creation
		BaseEntity andy = new Andy();
		this.entityStorage.add(andy);
	}
	
	//temporary
	public Andy getAndy() {
		if(this.entityStorage.get(0) instanceof Andy)
			return (Andy) this.entityStorage.get(0);
		return null;
	}

}
