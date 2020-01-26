package com.arpit.iitg.presentsir;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.text.format.Time;

public class NSO_NSS_NCC_BunkMeterCheck extends Activity {

	String today;
	int hours;
	String test;
	
	WakeLock wakeLock;
	
	Intent auto_bm_count_update;
	PendingIntent pi_auto_bm_count_update;
	AlarmManager mgr_auto_bm_count_update;
	
	AlertDialog bunk_alert_dialog;
	
	private Handler wakeLock_release_handler = new Handler();
	private Handler alert_box_dismiss_handler = new Handler();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.bunk_meter_check_page);
	    
	    // For testing
	    test = getIntent().getExtras().getString("test");
	
	    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
	    Calendar calendar = Calendar.getInstance();
	    today = dayFormat.format(calendar.getTime());	// Get today's day
	    
	    Time dtNow = new Time();
	    dtNow.setToNow();
	    hours = dtNow.hour;	// Get current hour
	    
	    // Check for false alarm
	    check_false_alarm();
	    
	    wakeLock_release_handler.postDelayed(new Runnable() {
			  @Override
			  public void run() {
				  wakeLock.release();
			  }
		}, (10*1000));
	    // 10 sec after the start of this activity, release the wakeLock
	    
	    alert_box_dismiss_handler.postDelayed(new Runnable() {
			  @Override
			  public void run() {
				  bunk_alert_dialog.dismiss();
			  }
		}, ((29*60*1000)+(55*1000)));
	    // 29 minutes, 55 seconds after the start of this activity, dismiss the alert box
	}

	private void check_false_alarm() {
		
		SharedPreferences open_tt_to_bm_check = getApplicationContext().getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		
		if(open_tt_to_bm_check.contains(today+"_eve6_30to8") && (open_tt_to_bm_check.getInt("bm_check", 0) == 1)){

			PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		    wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
	        wakeLock.acquire();
		    
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
	 		
	 		// ID - 501
	 		auto_bm_count_update = new Intent(getBaseContext(), AutoUpdateNSONSSNCCBunkMeter.class);
	 		auto_bm_count_update.putExtra("hours", hours);
	 		pi_auto_bm_count_update = PendingIntent.getActivity(getBaseContext(), 501, auto_bm_count_update, PendingIntent.FLAG_UPDATE_CURRENT);
	 		mgr_auto_bm_count_update = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
	 		mgr_auto_bm_count_update.set(AlarmManager.RTC, System.currentTimeMillis() + (30*60*1000), pi_auto_bm_count_update);
		    
	 		count_for_bm(today);
	 		
		}else{
			
			SharedPreferences false_pt_alarm_sp = getApplicationContext().getSharedPreferences("FalseNSONSSNCCAlarm",Context.MODE_PRIVATE);
			SharedPreferences.Editor edit_flase_alarm = false_pt_alarm_sp.edit();
			edit_flase_alarm.putInt("False", 1);
			edit_flase_alarm.commit();
			go_to_home();
		}
		
	}
	
	private void count_for_bm(String day) {
		
		SharedPreferences open_tt_to_bm_check = getApplicationContext().getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		
		String course = null;
		
		switch(open_tt_to_bm_check.getInt("course_value", 0)){
			case 1: course = "NSO"; break;
			case 2: course = "NSS"; break;
			case 3: course = "NCC"; break;
			default: course = "N/A"; break;
		}
		
		String alert_msg = "Did you BUNK the " + course + " class?";
		
		bunk_alert_dialog = new AlertDialog.Builder(this).setTitle("Bunked?")
        .setMessage(alert_msg)
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	cancel_auto_update_alarm();	// If anything clicked, cancel_the_auto_update
        	increase_bunk_count();
        	wakeLock_release_handler.removeCallbacksAndMessages(null);
        	alert_box_dismiss_handler.removeCallbacksAndMessages(null);
        	go_to_home();
        	// After the action, must go to home, so that all the pending intents can be set again
        }
    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	cancel_auto_update_alarm(); // If anything clicked, cancel_the_auto_update
        	wakeLock_release_handler.removeCallbacksAndMessages(null);
        	alert_box_dismiss_handler.removeCallbacksAndMessages(null);
        	go_to_home();
        	// After the action, must go to home, so that all the pending intents can be set again
        }
    }).show();
	}
	
	protected void cancel_auto_update_alarm() {
		
		try {
			Intent intent = new Intent(getBaseContext(), AutoUpdateNSONSSNCCBunkMeter.class);
			PendingIntent displayIntent = PendingIntent.getActivity(getApplicationContext(), 501, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alm = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
			
			if(displayIntent != null) {
				
				alm.cancel(displayIntent);
				displayIntent.cancel();
			}
	    } catch (Exception e) {
	    	// Do nothing
	    }
		
	}

	protected void increase_bunk_count() {
		
		SharedPreferences open_tt_to_incr_bm = getApplicationContext().getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_bunk_counter = open_tt_to_incr_bm.edit();
		
		int last_bunk_count = open_tt_to_incr_bm.getInt("bunk_count", 0); 
		edit_bunk_counter.putInt("bunk_count", (last_bunk_count+1));
		edit_bunk_counter.commit();
	}
	
	private void go_to_home() {
		Intent app_home_screen = new Intent(NSO_NSS_NCC_BunkMeterCheck.this, AppHome.class);
		app_home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(app_home_screen);
		finish();
	}

}