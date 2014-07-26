package com.comp380.towergame.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.comp380.towergame.R;
import com.comp380.towergame.background.Tile;

public class Levels
{
	int levelNum,levelLength;
	Context context;
	Tile[][] level;
	
	Bitmap transTile,spikeTile,floorTile,blackTile,brickTile,cornerRightTile, cornerLeftTile,graniteTile,postTile,roofTile,wallLeftTile,wallRightTile;

	public Levels(Context context, int levelNum) //where xInG is x position in game
	{
		this.levelNum = levelNum;
		this.context = context;
		levelLength = 0;
		loadBitmap(this.levelNum);
	}
	
	public void loadBitmap(int l)
	{
		if(l < 4) //load world one tiles if in range
		{
			transTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_trans);
			spikeTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_spike);
			floorTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_ground_1);
			blackTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_black);
			brickTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_brick_1);
			cornerRightTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_corner_right_1);
			cornerLeftTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_corner_left_1);
			graniteTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_granite_1);
			postTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_post_1);
			roofTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_roof_1);
			wallRightTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_wall_right_1);
			wallLeftTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_wall_left_1);
		}
		//TODO write other "world" bitmaps
	}
	
	public int getLevelNum()
	{
		return levelNum;
	}
	
	public int getlevelLength()
	{
		return levelLength;
	}
	
	public Tile[][] getLevel()
	{
		switch(levelNum)
		{
			case 1: 
				levelLength = 200;
				level = new Tile[levelLength][14]; // total room
				for(int i = 0; i < levelLength; i++) //fill entire array w/ floor
					for(int j = 0; j < 14; j++)
					{
						
						if(j == 0 && i < 49)
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						else if(j == 0 && i < 114)
							level[i][j] = new Tile(this.context,0, 0, i, j, false, roofTile);
						else if(j == 0 && i < 199)
							level[i][j] = new Tile(this.context,0, 0, i, j, false, blackTile);
						else if(j == 1 && i < 28)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if(j == 1 && i < 48)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, roofTile);
						}
						else if(j == 1 && i > 115 && i < 122)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, roofTile);
						}
						else if(j == 1 && i < 199 && i >= 122)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if(j == 2 && i < 27)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if(j == 2 && i > 123 && i < 136)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, roofTile);
						}
						else if(j == 2 && i >= 136 && i < 168)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if(j == 2 && i >= 168)
						{
							level[i][j] = new Tile(this.context,0,0,i,j,true,roofTile);
						}
						else if(j == 3 && i <= 26)
						{
							level[i][j] = new Tile(this.context,0,0,i,j,true,roofTile);
						}
						else if(j == 3 && i >= 136 && i < 168)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, roofTile);
						}
						else if((j == 6 && i >= 61 && i < 68) || (j == 6 && i >= 73 && i < 76) || (j == 6 && i >= 78 && i < 81) || (j == 6 && i >= 85 && i < 102))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if(j == 6 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, cornerRightTile);
						}
						else if((j == 7 && i >= 51 && i < 61) || (j == 7 && i >= 103 && i < 108) || (j == 7 && i >= 170 && i < 175) || (j == 7 && i >= 178 && i < 200))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if(j == 7 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, wallRightTile);
						}
						else if((j == 8 && i < 5) || (j == 8 && i >= 44 && i < 51) || (j == 8 && i >= 109 && i < 122) || (j == 8 && i >= 161 && i < 170))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if(j == 8 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, wallRightTile);
						}
						else if(j == 9 && i < 5)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, brickTile);
						}
						else if(j == 9 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, wallRightTile);
						}

						else if((j == 9 && i >= 12 && i < 27))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if(j == 10 && i < 5)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, brickTile);
						}
						else if(j == 10 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, wallRightTile);
						}
						else if((j == 10 && i >= 12 && i < 27))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if((j == 10 && i == 11) || (j == 10 && i >= 27 && i < 29))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if((j == 11 && i < 5))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, brickTile);
						}
						else if(j == 11 && i == 84)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, wallRightTile);
						}
						else if((j == 11 && i >= 11 && i < 29))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						else if((j == 11 && i >= 5 && i < 13) || (j == 11 && i >= 5 && i < 13) || (j == 11 && i >= 29 && i < 84) || (j == 11 && i >= 122 && i < 178))
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, floorTile);
						}
						else if(j == 12)
						{
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile);
						}
						
						
						/*
						else if(j == 12)
							level[i][j] = new Tile(this.context,0, 0, i, j, true, blackTile); //on screen 
						else if(i >= 100)
							level[i][j] = new Tile(this.context,0, 0, i, j, false, roofTile); //on screen
							*/
						else 
							level[i][j] = new Tile(this.context,0, 0, i, j, false, transTile); //on screen
							
							
					}
				break;
			//case 2:
				//TODO create level 2 here
				//break;
			//case 3:
				//same here...
				//
				//
				//
			//case n:
			
		}
		
		return level;
	}
	
	
	
}


