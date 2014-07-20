package com.comp380.towergame.entities;

abstract class BaseEntity {
	
	private int x;
	private int y;
	
	private int sizeX;
	private int sizeY;
	
	public BaseEntity(int x, int y, int sizeX, int sizeY) {
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
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
	

}
