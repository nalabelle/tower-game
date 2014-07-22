package com.comp380.towergame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        setFont();      
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
    
    public void launch_game(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //finish();
    }
    

    
    public void launch_credits(View view){
    	Intent intent = new Intent(this, CreditsActivity.class);
    	startActivity(intent);
    }
    
    //Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	TextView version = (TextView) findViewById(R.id.tvVersion);
    	Button start = (Button) findViewById(R.id.bStart);
    	Button credits = (Button) findViewById(R.id.bCredits);
    	
    	version.setTypeface(font);
    	start.setTypeface(font);
    	credits.setTypeface(font);
    }
}
