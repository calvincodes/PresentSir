package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SoundSettings extends Activity {
	
	RadioGroup sound_profiles;
	RadioButton vib,mel,vib_mel;
	Button save;
	TextView sound_title;
	
	int past_sound_pref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sound_settings_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    vib = (RadioButton) findViewById(R.id.vib_rb);	// 3
	    mel = (RadioButton) findViewById(R.id.mel_rb);	// 2
	    vib_mel = (RadioButton) findViewById(R.id.vib_mel_rb);	// 1
	    sound_title = (TextView) findViewById(R.id.sound_tv_ss);
	    
	    save = (Button) findViewById(R.id.save_sound_ss);
	    
	    vib.setTypeface(comic_font);
	    mel.setTypeface(comic_font);
	    vib_mel.setTypeface(comic_font);
	    save.setTypeface(comic_font);
	    sound_title.setTypeface(comic_font);
	    sound_title.setPaintFlags(sound_title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    
	    SharedPreferences sound_prefs_sp = getSharedPreferences("SoundPrefs",Context.MODE_PRIVATE);
	    final SharedPreferences.Editor edit_sound_prefs = sound_prefs_sp.edit();
	    
	    past_sound_pref = sound_prefs_sp.getInt("sound_pref", 1);
	    
	    if(past_sound_pref == 1){
	    	vib_mel.setChecked(true);
	    }else
	    	if(past_sound_pref == 2){;
		    	mel.setChecked(true);
		    }else
		    	if(past_sound_pref == 3){
			    	vib.setChecked(true);
			    }
	    
	    final Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
 		
	    vib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				call_vibrate();				
			}
		});
	    
	    mel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Ringtone r1 = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r1.play();			
			}
		});
	    
	    vib_mel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				call_vibrate();
				Ringtone r2 = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r2.play();		
			}
		});
	    
	    save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(vib_mel.isChecked())
					past_sound_pref = 1;
				else
				if(mel.isChecked())
					past_sound_pref = 2;
				else
				if(vib.isChecked())
					past_sound_pref = 3;
				
				edit_sound_prefs.putInt("sound_pref", past_sound_pref);
				edit_sound_prefs.commit();
				
				go_to_settings();
			}
		});
	    
	}
	
	protected void call_vibrate() {

		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(500);
		
	}

	@Override
    public void onBackPressed() {
		go_to_settings();
	}
	private void go_to_settings() {
		Intent home_screen = new Intent(SoundSettings.this, Settings.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
