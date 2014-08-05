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

public class OptionsActivity extends Activity {
	private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove title bar from activity, must before setContentView
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_options);
        CheckBox defaultButt = (CheckBox) findViewById(R.id.checkbox_defaultButt);
    	CheckBox musicOn = (CheckBox) findViewById(R.id.checkbox_musicPlay);
        defaultButt.setChecked(true);
    	musicOn.setChecked(true);
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
    
    
    //Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "font/IsomothPro.otf");
    	
    	CheckBox defaultButt = (CheckBox) findViewById(R.id.checkbox_defaultButt);
    	CheckBox oldButt = (CheckBox) findViewById(R.id.checkbox_old);
    	CheckBox musicOn = (CheckBox) findViewById(R.id.checkbox_musicPlay);
    	CheckBox musicOff = (CheckBox) findViewById(R.id.checkbox_musicStop);
    	Button playButt = (Button) findViewById(R.id.button_playGame);
    	TextView version = (TextView) findViewById(R.id.tvVersion);
    	
    	defaultButt.setTypeface(font);
    	oldButt.setTypeface(font);
    	version.setTypeface(font);
    	musicOn.setTypeface(font);
    	musicOff.setTypeface(font);
    }
    
    
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
    	//CheckBox clickedBox = (CheckBox) findViewById(view.getId());
        boolean checked = ((CheckBox) view).isChecked();
        
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_defaultButt:
                if (checked) {
                	CheckBox oldButt = (CheckBox) findViewById(R.id.checkbox_old);
                	oldButt.setChecked(false);
                }
                else
                {
                	CheckBox oldButt = (CheckBox) findViewById(R.id.checkbox_old);
                	oldButt.setChecked(true);
                }
                break;
            case R.id.checkbox_old:
                if (checked) {
                	CheckBox defaultButt = (CheckBox) findViewById(R.id.checkbox_defaultButt);
                	defaultButt.setChecked(false);
                }
                else {
                	CheckBox defaultButt = (CheckBox) findViewById(R.id.checkbox_defaultButt);
                	defaultButt.setChecked(true);
                }
                break;
            case R.id.checkbox_musicPlay:
                if (checked) {
                	CheckBox musicStop = (CheckBox) findViewById(R.id.checkbox_musicStop);
                	musicStop.setChecked(false);
                }
                else {
                	CheckBox musicStop = (CheckBox) findViewById(R.id.checkbox_musicStop);
                	musicStop.setChecked(true);
                }
                break;
            case R.id.checkbox_musicStop:
                if (checked) {
                	CheckBox musicPlay = (CheckBox) findViewById(R.id.checkbox_musicPlay);
                	musicPlay.setChecked(false);
                }
                else {
                	CheckBox musicPlay = (CheckBox) findViewById(R.id.checkbox_musicPlay);
                	musicPlay.setChecked(true);
                }
                break;
            
            
            // TODO: Veggie sandwich
        }
    }//end check logic
    
    public void launchGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //finish();
    }
    
}
