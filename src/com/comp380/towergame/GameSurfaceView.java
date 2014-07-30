package com.comp380.towergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
		
		
		((GameActivity) this.getContext()).tileEngine.drawTiles(canvas);
		
		this.drawEntities(canvas);
		this.drawHUD(canvas);
	}
	
	private void drawEntities(Canvas canvas) {
		((GameActivity) this.getContext()).entityManager.drawAll(canvas);
	}
	
	//Draw Andy's HP and Score 
	//If buttons cause lag draw buttons
	//here and make them invisible in the gameView
	private void drawHUD(Canvas canvas) {
		if(((GameActivity) this.getContext()).entityManager.getAndy() == null)
			return;
		
		int hp =((GameActivity) this.getContext()).entityManager.getAndy().getHealth();
		int score = ((GameActivity) this.getContext()).entityManager.getAndy().getScore();
		
		Paint myPaint = new Paint();
		Typeface font = Typeface.createFromAsset(this.getContext().getAssets(), "font/IsomothPro.otf");
		myPaint.setTypeface(font);
		myPaint.setStyle(Paint.Style.FILL);
		myPaint.setColor(Color.WHITE);
		myPaint.setTextSize(160);
		
		canvas.drawText("HP    "+hp, 1200, 100, myPaint);
		canvas.drawText("Score   "+score, 200, 100, myPaint);
	}
}
