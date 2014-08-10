package com.comp380.towergame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VictoryActivity extends Activity {
	TextView window;
	MediaPlayer music = null;
	int height;
	Handler handler;
	Thread textThread;
	public static final String PREFS_NAME = "gameConfig";
	private boolean buttonOption;
	private boolean soundOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	soundOption = settings.getBoolean("soundOption", true);

		try {
			if (soundOption){
				music = MediaPlayer.create(this, R.raw.music_victory);
				music.setLooping(true);
			}
		} catch (Exception e) {
			Log.v(getLocalClassName(), "music failed");
			e.printStackTrace();
		}

		// obtain screen resolution
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		height = dm.heightPixels;
		setContentView(R.layout.activity_victory);

		// setup textview attributes
		LinearLayout ll = (LinearLayout) findViewById(R.id.llVictory);
		window = new TextView(VictoryActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER_HORIZONTAL);
		window.setText("VICTORY!\n\n\n You emerge from the Magic Tower victorious.\n After a long battle to gain your freedom,\nyou look forward to further adventures... ");
		window.setTextColor(0xFFFFFFFF);
		window.setShadowLayer(15, 15, 15, Color.BLACK);
		setFont();
		window.setTextSize(height / 12);
		window.setLayoutParams(params);
		ViewGroup.MarginLayoutParams par = (ViewGroup.MarginLayoutParams) window
				.getLayoutParams();
		par.setMargins(0, height, 0, 0);
		window.setLayoutParams(par);
		ll.addView(window);

		// Handle messages from textThread and update the UI
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle b = msg.getData();
				height = b.getInt("heightKey");
				ViewGroup.MarginLayoutParams par = (ViewGroup.MarginLayoutParams) window
						.getLayoutParams();
				par.setMargins(0, height, 0, 0);
				window.setLayoutParams(par);
			}
		};
		moveTextView(height);

	}

	private void moveTextView(final int intialHeight) {

		textThread = new Thread() {
			int h = intialHeight;
			int endHeight = intialHeight * -1; // end thread after text passes
												// through the top of screen

			// Thread slowly decreases the height of the text so it appears to
			// scroll upwards
			public void run() {
				while (!textThread.isInterrupted() && h > endHeight) {
					// unimplemented: launch another activity when reaching
					// endHeight, credits for example
					try {
						sleep(125);
					} catch (InterruptedException e) {
						Log.v(this.getClass().toString(), "sleep failed");
						e.printStackTrace();
						break;
					}
					try {
						Message box = handler.obtainMessage();
						Bundle b = new Bundle();
						h = h - 6;
						b.putInt("heightKey", h);
						box.setData(b);
						handler.sendMessage(box);
					} catch (Exception e) {
						Log.v(this.getClass().getName(),
								"height not transferring");
					}

				}
			}
		};
		textThread.setName("Victory text");
		textThread.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (soundOption) {music.start();}		
	}

	@Override
	protected void onPause() {
		if (soundOption) {music.pause();}
		textThread.interrupt();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (music != null) {
			music.release();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.victory, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Using a custom font (IsomothPro) from assets for display
	public void setFont() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"font/IsomothPro.otf");

		window.setTypeface(font);
	}
}
