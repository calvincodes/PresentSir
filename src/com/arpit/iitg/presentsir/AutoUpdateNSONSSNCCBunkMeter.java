package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.widget.TextView;

public class AutoUpdateNSONSSNCCBunkMeter extends Activity {

	int hours;
	String today;
	TextView auto_update_tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.auto_bm_count_update_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    SharedPreferences sound_prefs_sp = getSharedPreferences("SoundPrefs",Context.MODE_PRIVATE);
        
        int sound_val = sound_prefs_sp.getInt("sound_pref", 1);
        
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
 		Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
 		
        if(sound_val == 1){ // Vib + Mel
        	
	 		v.vibrate(1500); // Vibrate for 1000 milliseconds = 1.5 sec
	 		r.play(); // Play the current ringtone of the phone
        }else if(sound_val == 2){ // Mel only
        	r.play();
        }else if(sound_val == 3){ // Vib only
        	v.vibrate(1500);
        }
	    
	    hours = getIntent().getExtras().getInt("hours");
	    
	    auto_update_tv = (TextView) findViewById(R.id.auto_update_notification_tv);
	    auto_update_tv.setTypeface(comic_font);
	    
		SharedPreferences open_tt_to_incr_bm = getApplicationContext().getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_bunk_counter = open_tt_to_incr_bm.edit();
		
		String course = null;
		
		switch(open_tt_to_incr_bm.getInt("course_value", 0)){
			case 1: course = "NSO"; break;
			case 2: course = "NSS"; break;
			case 3: course = "NCC"; break;
			default: course = "N/A"; break;
		}
		
		String set_update_msg = "No Response!\nWe assumed you bunked the "+ course +" class. If you attended the class, decrease the bunk meter manually";
		
		auto_update_tv.setText(set_update_msg);
		
		int last_bunk_count = open_tt_to_incr_bm.getInt("bunk_count", 0); 
		edit_bunk_counter.putInt("bunk_count", (last_bunk_count+1));
		edit_bunk_counter.commit();
	    
	}
	
	@Override
    public void onBackPressed() {
		go_to_home();
	}

	private void go_to_home() {
		Intent home_screen = new Intent(AutoUpdateNSONSSNCCBunkMeter.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
