package com.comp380.towergame;

import com.comp380.towergame.entities.Andy;

import com.comp380.towergame.background.*;
import android.R.color;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread thread = null;
	private Andy andy;
	
	private Background[][] backgroundArray = new Background[6][3];
	private String tag = this.getClass().toString();
	
	public GameSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		this.thread = new GameThread(getHolder(), this);
		setFocusable(true);
		
		this.andy = new Andy(this.getContext());
		for(int i = 0; i < 6; i++) //init bg
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j] = new Background(this.getContext(),i*400,j*400,1); 
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.thread.setRunning(true);
		this.thread.start();	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		this.thread.setRunning(false);
		while(this.thread.isAlive() == true) {
			try {
				this.thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		
		//this needs to be written in an update() method,,, just put it here now for testing
		for(int i = 0; i < 6; i++) 
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j].updateBackground();
		
		
		
		for(int i = 0; i < 6; i++) //init bg
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j].draw(canvas);
		
		int curX = this.andy.getX();
		int curY = this.andy.getY();
		
		Log.v(tag, "Location: "+curX+":"+curY);
		andy.draw(canvas);
		
		// Andy's Movement
		/*
		if(curX < (canvas.getWidth() - this.andy.getBoundingBoxX())) {
			this.andy.setX(curX + 15);
			Log.v(tag, "Moving right");
			return;
		}
		

		if(curY < (canvas.getHeight() - this.andy.getBoundingBoxY() - 20)) {
			this.andy.setY(curY + 5);
			Log.v(tag, "Moving down");
			return;
		} else {
			this.andy.setX(0);
			this.andy.setY(0);
			return;
		}
		*/
	}
	
	/**
	 * Touch Screen event handler
	 */
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		
		switch(action) {
			case (MotionEvent.ACTION_DOWN):
				//Touch
				return true;
			case (MotionEvent.ACTION_UP):
				//Touch end
				andy.setY(andy.getY()+10);
				return true;
			case (MotionEvent.ACTION_MOVE):
				//Contact movement
				return true;
			case (MotionEvent.ACTION_CANCEL):
				//Touch Canceled
				return true;
			case (MotionEvent.ACTION_OUTSIDE):
				//Movement outside screen element bounds
				return true;
			default: return super.onTouchEvent(event);
			
		}
	}

}
