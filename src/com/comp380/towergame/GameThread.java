package com.comp380.towergame;

import android.util.Log;

public class GameThread extends Thread {
	private String tag = this.getClass().toString();
	private GameActivity context;
	private boolean running;

	public GameThread(GameActivity context) {
		super();
		this.context = context;
	}

	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public void run() {
		Log.v(tag, "starting");
		while (this.running) {
			this.context.gameSurfaceView.draw();
		}
	}
}
