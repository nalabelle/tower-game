package com.comp380.towergame;

import com.comp380.towergame.entities.Andy;

import android.R.color;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread thread = null;
	private Andy andy;
	private String tag = this.getClass().toString();
	
	public GameSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		this.thread = new GameThread(getHolder(), this);
		setFocusable(true);
		
		this.andy = new Andy(BitmapFactory.decodeResource(getResources(), R.drawable.andy), 25, 25, 25, 25);
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
		int curX = this.andy.getX();
		int curY = this.andy.getY();
		
		Log.v(tag, "Location: "+curX+":"+curY);
		andy.draw(canvas);
		
		//andy movement

		if(curX < (canvas.getWidth() - this.andy.getSizeX() - 200)) {
			this.andy.setX(curX + 15);
			Log.v(tag, "Moving right");
			return;
		}
			
		if(curY < (canvas.getHeight() - this.andy.getSizeY() - 20)) {
			this.andy.setY(curY + 5);
			Log.v(tag, "Moving down");
			return;
		} else {
			this.andy.setX(0);
			this.andy.setY(0);
			return;
		}
	}

}
