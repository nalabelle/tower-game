package com.comp380.towergame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	private SurfaceHolder surfaceHolder;
	private GameSurfaceView surfaceView;
	private boolean running;

	public GameThread(SurfaceHolder surfaceHolder, GameSurfaceView surfaceView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.surfaceView = surfaceView;
	}

	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public void run() {
		Canvas canvas;
		while (this.running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (this.surfaceHolder) {
					this.surfaceView.draw(canvas);		
				}
			} finally {
				if (canvas != null) {
					this.surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
