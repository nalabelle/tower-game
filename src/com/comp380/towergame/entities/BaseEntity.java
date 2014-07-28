package com.comp380.towergame.entities;

import com.comp380.towergame.GameActivity;
import com.comp380.towergame.physics.Velocity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class BaseEntity {
	private int ID;
	private EntityManager manager;
	private Bitmap texture;
	
	private Velocity velocity;
	private boolean onGround = false;
	
	private int x;
	private int y;
	
	private int sizeX;
	private int sizeY;
	
	private int health;
	
	public BaseEntity(EntityManager manager, Bitmap texture, int x, int y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		//texture.getScaledWidth(DisplayMetrics.DENSITY_XHIGH) ?
		this.sizeX = texture.getWidth() * GameActivity.GRAPHIC_SCALING;
		this.sizeY = texture.getHeight() * GameActivity.GRAPHIC_SCALING;
		this.setManager(manager);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getBoundingBoxX() {
		return sizeX;
	}

	public void setBoundingBoxX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getBoundingBoxY() {
		return sizeY;
	}

	public void setBoundingBoxY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Bitmap getTexture() {
		return texture;
	}

	public void setTexture(Bitmap texture) {
		this.texture = texture;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.texture, this.x, this.y, null);
	}
	
	public void update() {
		if(!this.onGround) {
			
		}
	}

	public Rect getBounds() {
		return new Rect(this.getX(), this.getY(), this.getX() + this.getBoundingBoxX(), this.getY()+this.getBoundingBoxY());
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
