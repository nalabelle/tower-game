package com.comp380.towergame;

import android.util.Log;

public class GameThread extends Thread {
	private String tag = this.getClass().toString();
	private GameActivity context;
	private boolean running;
	
	private final static int MAX_FPS = 50;
	private final static int MAX_FRAME_SKIP = 5;
	private final static int FRAME_LENGTH = 1000 / MAX_FPS;

	public GameThread(GameActivity context) {
		super();
		this.context = context;
	}

	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public void run() {
		Log.v(tag, "starting");
		
		long startTime;
		int sleepTime = 0;
		int frameSkip;
		
		while (this.running && !this.isInterrupted()) {
			startTime = System.currentTimeMillis();
			frameSkip = 0;
			this.gameUpdate();
			this.context.gameSurfaceView.draw();
			long diffTime = System.currentTimeMillis() - startTime;
			sleepTime = (int) (FRAME_LENGTH - diffTime);
			
			if(sleepTime > 0) {
				//We're ahead, things are great. Let's take a breather.
				try {
					Thread.sleep(sleepTime);
				} catch(InterruptedException e) {
					//nothing.
				}
			}
			
			while(sleepTime < 0 && frameSkip < MAX_FRAME_SKIP && !this.isInterrupted()) {
				//We're behind, let's do some updates without drawing
				this.gameUpdate();
				
				sleepTime += FRAME_LENGTH;
				frameSkip++;
			}
		}
	}

	private void gameUpdate() {
		this.context.entityManager.updateAll();
		
		for(int i = 0; i < 6; i++) // Update Background
			for(int j = 0; j < 3; j++)
				this.context.backgroundArray[i][j].updateBackground();
	}
}
