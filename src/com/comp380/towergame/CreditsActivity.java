package com.comp380.towergame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
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

// Credits activity can be launched from andy's death or from main menu.
// Music and text color changes depending source. "Death" key set from the 
// intent that started activity. The text of the credits loops on another
// thread which uses a handler to update the main UI TextView

public class CreditsActivity extends Activity {
	MediaPlayer music;
	MediaPlayer deathScream;
	TextView window;
	Thread textThread;
	public int cycle = 1;
	Handler handler = null;
	boolean andyDied = false;
	public static final String PREFS_NAME = "gameConfig";
	private boolean soundOption;
	
	// Handler messages are short and queued once every 5 seconds,
	// therefore leaking warning should not be an issue
	@SuppressLint("HandlerLeak")  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//remove bars
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		

		setContentView(R.layout.activity_credits);
		window = (TextView)findViewById(R.id.tvWindow);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	soundOption = settings.getBoolean("soundOption", true);
		
    	// Andy dead - text is red, display starts with "Gameover," 
    	//		       plays blood curdling scream and creeping music.
    	// Otherwise - textcolor is white and displays "Magic Tower,"
    	// 		       music is chill. 
		try {
			andyDied = getIntent().getExtras().getBoolean("death");
			if (andyDied){
				if (soundOption) {
					music = MediaPlayer.create(this, R.raw.music_gameover);
					deathScream = MediaPlayer.create(this, R.raw.andy_death_scream);
					deathScream.start();
				}
				window.setTextColor(Color.RED);
				window.setText("GAME OVER");
			}
		} catch (Exception e) {
			Log.v(this.getClass().toString(), "Getting death info from intent failed");
			e.printStackTrace();
		}
		
		if (!andyDied){
			if (soundOption) {music = MediaPlayer.create(this, R.raw.music_credits);}
		}
		
		setFont();
		
		// Handler allows the text cycle thread to communicate with the UI thread.
		// Whenever a message is sent from the text Thread, the UI updates the 
		// widget on screen.
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				  Bundle b = msg.getData();
				  window.setText(b.getString("data"));
			     }
			 };			
		startTextSwap();		
	}	
	
	// Separate thread to loop changes to textview 
	public void startTextSwap(){
		
		textThread = new Thread() {			
			public void run() {
				while (!textThread.isInterrupted()) {					
						try {
							sleep(5000); //pause length between swaps
						} catch (InterruptedException e) {
							Log.v(this.getClass().toString(), "text swap messed up");
							e.printStackTrace();
							break;
						}
						try {
							// Handler sends a message in the form of a bundle
							// to UI thread
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
		textThread.setName("Text Rotation"); // For thread observation
		textThread.start();
	}
	
	//activity & MediaPlayer lifecycle controls
    @Override
	protected void onResume() {
		super.onResume();
		if (soundOption) {music.start(); }
	}

	@Override
	protected void onPause() {
		if (soundOption) {music.pause(); }
		textThread.interrupt();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (soundOption) {
			music.release();
			if (andyDied){ deathScream.release(); }
		}
		super.onDestroy();
	}

	//Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	window.setTypeface(font);
    }
}