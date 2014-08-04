package com.comp380.towergame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager extends SoundPool {
	SoundPool sounds;
	Context context;
	int level;
	
	//level 1 resource IDs (use to play)
	public static int bleetID = 0;
	public static int screamID = 0;
	public static int fireballID = 0;
	public static int goatHowlID = 0;
	

	public SoundManager(Context context, int level) {
		super(20, AudioManager.STREAM_MUSIC, 0);
		this.context = context;
		this.level = level;
		
		//if (loadFinished()){
			loadMusic(level);
		//}
	
	}
	
	//maybe necessary
//	public boolean loadFinished(){		
//		this.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//			
//			@Override
//			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//				loaded = true;
//				Log.v(this.getClass().toString(), "Sound: finished loading");
//				
//			}
//		}); 
//		return loaded;
//	}
	
	//add files to pool
	public void loadMusic(int level){
		switch(level){
		
		case 1:
			//add sounds here, link them to an ResourceID to identify which you want to play
			bleetID = this.load(context, R.raw.bleet, 1);
			screamID = this.load(context, R.raw.scream, 1);
			fireballID = this.load(context, R.raw.fireball, 1);
			goatHowlID = this.load(context, R.raw.goathowl, 1);
			break;
			
			//add additional levels
		}		
	}
}
