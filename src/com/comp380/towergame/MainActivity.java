package com.comp380.towergame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	public static final String PREFS_NAME = "gameConfig";
	private boolean buttonOption;
	private boolean soundOption;
    MediaPlayer music = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        setFont();    
        
        //Much better way?
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        soundOption = true;
        buttonOption = true;
        editor.putBoolean("soundOption", true);
        editor.putBoolean("buttonOption", true);
        editor.putBoolean("devOption", false);
        editor.commit();  
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void launchGame(View view) {
    	if (soundOption) { endMusic(); }
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //finish();
    }
    
    public void launchCredits(View view){
    	if (soundOption) { endMusic(); }
    	Intent intent = new Intent(this, CreditsActivity.class);
    	startActivity(intent);
    }
    
    public void launchOptions(View view){
    	//Toast.makeText(this, "To be implemented...", Toast.LENGTH_LONG).show();
    	if (soundOption) { endMusic(); }
    	Intent intent = new Intent(this, OptionsActivity.class); //OptionsActivity
        Log.v("ALEX", ""+soundOption+buttonOption);
        startActivity(intent);
    }
    
    public void endMusic(){
    	if (music != null){
    		music.release();
    	}
    }
    
    public void playMusic(){
    	//music.setAudioStreamType(AudioManager))
		music = MediaPlayer.create(this, R.raw.music_main);
		if (soundOption) {
			music.start();
			Log.v("ALEX -- Main ", "starting music");
		}
    }
            @Override	protected void onResume() {		super.onResume();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		soundOption = settings.getBoolean("soundOption", true);
		buttonOption = settings.getBoolean("buttonOption", true);
		Log.v("ALEX -- Main ", "resumed, playMusic()"+soundOption+buttonOption);
		playMusic();	}
    	@Override    protected void onPause() {
		if (soundOption) {endMusic();}		super.onPause();	}

    //Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	TextView title = (TextView) findViewById(R.id.tvTitle);
    	TextView version = (TextView) findViewById(R.id.tvVersion);
    	Button start = (Button) findViewById(R.id.bStart);
    	Button credits = (Button) findViewById(R.id.bCredits);
    	Button options = (Button) findViewById(R.id.bOptions);
    	
    	title.setTypeface(font);
    	version.setTypeface(font);
    	start.setTypeface(font);
    	credits.setTypeface(font);
    	options.setTypeface(font);
    }
}
