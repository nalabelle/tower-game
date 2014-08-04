package com.comp380.towergame.background;

import java.util.ArrayList;
import com.comp380.towergame.GameActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TileEngine
{
	private int x = 30;//22 
	private int y = 13; //y port on screen
	private int screenC = 30; //screen Constant
	private int levelLength;
	private Tile tileArray[][];
	private Tile sourceArray[][];
	private int speed;
	
	Context context;
	
	public TileEngine(Context context, Tile[][] source,int l) //where xInG is x position in game
	{
		
		tileArray = new Tile[x][y];
		sourceArray = source.clone();
		levelLength = l;
		//initiate tile array
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				tileArray[i][j] = sourceArray[i][j];
				
				if(tileArray[i][j] == null)
					continue;
				
				tileArray[i][j].setTileOnScreenX(i*90 - 90);
				tileArray[i][j].setTileOnScreenY(j*90);
				tileArray[i][j].setTileXSpeed(0);
				
			}
		}
		
		this.context = context;
		
	}
	
	public void drawTiles(Canvas canvas)
	{
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(tileArray[i][j] == null)
					continue;
				canvas.drawBitmap(tileArray[i][j].getTileBitmap(), tileArray[i][j].getTileOnScreenX(),tileArray[i][j].getTileOnScreenY(), null);
				
				//debug bounding drawing.
				if(((GameActivity) this.context).DEV_MODE && tileArray[i][j].isSolid()) {
					Paint myPaint = new Paint();
					myPaint.setStyle(Paint.Style.STROKE);
					myPaint.setColor(Color.BLUE);
					myPaint.setStrokeWidth(2);
					canvas.drawRect(tileArray[i][j].getBounds(), myPaint);
				}
			}
		}
	}
	
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setTileSpeed(int s)//delete
	{
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				//if(tileArray[i][j] == null)
					//continue;
				tileArray[i][j].setTileXSpeed(s);
			}
		}
	}
	
	public void check()
	{
		int scX1;
		int scX2;
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				scX1 = tileArray[i][j].getTileOnScreenX();
				if(scX1 < 90)//
				{
					continue;
				}
				
				if(i == 0)
				{
					scX2 = tileArray[x-1][j].getTileOnScreenX();
				}
				else
				{
					scX2 = tileArray[i-1][j].getTileOnScreenX();
				}	

				scX1 -= scX1 - scX2 - 90;
				//System.out.println("x size is: " + scX1);//
				tileArray[i][j].setTileOnScreenX(scX1);
			}
		}
	}
	
	
	public int getInGamePos()
	{
		return tileArray[0][0].getTileInGX();
	}
	
	public void updateTiles()
	{
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(tileArray[i][j] == null)
					continue;
				tileArray[i][j].setTileXSpeed(speed);
				tileArray[i][j].updateTile();
				
				/*
				 * 
				 * Scroll the level to the left!
				 * 
				 */
				if(tileArray[i][j].getTileOnScreenX() < -90)//90 is size of block... 
				{
					int scrX = tileArray[i][j].getTileOnScreenX();
					int scrY = tileArray[i][j].getTileOnScreenY(); // store on screen values
					int inGX = tileArray[i][j].getTileInGX();
					int inGY = tileArray[i][j].getTileInGY(); // store array location
					scrX += 2700; //screen width in block dim (note how 2070 is multiple of 90)
					

					
					
					//inGX2 += 90;
					//scrX -= inGX2 - scrX;
					
					if(inGX+screenC >= levelLength)//array out of bounds checking
						break;
					tileArray[i][j] = sourceArray[inGX+screenC][inGY]; //reading from array location

					
					//int inGX2 = tileArray[i-1][j].getTileOnScreenX();
					//System.out.println("x size is: " + scrX);//
					
					tileArray[i][j].setTileOnScreenX(scrX); // adjust on screen location
					tileArray[i][j].setTileOnScreenY(scrY);
					
				}
				
			}
		}
	}

	public ArrayList<Tile> getAllVisibleSolid() {
		ArrayList<Tile> newArray = new ArrayList<Tile>();
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(tileArray[i][j] == null)
					continue;
				if(!tileArray[i][j].isSolid())
					continue;
				if(tileArray[i][j].getTileOnScreenX() > 0 &&
					tileArray[i][j].getTileOnScreenX() < GameActivity.GAME_MAX_WIDTH &&
					tileArray[i][j].getTileOnScreenY() > 0 &&
					tileArray[i][j].getTileOnScreenY() < GameActivity.GAME_MAX_HEIGHT) {
					newArray.add(tileArray[i][j]);
				}
			}
		}
		return newArray;
	}
	
	
	
	
	
}


