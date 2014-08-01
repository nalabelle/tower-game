package com.comp380.towergame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CreditsActivity extends Activity {
	MediaPlayer music;
	TextView window;
	Thread textThread;
	public int cycle = 1;
	Handler handler = null;
	boolean andyDied = false;
	
	 // TO DO: find another song instead of gameover for viewing credits
	// from main menu instead of death
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//remove bars
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		music = MediaPlayer.create(this, R.raw.gameover);

		setContentView(R.layout.activity_credits);
		window = (TextView)findViewById(R.id.tvWindow);
		
		try {
			andyDied = getIntent().getExtras().getBoolean("death");
			if (andyDied){
				window.setTextColor(Color.RED);
				window.setText("GAME OVER");
			}
		} catch (Exception e) {
			Log.v(this.getClass().toString(), "Getting death info from intent failed");
			e.printStackTrace();
		}		
		
		setFont();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				  Bundle b = msg.getData();
				  window.setText(b.getString("data"));
			     }
			 };
			
		startTextSwap();
		
	}	
	
	public void startTextSwap(){
		
		textThread = new Thread() {			
			public void run() {
				while (!textThread.isInterrupted()) {					
						try {
							sleep(5000);
						} catch (InterruptedException e) {
							Log.v(this.getClass().toString(), "text swap messed up");
							e.printStackTrace();
							break;
						}
						try {
							Message box = handler.obtainMessage();
							Bundle b = new Bundle();
							switch (cycle){
								case 0: 
									b.putString("data", "Magic Tower");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								case 1: 
									b.putString("data", "Developed By:");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								case 2:
									b.putString("data", "Nickolas Monson");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								case 3:
									b.putString("data", "Alex Swanson");
									box.setData(b);
							     	handler.sendMessage(box);
									break;
								case 4:								
									b.putString("data", "Anthony Sager");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								case 5:
									b.putString("data", "Xintong \"Summer\" Shi");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								case 6:
									b.putString("data", "Nik LaBelle");
							         box.setData(b);
							         handler.sendMessage(box);
									break;
								default:
									cycle = 0;
									break;
							}
						} catch (Exception e) {
							Log.v(this.getClass().toString(), "Second thread handlers messed up");
							e.printStackTrace();
						}
						cycle++;
				}
			}
		};
		textThread.start();
	}
	
	//activity & MediaPlayer lifecycle controls
    @Override
	protected void onResume() {
		super.onResume();
		music.start(); //plays or resumes paused music
	}

	@Override
	protected void onPause() {
		music.pause();
		textThread.interrupt();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
    	music.release();
		super.onDestroy();
	}



	//Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	window.setTypeface(font);
    }
}
