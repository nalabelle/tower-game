package com.comp380.towergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.comp380.towergame.background.Background;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private String tag = this.getClass().toString();
	private boolean surfaceCreated = false;
	
	public GameSurfaceView(GameActivity context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
	}

	protected void onSizeChanged(int newX, int newY, int oldX, int oldY) {
		super.onSizeChanged(newX, newY, oldX, oldY);
		//GameActivity.GAME_MAX_HEIGHT = newY;
		//GameActivity.GAME_MAX_HEIGHT = newX;
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
		if (canvas == null) return;
		canvas.drawColor(Color.BLACK);
		
		for(int i = 0; i < 6; i++) //Draw background
			for(int j = 0; j < 3; j++)
				((GameActivity) this.getContext()).backgroundArray[i][j].draw(canvas);
		
		this.drawEntities(canvas);
	}
	
	private void drawEntities(Canvas canvas) {
		((GameActivity) this.getContext()).entityManager.drawAll(canvas);
	}
	
	/**
	 * Touch Screen event handler
	 * Handler a player touching the screen
	 * 
	 * Uses: Capturing gestures, touching entities
	 */
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		
		
		switch(action) {
			case (MotionEvent.ACTION_DOWN):
				//Touch
				event.getX();
				return true;
			case (MotionEvent.ACTION_UP):
				//Touch end
				if(event.getX() < GameActivity.GAME_MAX_WIDTH/3 && 
						(event.getY() > GameActivity.GAME_MAX_HEIGHT/3) &&
						(event.getY() < 2*GameActivity.GAME_MAX_HEIGHT/3)) {
					((GameActivity) this.getContext()).entityManager.getAndy().setX(
							((GameActivity) this.getContext()).entityManager.getAndy().getX()-10);	
				}
				if(event.getX() > 2*GameActivity.GAME_MAX_WIDTH/3 && 
						(event.getY() > GameActivity.GAME_MAX_HEIGHT/3) &&
						(event.getY() < 2*GameActivity.GAME_MAX_HEIGHT/3)) {
					((GameActivity) this.getContext()).entityManager.getAndy().setX(
							((GameActivity) this.getContext()).entityManager.getAndy().getX()+10);						
				}
				
				if(event.getY() < GameActivity.GAME_MAX_HEIGHT/3 && 
						(event.getX() > GameActivity.GAME_MAX_WIDTH/3) &&
						(event.getX() < 2*GameActivity.GAME_MAX_WIDTH/3)) {
					((GameActivity) this.getContext()).entityManager.getAndy().setY(
							((GameActivity) this.getContext()).entityManager.getAndy().getY()-10);						
				}
				if(event.getY() > 2*GameActivity.GAME_MAX_HEIGHT/3 && 
						(event.getX() > GameActivity.GAME_MAX_WIDTH/3) &&
						(event.getX() < 2*GameActivity.GAME_MAX_WIDTH/3)) {
					((GameActivity) this.getContext()).entityManager.getAndy().setY(
							((GameActivity) this.getContext()).entityManager.getAndy().getY()+10);						
				}

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
