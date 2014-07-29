package com.comp380.towergame.background;

import android.content.Context;
import android.graphics.Canvas;

public class TileEngine
{
	private int x = 23;//22
	private int y = 13; //y port on screen
	private int screenC = 23; //screen Constant
	private int levelLength;
	private Tile tileArray[][];
	private Tile sourceArray[][];
	
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
			}
		}
	}
	
	public void setTileSpeed(int s)
	{
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(tileArray[i][j] == null)
					continue;
				tileArray[i][j].setTileXSpeed(s);
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
					scrX += 2070; //screen width in block dim (note how 2070 is multiple of 90)
					
					if(inGX+screenC >= levelLength)//array out of bounds checking
						break;
					tileArray[i][j] = sourceArray[inGX+screenC][inGY]; //reading from array location

					tileArray[i][j].setTileOnScreenX(scrX); // adjust on screen location
					tileArray[i][j].setTileOnScreenY(scrY);
					
				}
				
			}
		}
	}
	
	
	
	
	
}


