package com.comp380.towergame;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Group5: Nik
 * 
 * The main activity launches the game's title screen
 * Start, Load, Options, Quit  (HighScore? in options?)
 * 
 * Question: Do we ever return here? 
 * 		Quit from Gameplay? 
 * 		return from interrupt (home button, call, etc.)?
 */

public class MainActivity extends Activity {
    MediaPlayer music = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        setFont();        
        playMusic();
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
    	endMusic();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //finish();
    }
    
    public void launchCredits(View view){
    	endMusic();
    	Intent intent = new Intent(this, CreditsActivity.class);
    	startActivity(intent);
    }
    
    public void launchOptions(View view){
    	//Toast.makeText(this, "To be implemented...", Toast.LENGTH_LONG).show();
    	endMusic();
    	Intent intent = new Intent(this, OptionsActivity.class);
    	startActivity(intent);
    }
    
    public void endMusic(){
    	if (music != null){
    		music.release();
    	}
    }
    
    public void playMusic(){
    	//music.setAudioStreamType(AudioManager)
		music = MediaPlayer.create(this, R.raw.music_main);
		music.start();
    }
        
//    @Override
//	protected void onResume() {
//		super.onResume();
//    	music.start();
//	}
//
//	@Override
//	protected void onPause() {
//		music.pause();
//		super.onPause();
//	}

	
    
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
