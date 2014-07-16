package com.comp380.towergame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	//private GLSurfaceView glSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//testing gl renderer, ignore
		//glSurfaceView = new GLSurfaceView(this);
        //glSurfaceView.setRenderer(new GameRenderer(this));
        //setContentView(glSurfaceView);

		setContentView(new GameSurfaceView(this));
	}
}
