package com.comp380.towergame;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

public class OptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_options);
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
    
    public void launchGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //finish();
    }
    
    //Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	CheckBox defaultButt = (CheckBox) findViewById(R.id.checkbox_defaultButt);
    	CheckBox oldButt = (CheckBox) findViewById(R.id.checkbox_old);
    	TextView version = (TextView) findViewById(R.id.tvVersion);
    	Button start = (Button) findViewById(R.id.bStart);
    	Button credits = (Button) findViewById(R.id.bCredits);
    	Button options = (Button) findViewById(R.id.bOptions);
    	
    	defaultButt.setTypeface(font);
    	oldButt.setTypeface(font);
    	version.setTypeface(font);
    	start.setTypeface(font);
    	credits.setTypeface(font);
    	options.setTypeface(font);
    }
    
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_defaultButt:
                if (checked) {
                	
                }
                else
                {
                    // Remove the meat
                }
                break;
            case R.id.checkbox_old:
                if (checked) {
                    // Cheese me
                }
                else {
                    // I'm lactose intolerant
                }
                break;
            // TODO: Veggie sandwich
        }
    }
}