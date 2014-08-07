package com.comp380.towergame;

import android.app.Activity;
import android.content.Intent;
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
    MediaPlayer music = null;
    private int musicOption = 0;
	private int buttonOption = 0;
	private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        setFont();    
        Intent intent = getIntent();
        musicOption = intent.getIntExtra("music", 0);
		buttonOption = intent.getIntExtra("buttons", 0);
		Log.v("ALEX Main", ""+musicOption+buttonOption);
		
		if (musicOption == 0) {
			playMusic();
		}
		
        intent.putExtra("music", musicOption);
        intent.putExtra("buttons", buttonOption);
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
    	if (musicOption == 0) { endMusic(); }
        intent = new Intent(this, GameActivity.class);
        intent.putExtra("music", musicOption);
        intent.putExtra("buttons", buttonOption);
        startActivity(intent);
        //finish();
    }
    
    public void launchCredits(View view){
    	if (musicOption == 0) { endMusic(); }
    	intent = new Intent(this, CreditsActivity.class);
    	intent.putExtra("music", musicOption);
        intent.putExtra("buttons", buttonOption);
    	startActivity(intent);
    }
    
    public void launchOptions(View view){
    	//Toast.makeText(this, "To be implemented...", Toast.LENGTH_LONG).show();
    	if (musicOption == 0) { endMusic(); }
    	intent = new Intent(this, OptionsActivity.class); //OptionsActivity
    	intent.putExtra("music", musicOption);
        intent.putExtra("buttons", buttonOption);
        Log.v("ALEX", ""+musicOption+buttonOption);
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
		if (musicOption == 0) {
			music.start();
		}
    }
            @Override	protected void onResume() {		super.onResume();
		Intent intent = getIntent();
        musicOption = intent.getIntExtra("music", 0);
		buttonOption = intent.getIntExtra("buttons", 0);
		Log.v("ALEX -- Resumed", ""+musicOption+buttonOption);	}//////	@Override///	protected void onPause() {///		music.pause();///		super.onPause();///	}

	
    
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
