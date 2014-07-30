package com.comp380.towergame.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tile
{
	private int tileOnScreenX, tileOnScreenY, tileXSpeed,tileInGX, tileInGY;  //TileInGX = tile in game x position
	private int boxSizeX, boxSizeY;
	private Bitmap bitmap;
	private boolean solid;
	
	public Tile(Context context,int x, int y, int xInG, int yInG, boolean solid, Bitmap b) //where xInG is x position in game
	{
		tileXSpeed = -8; //value should be 0
		tileOnScreenX = x;
		tileOnScreenY = y;
		tileInGX = xInG;
		tileInGY = yInG;
		bitmap = b;
		
		this.solid = solid; 
		
		boxSizeX = 16; //  Values here can be adjusted in code,,
		boxSizeY = 16; //  No need to update this in game since all tile sizes are the same
	
	}
	
	public void updateTile()
	{
		tileOnScreenX += tileXSpeed;
	}
	
	public boolean isSolid()
	{
		return solid;
	}
	
	public Bitmap getTileBitmap()
	{
		return bitmap;
	}
	
	public int getTileOnScreenX()
	{
		return tileOnScreenX;
	}
	
	public int getTileOnScreenY()
	{
		return tileOnScreenY;
	}
	
	public int getTileSpeed()
	{
		return tileXSpeed;
	}
	
	public int getTileInGX()
	{
		return tileInGX;
	}
	
	public int getTileInGY()
	{
		return tileInGY;
	}
	
	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}
	
	public void setBitmapTile(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	public void setTileOnScreenX(int tileOnScreenX)
	{
		this.tileOnScreenX = tileOnScreenX;
	}
	
	public void setTileOnScreenY(int tileOnScreenY)
	{
		this.tileOnScreenY = tileOnScreenY;
	}
	public void setTileInGX(int tileInGX)
	{
		this.tileInGX = tileInGX;
	}
	
	public void setTileInGY(int tileInGX)
	{
		this.tileInGY = tileInGX;
	}
	
	public void setTileXSpeed(int s)
	{
		tileXSpeed = s;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.bitmap, tileOnScreenX, tileOnScreenY, null);
	}
	
	/*
	 * Note: The getBounds for the Tile is
	 * using the on screen x and y values
	 * might have to change to in game
	 * later on.... not sure for now
	 */
	
	public Rect getBounds() 
	{
		return new Rect(this.getTileOnScreenX(), this.getTileOnScreenY(), this.getTileOnScreenX() + this.bitmap.getWidth(), this.getTileOnScreenY() + this.bitmap.getHeight());
	}
}



