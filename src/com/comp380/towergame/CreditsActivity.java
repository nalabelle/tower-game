package com.comp380.towergame;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CreditsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//remove bars
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_credits);
		setFont();
	}
	
    //Using a custom font (IsomothPro) from assets for display
    public void setFont(){
    	Typeface font = Typeface.createFromAsset(getAssets(), "IsomothPro.otf");
    	
    	TextView title = (TextView) findViewById(R.id.tvCreditsTitle);
    	TextView nick = (TextView) findViewById(R.id.tvNick);
    	TextView anthony = (TextView) findViewById(R.id.tvAnthony);
    	TextView nik = (TextView) findViewById(R.id.tvNik);
    	TextView alex = (TextView) findViewById(R.id.tvAlex);
    	TextView summer = (TextView) findViewById(R.id.tvSummer);
    	
    	title.setTypeface(font, Typeface.BOLD);
    	nick.setTypeface(font);
    	anthony.setTypeface(font);
    	nik.setTypeface(font);
    	alex.setTypeface(font);
    	summer.setTypeface(font);
    }
}
