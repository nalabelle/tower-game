package com.comp380.towergame.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Andy {
	private Bitmap bitmap;
	
	private int x;
	private int y;
	
	private int sizeX;
	private int sizeY;
	
	public Andy(Bitmap b, int x, int y, int sizeX, int sizeY) {
		this.bitmap = b;
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public Bitmap getBitmap() {
		return this.bitmap;
	}
	public void setBitmap(Bitmap b) {
		this.bitmap = b;
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
	
	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.bitmap, this.x, this.y, null);
	}
}
