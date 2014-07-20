package com.comp380.towergame;

import com.comp380.towergame.background.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private Background[][] backgroundArray = new Background[6][3];
	private String tag = this.getClass().toString();
	private boolean surfaceCreated = false;
	
	public GameSurfaceView(GameActivity context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);

		for(int i = 0; i < 6; i++) //init bg
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j] = new Background(this.getContext(),i*400,j*400,1);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(tag, "Surface Created");
		this.surfaceCreated = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	public void draw() {
		Canvas canvas;
		canvas = null;
		if(this.surfaceCreated == false) return;
		try {
			canvas = this.getHolder().lockCanvas();
			synchronized (this.getHolder()) {
				this.drawUpdate(canvas);
			} 
		} finally {
				if (canvas != null) {
					this.getHolder().unlockCanvasAndPost(canvas);
				}
		}
		
	}
	
	private void drawUpdate(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		//this needs to be written in an update() method,,, just put it here now for testing
		for(int i = 0; i < 6; i++) 
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j].updateBackground();
		
		
		
		for(int i = 0; i < 6; i++) //init bg
			for(int j = 0; j < 3; j++)
				backgroundArray[i][j].draw(canvas);

		//The below here is dragons. This should be offloaded to a different area in the game thread soon.
		int curX = ((GameActivity) this.getContext()).getAndy().getX();
		int curY = ((GameActivity) this.getContext()).getAndy().getY();

		Log.v(tag, "Location: "+curX+":"+curY);
		canvas.drawBitmap(((GameActivity) this.getContext()).getAndyTexture(), curX, curY, null);
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
				((GameActivity) this.getContext()).getAndy().setY(((GameActivity) this.getContext()).getAndy().getY()+10);
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
