package com.comp380.towergame.background;

import java.util.ArrayList;
import com.comp380.towergame.GameActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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


