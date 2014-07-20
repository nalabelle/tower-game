package com.comp380.towergame.background;

import com.comp380.towergame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background
{
	private int backgroundX, backgroundY, backgroundSpeed;  
	private Bitmap bitmap;
	
	public Background(Context context,int x, int y, int imageNum) //where imageNum is background image
	{
		backgroundSpeed = -5; //value should be 0
		backgroundX = x;
		backgroundY = y;
		switch(imageNum)
		{
		case 1:
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1);
			break;
		default:
			break;
		}
	}
	
	public void updateBackground()
	{
		backgroundX += backgroundSpeed;
		if (backgroundX <= -400) //-160 for screen dim
			backgroundX += 2400;
	}
	
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	
	public int getBackgroundX()
	{
		return backgroundX;
	}
	
	public int getBackgroundY()
	{
		return backgroundY;
	}
	
	public int getBackgroundSpeed()
	{
		return backgroundSpeed;
	}
	
	public void setBitmapBackground(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	public void setBackgroundX(int bgX)
	{
		backgroundX = bgX;
	}
	
	public void setBackgroundY(int bgY)
	{
		backgroundY = bgY;
	}
	
	public void setBackgroundSpeed(int bgS)
	{
		backgroundSpeed = bgS;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(this.bitmap, backgroundX, backgroundY, null);
	}
	
}



