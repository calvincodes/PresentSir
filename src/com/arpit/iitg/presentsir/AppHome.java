package com.arpit.iitg.presentsir;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class AppHome extends Activity {
	
	TextView edit_tt_tv, view_next_tv, edit_course_tt;
	Button testing_tv1, testing_tv2, use_template, add_pt, add_nso_nss_ncc;
	Button select_pt_nso_to_view;
	String today;
	int hours;
	int minutes;
	
	TextView whats_next, my_tt, bunk_stats, settings, credits;
	TextView copyrights;
	
	Intent[] bm_mon = new Intent[10];
	PendingIntent[] bm_mon_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_mon_before = new AlarmManager[10];
	PendingIntent[] bm_mon_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_mon_after = new AlarmManager[10];
	
	Intent[] bm_tues = new Intent[10];
	PendingIntent[] bm_tues_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_tues_before = new AlarmManager[10];
	PendingIntent[] bm_tues_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_tues_after = new AlarmManager[10];
	
	Intent[] bm_wed = new Intent[10];
	PendingIntent[] bm_wed_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_wed_before = new AlarmManager[10];
	PendingIntent[] bm_wed_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_wed_after = new AlarmManager[10];
	
	Intent[] bm_thurs = new Intent[10];
	PendingIntent[] bm_thurs_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_thurs_before = new AlarmManager[10];
	PendingIntent[] bm_thurs_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_thurs_after = new AlarmManager[10];
	
	Intent[] bm_fri = new Intent[10];
	PendingIntent[] bm_fri_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_fri_before = new AlarmManager[10];
	PendingIntent[] bm_fri_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_fri_after = new AlarmManager[10];
	
	Intent[] bm_pt = new Intent[10];
	PendingIntent[] bm_pt_pending_intent_before = new PendingIntent[10];
	AlarmManager[] mgr_bm_pt_before = new AlarmManager[10];
	PendingIntent[] bm_pt_pending_intent_after = new PendingIntent[10];
	AlarmManager[] mgr_bm_pt_after = new AlarmManager[10];
	// Even index of each are for Morning PT session, eg: bm_pt[0] - is for Monday morning, bm_pt[2] - is for Tues morning
	// Odd index of each are for Evening PT session , eg bm_pt[1] - is for Monday, evening, bm_pt[4] - is for Tues evening
	
	Intent[] bm_nso_nss_ncc = new Intent[5];
	PendingIntent[] bm_nso_nss_ncc_pending_intent_before = new PendingIntent[5];
	AlarmManager[] mgr_bm_nso_nss_ncc_before = new AlarmManager[5];
	PendingIntent[] bm_nso_nss_ncc_pending_intent_after = new PendingIntent[5];
	AlarmManager[] mgr_bm_nso_nss_ncc_after = new AlarmManager[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_home_page);
		
		android.app.ActionBar actionBar = getActionBar();
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
		
		check_empty_bm_day_lists();	// Check if any of the bm_day list is empty. If yes, delete that SharedPreference
		check_empty_day_lists();	// Check if any of the day list is empty. If yes, delete that SharedPreference
		
		start_bm_pending_intents();	// Set all pending intents whenever we reach the home
		// Course Alarm IDs: 0 - 100
		
		start_pt_pending_intent();
		// PT Alarm Ids: 201 - 220
		
		start_nso_nss_ncc_pending_intent();
		// PT Alarm Ids: 401 - 410
		
		check_false_alarm();	// If we are reaching home because of a false alarm, we need to exit.
		// We will exit after calling all other functions, so that an attempt to set the alarms in the right manner can be taken.
		
		check_false_pt_alarm();
		
		check_false_nso_alarm();
		
		refresh();
		// Call all functions once again - safe side
		
		check_restart();
		// If the app starts because of phone reboot, exit the app.
		// App start on reboot is required to set all the pending intents.
		
		whats_next = (TextView) findViewById(R.id.next_class_tv_ahp);
		my_tt = (TextView) findViewById(R.id.my_tt_tv_ahp);
		bunk_stats = (TextView) findViewById(R.id.bunk_stats_tv_ahp);
		settings = (TextView) findViewById(R.id.settings_tv_ahp);
		credits = (TextView) findViewById(R.id.credits_tv_ahp);
		copyrights = (TextView) findViewById(R.id.copyright_tv_ahp);
		
		whats_next.setTypeface(comic_font);
		my_tt.setTypeface(comic_font);
		bunk_stats.setTypeface(comic_font);
		settings.setTypeface(comic_font);
		credits.setTypeface(comic_font);
		copyrights.setTypeface(comic_font);
		
		whats_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_course_list_empty(1);
			}
		});
		
		my_tt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent my_tt_screen = new Intent(AppHome.this, MyTimeTable.class);
				startActivity(my_tt_screen);
			}
		});
		
		settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent settings_screen = new Intent(AppHome.this, Settings.class);
				startActivity(settings_screen);
			}
		});
		
		bunk_stats.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_course_list_empty(2);
			}
		});
		
		credits.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent credits_screen = new Intent(AppHome.this, Credits.class);
				startActivity(credits_screen);
			}
		});

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	protected void check_if_course_list_empty(int i) {

		SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    int course_counter = course_list_sp.getInt("counter",0);
	    
	    if(course_counter != 0){
	    	
	    	if(i == 1){
	    		Intent view_next_screen = new Intent(AppHome.this, ViewTodayTT.class);
				startActivity(view_next_screen);
	    	}
	    	
	    	if(i == 2){
	    		Intent bunk_stats_screen = new Intent(AppHome.this, BunkMeterStats.class);
				startActivity(bunk_stats_screen);
	    	}
	    	
    	}else{
    		
    		BuildAlertNoCourseToShow();
    	}
	}

	private void BuildAlertNoCourseToShow() {

		new AlertDialog.Builder(this).setTitle("Empty Course List")
        .setMessage(getResources().getString(R.string.no_courses_to_show_msg_whats_next_bunk_stats))
        .setCancelable(true)
        .setPositiveButton("OK", null)
        .show();
		
	}

	private void refresh() {
		check_empty_bm_day_lists();	
		check_empty_day_lists();	
		start_bm_pending_intents();	
		start_pt_pending_intent();
		start_nso_nss_ncc_pending_intent();
		check_false_alarm();
		check_false_pt_alarm();
		check_false_nso_alarm();
	}
	
	private void check_restart() {
		
		SharedPreferences restart_sp = getApplicationContext().getSharedPreferences("Restart",Context.MODE_PRIVATE);
		
		int restart = restart_sp.getInt("Restart", 0);
		
		SharedPreferences.Editor edit_flase_alarm = restart_sp.edit();
		edit_flase_alarm.putInt("Restart", 0);
		edit_flase_alarm.commit();
		
		if(restart == 1){
			finish();
            System.exit(0);
		}
		
	}

	private void check_false_alarm() {
		
		SharedPreferences false_alarm_sp = getApplicationContext().getSharedPreferences("FalseAlarm",Context.MODE_PRIVATE);
		
		int false_al = false_alarm_sp.getInt("False", 0);
		
		SharedPreferences.Editor edit_flase_alarm = false_alarm_sp.edit();
		edit_flase_alarm.putInt("False", 0);
		edit_flase_alarm.commit();
		
		if(false_al == 1){
			finish();
            System.exit(0);
		}
		
	}
	
	private void check_false_pt_alarm() {
		
		SharedPreferences false_pt_alarm_sp = getApplicationContext().getSharedPreferences("FalsePTAlarm",Context.MODE_PRIVATE);
		
		int false_al = false_pt_alarm_sp.getInt("False", 0);
		
		SharedPreferences.Editor edit_flase_alarm = false_pt_alarm_sp.edit();
		edit_flase_alarm.putInt("False", 0);
		edit_flase_alarm.commit();
		
		if(false_al == 1){
			finish();
            System.exit(0);
		}
		
	}
	
	private void check_false_nso_alarm() {
		
		SharedPreferences false_pt_alarm_sp = getApplicationContext().getSharedPreferences("FalseNSONSSNCCAlarm",Context.MODE_PRIVATE);
		
		int false_al = false_pt_alarm_sp.getInt("False", 0);
		
		SharedPreferences.Editor edit_flase_alarm = false_pt_alarm_sp.edit();
		edit_flase_alarm.putInt("False", 0);
		edit_flase_alarm.commit();
		
		if(false_al == 1){
			finish();
            System.exit(0);
		}
		
	}

	private void check_empty_day_lists() {
		
		String day = null;
		String slot_to_append = null;
		SharedPreferences days_check = getSharedPreferences("DaysUpdated",Context.MODE_PRIVATE);
		
		for(int i = 0; i <= 4; i++){
			
			int day_check_flag = 0;
			// flag to be initialized for each day.
			
			switch(i){
				case 0: day = "Monday"; break;
				case 1: day = "Tuesday"; break;
				case 2: day = "Wednesday"; break;
				case 3: day = "Thursday"; break;
				case 4: day = "Friday"; break;
			}
			
			if(days_check.contains(day)){
				// If the day is there in the bm_days list, then check if it contains any slot or no.
				
				SharedPreferences day_slot_contain_check = getSharedPreferences(day,Context.MODE_PRIVATE);
				
				for(int j = 1; j <= 10; j++){
					
					switch(j){
						case 1: slot_to_append = "_8to9"; break;
						case 2: slot_to_append = "_9to10"; break;
						case 3: slot_to_append = "_10to11"; break;
						case 4: slot_to_append = "_11to12"; break;
						case 5: slot_to_append = "_12to1"; break;
						case 6: slot_to_append = "_1to2"; break;
						case 7: slot_to_append = "_2to3"; break;
						case 8: slot_to_append = "_3to4"; break;
						case 9: slot_to_append = "_4to5"; break;
						case 10: slot_to_append = "_5to6"; break;
					}
					
					if(day_slot_contain_check.contains("name"+slot_to_append)){
						// If the bm_day specific list, contains any slot, then set the bm_check_flag and break the loop
						day_check_flag = 1;
						break;
					}
				}
				
				if(day_check_flag == 0){
					// After the loop of slot check, if the bm_check_flag is not set, then delete the bm_day shared preference
					// and also remove it from the bm_day_list
					days_check.edit().remove(day).commit();
					day_slot_contain_check.edit().clear().commit();
				}
			}// end of if(bm_days_check.contains(day))
		}// end of for(int i = 0; i <= 4; i++)
	}

	private void check_empty_bm_day_lists() {
		
		String day = null;
		String slot_to_append = null;
		SharedPreferences bm_days_check = getSharedPreferences("BMDaysUpdated",Context.MODE_PRIVATE);
		
		for(int i = 0; i <= 4; i++){
			
			int bm_check_flag = 0;
			// flag to be initialized for each day.
			
			switch(i){
				case 0: day = "Monday"; break;
				case 1: day = "Tuesday"; break;
				case 2: day = "Wednesday"; break;
				case 3: day = "Thursday"; break;
				case 4: day = "Friday"; break;
			}
			
			if(bm_days_check.contains(day)){
				// If the day is there in the bm_days list, then check if it contains any slot or no.
				
				SharedPreferences bm_day_slot_contain_check = getSharedPreferences("bm_"+day,Context.MODE_PRIVATE);
				
				for(int j = 1; j <= 10; j++){
					
					switch(j){
						case 1: slot_to_append = "_8to9"; break;
						case 2: slot_to_append = "_9to10"; break;
						case 3: slot_to_append = "_10to11"; break;
						case 4: slot_to_append = "_11to12"; break;
						case 5: slot_to_append = "_12to1"; break;
						case 6: slot_to_append = "_1to2"; break;
						case 7: slot_to_append = "_2to3"; break;
						case 8: slot_to_append = "_3to4"; break;
						case 9: slot_to_append = "_4to5"; break;
						case 10: slot_to_append = "_5to6"; break;
					}
					
					if(bm_day_slot_contain_check.contains("name"+slot_to_append)){
						// If the bm_day specific list, contains any slot, then set the bm_check_flag and break the loop
						bm_check_flag = 1;
						break;
					}
				}
				
				if(bm_check_flag == 0){
					// After the loop of slot check, if the bm_check_flag is not set, then delete the bm_day shared preference
					// and also remove it from the bm_day_list
					bm_days_check.edit().remove(day).commit();
					bm_day_slot_contain_check.edit().clear().commit();
				}
			}// end of if(bm_days_check.contains(day))
		}// end of for(int i = 0; i <= 4; i++)
	}

	private void start_bm_pending_intents() {
		
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
	    Calendar calendar = Calendar.getInstance();
	    today = dayFormat.format(calendar.getTime());	// Get today's day
	    
	    Time dtNow = new Time();
	    dtNow.setToNow();
	    hours = dtNow.hour;	// Get current hour
	    minutes = dtNow.minute; // Get current minute
	    
			SharedPreferences open_bm_monday_file = getApplicationContext().getSharedPreferences("bm_Monday",Context.MODE_PRIVATE);
			
			if(today.equals("Monday"))
				// If today is Monday itself, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 0, 6);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 6, 5);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 5, 4);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 4, 3);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 3, 2);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 2, 1);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Monday
				check_all_slots_for_bm_mon(open_bm_monday_file, 1, 0);

		// All the BM alarms of Monday are set.
			
			SharedPreferences open_bm_tuesday_file = getApplicationContext().getSharedPreferences("bm_Tuesday",Context.MODE_PRIVATE);
			
			if(today.equals("Tuesday"))
				// If today is Tuesday itself, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 0, 6);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 6, 5);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 5, 4);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 4, 3);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 3, 2);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 2, 1);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Tuesday
				check_all_slots_for_bm_tues(open_bm_tuesday_file, 1, 0);

		// All the BM alarms of Tuesday are set.
			
			SharedPreferences open_bm_wednesday_file = getApplicationContext().getSharedPreferences("bm_Wednesday",Context.MODE_PRIVATE);
			
			if(today.equals("Wednesday"))
				// If today is Wednesday itself, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 0, 6);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 6, 5);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 5, 4);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 4, 3);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 3, 2);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 2, 1);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Wednesday
				check_all_slots_for_bm_wed(open_bm_wednesday_file, 1, 0);
		
		
		// All the BM alarms of Wednesday are set.
			
			SharedPreferences open_bm_thursday_file = getApplicationContext().getSharedPreferences("bm_Thursday",Context.MODE_PRIVATE);
			
			if(today.equals("Thursday"))
				// If today is Thursday itself, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 0, 6);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 6, 5);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 5, 4);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 4, 3);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 3, 2);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 2, 1);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Thursday
				check_all_slots_for_bm_thurs(open_bm_thursday_file, 1, 0);

		// All the BM alarms of Thursday are set.
			
			SharedPreferences open_bm_friday_file = getApplicationContext().getSharedPreferences("bm_Friday",Context.MODE_PRIVATE);
			
			if(today.equals("Friday"))
				// If today is Friday itself, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 0, 6);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 6, 5);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 5, 4);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 4, 3);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 3, 2);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 2, 1);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Friday
				check_all_slots_for_bm_fri(open_bm_friday_file, 1, 0);

		// All the BM alarms of Friday are set.
		
	}
	
	private void start_pt_pending_intent(){
		
		SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
		
		int pt_check = pt_tt_sp.getInt("bm_check", 0);
		
		if(pt_check == 1){
			
			if(today.equals("Monday"))
				// If today is Monday itself, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 0, 6);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 6, 5);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 5, 4);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 4, 3);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 3, 2);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 2, 1);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM PT pending intents for Monday
				check_pt_slots_for_monday(pt_tt_sp, 1, 0);
			
			// All the BM alarms of Monday are set.
			
			if(today.equals("Tuesday"))
				// If today is Tuesday itself, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 0, 6);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 6, 5);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 5, 4);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 4, 3);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 3, 2);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 2, 1);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Tuesday
				check_pt_slots_for_tues(pt_tt_sp, 1, 0);

		// All the BM alarms of Tuesday are set.
			
			if(today.equals("Wednesday"))
				// If today is Wednesday itself, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 0, 6);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 6, 5);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 5, 4);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 4, 3);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 3, 2);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 2, 1);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Wednesday
				check_pt_slots_for_wed(pt_tt_sp, 1, 0);
			
		// All the BM alarms of Wednesday are set.
			
			if(today.equals("Thursday"))
				// If today is Thursday itself, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 0, 6);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 6, 5);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 5, 4);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 4, 3);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 3, 2);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 2, 1);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Thursday
				check_pt_slots_for_thurs(pt_tt_sp, 1, 0);

		// All the BM alarms of Thursday are set.
			
			if(today.equals("Friday"))
				// If today is Friday itself, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 0, 6);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 6, 5);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 5, 4);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 4, 3);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 3, 2);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 2, 1);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM pending intents for Friday
				check_pt_slots_for_fri(pt_tt_sp, 1, 0);

		// All the BM alarms of Friday are set.
			
		}else{
			for(int i = 201; i <= 220; i ++)
				cancel_pt_alarm(i);
			// Cancel all alarms associated with PT
		}
	}
	
	private void start_nso_nss_ncc_pending_intent(){
		
		SharedPreferences nso_nss_ncc_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		
		int nso_nss_ncc_check = nso_nss_ncc_tt_sp.getInt("bm_check", 0);
		
		if(nso_nss_ncc_check == 1){
			
			if(today.equals("Monday"))
				// If today is Monday itself, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 0, 6);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 6, 5);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 5, 4);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 4, 3);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 3, 2);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 2, 1);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM NSO/NSS/NCC pending intents for Monday
				check_nso_nss_ncc_slots_for_monday(nso_nss_ncc_tt_sp, 1, 0);
			
			// All the BM alarms of Monday are set.
			
			if(today.equals("Tuesday"))
				// If today is Tuesday itself, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 0, 6);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 6, 5);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 5, 4);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 4, 3);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 3, 2);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 2, 1);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM NSO/NSS/NCC pending intents for Tuesday
				check_nso_nss_ncc_slots_for_tues(nso_nss_ncc_tt_sp, 1, 0);

		// All the BM alarms of Tuesday are set.
			
			if(today.equals("Wednesday"))
				// If today is Wednesday itself, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 0, 6);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 6, 5);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 5, 4);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 4, 3);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 3, 2);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 2, 1);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM NSO/NSS/NCC pending intents for Wednesday
				check_nso_nss_ncc_slots_for_wed(nso_nss_ncc_tt_sp, 1, 0);
			
		// All the BM alarms of Wednesday are set.
			
			if(today.equals("Thursday"))
				// If today is Thursday itself, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 0, 6);
			
			else if(today.equals("Friday"))
				// If today is Friday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 6, 5);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 5, 4);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 4, 3);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 3, 2);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 2, 1);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM NSO/NSS/NCC pending intents for Thursday
				check_nso_nss_ncc_slots_for_thurs(nso_nss_ncc_tt_sp, 1, 0);

		// All the BM alarms of Thursday are set.
			
			if(today.equals("Friday"))
				// If today is Friday itself, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 0, 6);
			
			else if(today.equals("Saturday"))
				// If today is Saturday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 6, 5);
			
			else if(today.equals("Sunday"))
				// If today is Sunday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 5, 4);
			
			else if(today.equals("Monday"))
				// If today is Monday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 4, 3);
			
			else if(today.equals("Tuesday"))
				// If today is Tuesday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 3, 2);
			
			else if(today.equals("Wednesday"))
				// If today is Wednesday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 2, 1);
			
			else if(today.equals("Thursday"))
				// If today is Thursday, while we are setting up the BM NSO/NSS/NCC pending intents for Friday
				check_nso_nss_ncc_slots_for_fri(nso_nss_ncc_tt_sp, 1, 0);

		// All the BM alarms of Friday are set.
			
		}else{
			for(int i = 401; i <= 410; i ++)
				cancel_nso_nss_ncc_alarm(i);
			// Cancel all alarms associated with PT
		}
	}
	
	private void check_pt_slots_for_monday(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Monday_morn5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Monday morning session
			
			if(hours <= 6)
				set_pt_bm(6,bm_pt[0],bm_pt_pending_intent_before[0],mgr_bm_pt_before[0],201,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_1");
			else
				set_pt_bm(6,bm_pt[0],bm_pt_pending_intent_after[0],mgr_bm_pt_after[0],202,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_2");
			
		}
		else{
			cancel_pt_alarm(201);
			cancel_pt_alarm(202);
		}
		
		if(open_bm_day_file.getString("Monday_eve5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Monday evening session
			
			if(hours <= 18)
				set_pt_bm(18,bm_pt[1],bm_pt_pending_intent_before[1],mgr_bm_pt_before[1],203,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_3");
			else
				set_pt_bm(18,bm_pt[1],bm_pt_pending_intent_after[1],mgr_bm_pt_after[1],204,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_4");
			
		}
		else{
			cancel_pt_alarm(203);
			cancel_pt_alarm(204);
		}
		
	}
	
	private void check_pt_slots_for_tues(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Tuesday_morn5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Tuesday morning session
			
			if(hours <= 6)
				set_pt_bm(6,bm_pt[2],bm_pt_pending_intent_before[2],mgr_bm_pt_before[2],205,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_1");
			else
				set_pt_bm(6,bm_pt[2],bm_pt_pending_intent_after[2],mgr_bm_pt_after[2],206,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_2");
			
		}
		else{
			cancel_pt_alarm(205);
			cancel_pt_alarm(206);
		}
		
		if(open_bm_day_file.getString("Tuesday_eve5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Tuesday evening session
			
			if(hours <= 18)
				set_pt_bm(18,bm_pt[3],bm_pt_pending_intent_before[3],mgr_bm_pt_before[3],207,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_3");
			else
				set_pt_bm(18,bm_pt[3],bm_pt_pending_intent_after[3],mgr_bm_pt_after[3],208,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_4");
			
		}
		else{
			cancel_pt_alarm(207);
			cancel_pt_alarm(208);
		}
		
	}
	
	private void check_pt_slots_for_wed(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Wednesday_morn5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Wednesday morning session
			
			if(hours <= 6)
				set_pt_bm(6,bm_pt[4],bm_pt_pending_intent_before[4],mgr_bm_pt_before[4],209,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_1");
			else
				set_pt_bm(6,bm_pt[4],bm_pt_pending_intent_after[4],mgr_bm_pt_after[4],210,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_2");
			
		}
		else{
			cancel_pt_alarm(209);
			cancel_pt_alarm(210);
		}
		
		if(open_bm_day_file.getString("Wednesday_eve5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Wednesday evening session
			
			if(hours <= 18)
				set_pt_bm(18,bm_pt[5],bm_pt_pending_intent_before[5],mgr_bm_pt_before[5],211,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_3");
			else
				set_pt_bm(18,bm_pt[5],bm_pt_pending_intent_after[5],mgr_bm_pt_after[5],212,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_4");
			
		}
		else{
			cancel_pt_alarm(211);
			cancel_pt_alarm(212);
		}
		
	}
	
	private void check_pt_slots_for_thurs(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Thursday_morn5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Thursday morning session
			
			if(hours <= 6)
				set_pt_bm(6,bm_pt[6],bm_pt_pending_intent_before[6],mgr_bm_pt_before[6],213,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_1");
			else
				set_pt_bm(6,bm_pt[6],bm_pt_pending_intent_after[6],mgr_bm_pt_after[6],214,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_2");
			
		}
		else{
			cancel_pt_alarm(213);
			cancel_pt_alarm(214);
		}
		
		if(open_bm_day_file.getString("Thursday_eve5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Thursday evening session
			
			if(hours <= 18)
				set_pt_bm(18,bm_pt[7],bm_pt_pending_intent_before[7],mgr_bm_pt_before[7],215,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_3");
			else
				set_pt_bm(18,bm_pt[7],bm_pt_pending_intent_after[7],mgr_bm_pt_after[7],216,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_4");
			
		}
		else{
			cancel_pt_alarm(215);
			cancel_pt_alarm(216);
		}
		
	}
	
	private void check_pt_slots_for_fri(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Friday_morn5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Friday morning session
			
			if(hours <= 6)
				set_pt_bm(6,bm_pt[8],bm_pt_pending_intent_before[8],mgr_bm_pt_before[8],217,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_1");
			else
				set_pt_bm(6,bm_pt[8],bm_pt_pending_intent_after[8],mgr_bm_pt_after[8],218,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_2");
			
		}
		else{
			cancel_pt_alarm(217);
			cancel_pt_alarm(218);
		}
		
		if(open_bm_day_file.getString("Friday_eve5_45to6_30","No").equals("Yes")){
			// i.e., PT file has Friday evening session
			
			if(hours <= 18)
				set_pt_bm(18,bm_pt[9],bm_pt_pending_intent_before[9],mgr_bm_pt_before[9],219,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_3");
			else
				set_pt_bm(18,bm_pt[9],bm_pt_pending_intent_after[9],mgr_bm_pt_after[9],220,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_4");
			
		}
		else{
			cancel_pt_alarm(219);
			cancel_pt_alarm(220);
		}
		
	}
	
	private void check_nso_nss_ncc_slots_for_monday(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Monday_eve6_30to8","No").equals("Yes")){
			// i.e., NSO/NSS/NCC file has Monday evening session
			
			if(hours <= 19)
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[0],bm_nso_nss_ncc_pending_intent_before[0],mgr_bm_nso_nss_ncc_before[0],401,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_3");
			else
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[0],bm_nso_nss_ncc_pending_intent_after[0],mgr_bm_nso_nss_ncc_after[0],402,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_4");
			
		}
		else{
			cancel_nso_nss_ncc_alarm(401);
			cancel_nso_nss_ncc_alarm(402);
		}
		
	}
	
	private void check_nso_nss_ncc_slots_for_tues(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Tuesday_eve6_30to8","No").equals("Yes")){
			// i.e., NSO/NSS/NCC file has Tuesday evening session
			
			if(hours <= 19)
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[1],bm_nso_nss_ncc_pending_intent_before[1],mgr_bm_nso_nss_ncc_before[1],403,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_3");
			else
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[1],bm_nso_nss_ncc_pending_intent_after[1],mgr_bm_nso_nss_ncc_after[1],404,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_4");
			
		}
		else{
			cancel_nso_nss_ncc_alarm(403);
			cancel_nso_nss_ncc_alarm(404);
		}
		
	}
	
	private void check_nso_nss_ncc_slots_for_wed(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Wednesday_eve6_30to8","No").equals("Yes")){
			// i.e., NSO/NSS/NCC file has Wednesday evening session
			
			if(hours <= 19)
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[2],bm_nso_nss_ncc_pending_intent_before[2],mgr_bm_nso_nss_ncc_before[2],405,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_3");
			else
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[2],bm_nso_nss_ncc_pending_intent_after[2],mgr_bm_nso_nss_ncc_after[2],406,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_4");
			
		}
		else{
			cancel_nso_nss_ncc_alarm(405);
			cancel_nso_nss_ncc_alarm(406);
		}
		
	}
	
	private void check_nso_nss_ncc_slots_for_thurs(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Thursday_eve6_30to8","No").equals("Yes")){
			// i.e., NSO/NSS/NCC file has Thursday evening session
			
			if(hours <= 19)
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[3],bm_nso_nss_ncc_pending_intent_before[3],mgr_bm_nso_nss_ncc_before[3],407,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_3");
			else
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[3],bm_nso_nss_ncc_pending_intent_after[3],mgr_bm_nso_nss_ncc_after[3],408,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_4");
			
		}
		else{
			cancel_nso_nss_ncc_alarm(407);
			cancel_nso_nss_ncc_alarm(408);
		}
		
	}
	
	private void check_nso_nss_ncc_slots_for_fri(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.getString("Friday_eve6_30to8","No").equals("Yes")){
			// i.e., NSO/NSS/NCC file has Friday evening session
			
			if(hours <= 19)
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[4],bm_nso_nss_ncc_pending_intent_before[4],mgr_bm_nso_nss_ncc_before[4],409,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_3");
			else
				set_nso_nss_ncc_bm(19,bm_nso_nss_ncc[4],bm_nso_nss_ncc_pending_intent_after[4],mgr_bm_nso_nss_ncc_after[4],410,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_4");
			
		}
		else{
			cancel_nso_nss_ncc_alarm(409);
			cancel_nso_nss_ncc_alarm(410);
		}
		
	}
	
	private void set_pt_bm(int class_start_time, Intent bm_day_time, PendingIntent bm_day_time_before_after, AlarmManager mgr_bm_day_time_before_after,
			int pending_id, int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours,
			String test) {

			long delay_time = 0;
			
			if(hours <= class_start_time){
				// if today is Monday - i.e., the class will be held today or is ongoing right now.
				// otherwise, calculate time left in class_start_time on that day, and add the required no. of days to it
				
				bm_day_time = new Intent(getBaseContext(), PTBunkMeterCheck.class);
				delay_time = ((class_start_time - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((day_count_for_less_than_class_hours*24)*60*60*1000);
				// delay_time = (Time left in class_start_time on today) + (Time to the class_start_time + 1 hours from 12 am Tuesday) + (6 more days);
				
				// For testing
				bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
				//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
				bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
				mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
				mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
			
				//Toast.makeText(AppHome.this, "Alarm After"+Long.toString(delay_time/60000), Toast.LENGTH_LONG).show();
			}
			else{
			// i.e., the class will now be held in the next week's Monday (irrespective of whether today's Monday or not)
			
				bm_day_time = new Intent(getBaseContext(), PTBunkMeterCheck.class);
				delay_time = ((23 - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((class_start_time + 1)*60*60*1000) + ((day_count_for_more_than_class_hours*24)*60*60*1000);
				// delay_time = (Time left in 12am of next day) + (Time to the class_start_time + 1 hours from 12 am next day) + (day_count_for_more_than_class_hours more days);
				
				// For testing
				bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
				//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
				bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
				mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
				mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
			}
		
		}
	
	private void set_nso_nss_ncc_bm(int class_start_time, Intent bm_day_time, PendingIntent bm_day_time_before_after, AlarmManager mgr_bm_day_time_before_after,
			int pending_id, int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours,
			String test) {

			long delay_time = 0;
			
			if(hours <= class_start_time){
				// if today is Monday - i.e., the class will be held today or is ongoing right now.
				// otherwise, calculate time left in class_start_time on that day, and add the required no. of days to it
				
				bm_day_time = new Intent(getBaseContext(), NSO_NSS_NCC_BunkMeterCheck.class);
				delay_time = ((class_start_time - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((day_count_for_less_than_class_hours*24)*60*60*1000);
				// delay_time = (Time left in class_start_time on today) + (Time to the class_start_time + 1 hours from 12 am Tuesday) + (6 more days);
				
				// For testing
				bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
				//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
				bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
				mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
				mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
			
				//Toast.makeText(AppHome.this, "Alarm After"+Long.toString(delay_time/60000), Toast.LENGTH_LONG).show();
			}
			else{
			// i.e., the class will now be held in the next week's Monday (irrespective of whether today's Monday or not)
			
				bm_day_time = new Intent(getBaseContext(), NSO_NSS_NCC_BunkMeterCheck.class);
				delay_time = ((23 - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((class_start_time + 1)*60*60*1000) + ((day_count_for_more_than_class_hours*24)*60*60*1000);
				// delay_time = (Time left in 12am of next day) + (Time to the class_start_time + 1 hours from 12 am next day) + (day_count_for_more_than_class_hours more days);
				
				// For testing
				bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
				//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
				bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
				mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
				mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
			}
			//Toast.makeText(AppHome.this, "Alarm After"+Long.toString(delay_time/60000), Toast.LENGTH_LONG).show();
		}

	private void check_all_slots_for_bm_mon(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.contains("name_8to9")){
			// i.e., Monday's 8am to 9am has a BM counter on it
			
			if(hours <= 8)
				set_bm(8,bm_mon[0],bm_mon_pending_intent_before[0],mgr_bm_mon_before[0],1,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_1");
			else
				set_bm(8,bm_mon[0],bm_mon_pending_intent_after[0],mgr_bm_mon_after[0],2,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_2");
			
		} // end of if(open_bm_day_file.contains("name_8to9"))
		else{
			cancel_alarm(1);
			cancel_alarm(2);
		}
		
		if(open_bm_day_file.contains("name_9to10")){
			// i.e., Monday's 9am to 10am has a BM counter on it
			
			if(hours <= 9)
				set_bm(9,bm_mon[1],bm_mon_pending_intent_before[1],mgr_bm_mon_before[1],3,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_3");
			else
				set_bm(9,bm_mon[1],bm_mon_pending_intent_after[1],mgr_bm_mon_after[1],4,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_4");
			
		} // end of if(open_bm_day_file.contains("name_9to10"))
		else{
			cancel_alarm(3);
			cancel_alarm(4);
		}
		
		if(open_bm_day_file.contains("name_10to11")){
			// i.e., Monday's 10am to 11am has a BM counter on it
			
			if(hours <= 10)
				set_bm(10,bm_mon[2],bm_mon_pending_intent_before[2],mgr_bm_mon_before[2],5,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_5");
			else
				set_bm(10,bm_mon[2],bm_mon_pending_intent_after[2],mgr_bm_mon_after[2],6,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_6");
			
		} // end of if(open_bm_day_file.contains("name_10to11"))
		else{
			cancel_alarm(5);
			cancel_alarm(6);
		}
		
		if(open_bm_day_file.contains("name_11to12")){
			// i.e., Monday's 11am to 12pm has a BM counter on it
			
			if(hours <= 11)
				set_bm(11,bm_mon[3],bm_mon_pending_intent_before[3],mgr_bm_mon_before[3],7,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_7");
			else
				set_bm(11,bm_mon[3],bm_mon_pending_intent_after[3],mgr_bm_mon_after[3],8,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_8");
			
		} // end of if(open_bm_day_file.contains("name_11to12"))
		else{
			cancel_alarm(7);
			cancel_alarm(8);
		}
		
		if(open_bm_day_file.contains("name_12to1")){
			// i.e., Monday's 12pm to 1pm has a BM counter on it
			
			if(hours <= 12)
				set_bm(12,bm_mon[4],bm_mon_pending_intent_before[4],mgr_bm_mon_before[4],9,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_9");
			else
				set_bm(12,bm_mon[4],bm_mon_pending_intent_after[4],mgr_bm_mon_after[4],10,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_10");
			
		} // end of if(open_bm_day_file.contains("name_12to1"))
		else{
			cancel_alarm(9);
			cancel_alarm(10);
		}
		
		if(open_bm_day_file.contains("name_1to2")){
			// i.e., Monday's 1pm to 2pm has a BM counter on it
			
			if(hours <= 13)
				set_bm(13,bm_mon[5],bm_mon_pending_intent_before[5],mgr_bm_mon_before[5],11,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_11");
			else
				set_bm(13,bm_mon[5],bm_mon_pending_intent_after[5],mgr_bm_mon_after[5],12,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_12");
			
		} // end of if(open_bm_day_file.contains("name_1to2"))
		else{
			cancel_alarm(11);
			cancel_alarm(12);
		}
		
		if(open_bm_day_file.contains("name_2to3")){
			// i.e., Monday's 2pm to 3pm has a BM counter on it
			
			if(hours <= 14)
				set_bm(14,bm_mon[6],bm_mon_pending_intent_before[6],mgr_bm_mon_before[6],13,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_13");
			else
				set_bm(14,bm_mon[6],bm_mon_pending_intent_after[6],mgr_bm_mon_after[6],14,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_14");
			
		} // end of if(open_bm_day_file.contains("name_2to3"))
		else{
			cancel_alarm(13);
			cancel_alarm(14);
		}
		
		if(open_bm_day_file.contains("name_3to4")){
			// i.e., Monday's 3pm to 4pm has a BM counter on it
			
			if(hours <= 15)
				set_bm(15,bm_mon[7],bm_mon_pending_intent_before[7],mgr_bm_mon_before[7],15,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_15");
			else
				set_bm(15,bm_mon[7],bm_mon_pending_intent_after[7],mgr_bm_mon_after[7],16,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_16");
			
		} // end of if(open_bm_day_file.contains("name_3to4"))
		else{
			cancel_alarm(15);
			cancel_alarm(16);
		}
		
		if(open_bm_day_file.contains("name_4to5")){
			// i.e., Monday's 4pm to 5pm has a BM counter on it
			
			if(hours <= 16)
				set_bm(16,bm_mon[8],bm_mon_pending_intent_before[8],mgr_bm_mon_before[8],17,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_17");
			else
				set_bm(16,bm_mon[8],bm_mon_pending_intent_after[8],mgr_bm_mon_after[8],18,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_18");
			
		} // end of if(open_bm_day_file.contains("name_4to5"))
		else{
			cancel_alarm(17);
			cancel_alarm(18);
		}
		
		if(open_bm_day_file.contains("name_5to6")){
			// i.e., Monday's 5pm to 6pm has a BM counter on it
			
			if(hours <= 17)
				set_bm(17,bm_mon[9],bm_mon_pending_intent_before[9],mgr_bm_mon_before[9],19,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_19");
			else
				set_bm(17,bm_mon[9],bm_mon_pending_intent_after[9],mgr_bm_mon_after[9],20,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "mon_20");
			
		} // end of if(open_bm_day_file.contains("name_5to6"))
		else{
			cancel_alarm(19);
			cancel_alarm(20);
		}
	}
	
	private void check_all_slots_for_bm_tues(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.contains("name_8to9")){
			// i.e., Tuesday's 8am to 9am has a BM counter on it
			
			if(hours <= 8)
				set_bm(8,bm_tues[0],bm_tues_pending_intent_before[0],mgr_bm_tues_before[0],21,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_1");
			else
				set_bm(8,bm_tues[0],bm_tues_pending_intent_after[0],mgr_bm_tues_after[0],22,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_2");
			
		} // end of if(open_bm_day_file.contains("name_8to9"))
		else{
			cancel_alarm(21);
			cancel_alarm(22);
		}
		
		if(open_bm_day_file.contains("name_9to10")){
			// i.e., Tuesday's 9am to 10am has a BM counter on it
			
			if(hours <= 9)
				set_bm(9,bm_tues[1],bm_tues_pending_intent_before[1],mgr_bm_tues_before[1],23,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_3");
			else
				set_bm(9,bm_tues[1],bm_tues_pending_intent_after[1],mgr_bm_tues_after[1],24,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_4");
			
		} // end of if(open_bm_day_file.contains("name_9to10"))
		else{
			cancel_alarm(23);
			cancel_alarm(24);
		}
		
		if(open_bm_day_file.contains("name_10to11")){
			// i.e., Tuesday's 10am to 11am has a BM counter on it
			
			if(hours <= 10)
				set_bm(10,bm_tues[2],bm_tues_pending_intent_before[2],mgr_bm_tues_before[2],25,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_5");
			else
				set_bm(10,bm_tues[2],bm_tues_pending_intent_after[2],mgr_bm_tues_after[2],26,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_6");
			
		} // end of if(open_bm_day_file.contains("name_10to11"))
		else{
			cancel_alarm(25);
			cancel_alarm(26);
		}
		
		if(open_bm_day_file.contains("name_11to12")){
			// i.e., Tuesday's 11am to 12pm has a BM counter on it
			
			if(hours <= 11)
				set_bm(11,bm_tues[3],bm_tues_pending_intent_before[3],mgr_bm_tues_before[3],27,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_7");
			else
				set_bm(11,bm_tues[3],bm_tues_pending_intent_after[3],mgr_bm_tues_after[3],28,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_8");
			
		} // end of if(open_bm_day_file.contains("name_11to12"))
		else{
			cancel_alarm(27);
			cancel_alarm(28);
		}
		
		if(open_bm_day_file.contains("name_12to1")){
			// i.e., Tuesday's 12pm to 1pm has a BM counter on it
			
			if(hours <= 12)
				set_bm(12,bm_tues[4],bm_tues_pending_intent_before[4],mgr_bm_tues_before[4],29,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_9");
			else
				set_bm(12,bm_tues[4],bm_tues_pending_intent_after[4],mgr_bm_tues_after[4],30,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_10");
			
		} // end of if(open_bm_day_file.contains("name_12to1"))
		else{
			cancel_alarm(29);
			cancel_alarm(30);
		}
		
		if(open_bm_day_file.contains("name_1to2")){
			// i.e., Tuesday's 1pm to 2pm has a BM counter on it
			
			if(hours <= 13)
				set_bm(13,bm_tues[5],bm_tues_pending_intent_before[5],mgr_bm_tues_before[5],31,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_11");
			else
				set_bm(13,bm_tues[5],bm_tues_pending_intent_after[5],mgr_bm_tues_after[5],32,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_12");
			
		} // end of if(open_bm_day_file.contains("name_1to2"))
		else{
			cancel_alarm(31);
			cancel_alarm(32);
		}
		
		if(open_bm_day_file.contains("name_2to3")){
			// i.e., Tuesday's 2pm to 3pm has a BM counter on it
		
			if(hours <= 14)
				set_bm(14,bm_tues[6],bm_tues_pending_intent_before[6],mgr_bm_tues_before[6],33,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_13");
			else
				set_bm(14,bm_tues[6],bm_tues_pending_intent_after[6],mgr_bm_tues_after[6],34,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_14");
			
		} // end of if(open_bm_day_file.contains("name_2to3"))
		else{
			cancel_alarm(33);
			cancel_alarm(34);
		}
		
		if(open_bm_day_file.contains("name_3to4")){
			// i.e., Tuesday's 3pm to 4pm has a BM counter on it
			
			if(hours <= 15)
				set_bm(15,bm_tues[7],bm_tues_pending_intent_before[7],mgr_bm_tues_before[7],35,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_15");
			else
				set_bm(15,bm_tues[7],bm_tues_pending_intent_after[7],mgr_bm_tues_after[7],36,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_16");
			
		} // end of if(open_bm_day_file.contains("name_3to4"))
		else{
			cancel_alarm(35);
			cancel_alarm(36);
		}
		
		if(open_bm_day_file.contains("name_4to5")){
			// i.e., Tuesday's 4pm to 5pm has a BM counter on it
			
			if(hours <= 16)
				set_bm(16,bm_tues[8],bm_tues_pending_intent_before[8],mgr_bm_tues_before[8],37,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_17");
			else
				set_bm(16,bm_tues[8],bm_tues_pending_intent_after[8],mgr_bm_tues_after[8],38,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_18");
			
		} // end of if(open_bm_day_file.contains("name_4to5"))
		else{
			cancel_alarm(37);
			cancel_alarm(38);
		}
		
		if(open_bm_day_file.contains("name_5to6")){
			// i.e., Tuesday's 5pm to 6pm has a BM counter on it
			
			if(hours <= 17)
				set_bm(17,bm_tues[9],bm_tues_pending_intent_before[9],mgr_bm_tues_before[9],39,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_19");
			else
				set_bm(17,bm_tues[9],bm_tues_pending_intent_after[9],mgr_bm_tues_after[9],40,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "tues_20");
			
		} // end of if(open_bm_day_file.contains("name_5to6"))
		else{
			cancel_alarm(39);
			cancel_alarm(40);
		}
	}
	
	private void check_all_slots_for_bm_wed(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.contains("name_8to9")){
			// i.e., Wednesday's 8am to 9am has a BM counter on it
			
			if(hours <= 8)
				set_bm(8,bm_wed[0],bm_wed_pending_intent_before[0],mgr_bm_wed_before[0],41,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_1");
			else
				set_bm(8,bm_wed[0],bm_wed_pending_intent_after[0],mgr_bm_wed_after[0],42,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_2");
			
		} // end of if(open_bm_day_file.contains("name_8to9"))
		else{
			cancel_alarm(41);
			cancel_alarm(42);
		}
		
		if(open_bm_day_file.contains("name_9to10")){
			// i.e., Wednesday's 9am to 10am has a BM counter on it
			
			if(hours <= 9)
				set_bm(9,bm_wed[1],bm_wed_pending_intent_before[1],mgr_bm_wed_before[1],43,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_3");
			else
				set_bm(9,bm_wed[1],bm_wed_pending_intent_after[1],mgr_bm_wed_after[1],44,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_4");
			
		} // end of if(open_bm_day_file.contains("name_9to10"))
		else{
			cancel_alarm(43);
			cancel_alarm(44);
		}
		
		if(open_bm_day_file.contains("name_10to11")){
			// i.e., Wednesday's 10am to 11am has a BM counter on it

			if(hours <= 10)
				set_bm(10,bm_wed[2],bm_wed_pending_intent_before[2],mgr_bm_wed_before[2],45,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_5");
			else
				set_bm(10,bm_wed[2],bm_wed_pending_intent_after[2],mgr_bm_wed_after[2],46,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_6");
			
		} // end of if(open_bm_day_file.contains("name_10to11"))
		else{
			cancel_alarm(45);
			cancel_alarm(46);
		}
		
		if(open_bm_day_file.contains("name_11to12")){
			// i.e., Wednesday's 11am to 12pm has a BM counter on it
			
			if(hours <= 11)
				set_bm(11,bm_wed[3],bm_wed_pending_intent_before[3],mgr_bm_wed_before[3],47,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_7");
			else
				set_bm(11,bm_wed[3],bm_wed_pending_intent_after[3],mgr_bm_wed_after[3],48,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_8");
			
		} // end of if(open_bm_day_file.contains("name_11to12"))
		else{
			cancel_alarm(47);
			cancel_alarm(48);
		}
		
		if(open_bm_day_file.contains("name_12to1")){
			// i.e., Wednesday's 12pm to 1pm has a BM counter on it
			
			if(hours <= 12)
				set_bm(12,bm_wed[4],bm_wed_pending_intent_before[4],mgr_bm_wed_before[4],49,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_9");
			else
				set_bm(12,bm_wed[4],bm_wed_pending_intent_after[4],mgr_bm_wed_after[4],50,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_10");
			
		} // end of if(open_bm_day_file.contains("name_12to1"))
		else{
			cancel_alarm(49);
			cancel_alarm(50);
		}
		
		if(open_bm_day_file.contains("name_1to2")){
			// i.e., Wednesday's 1pm to 2pm has a BM counter on it
			
			if(hours <= 13)
				set_bm(13,bm_wed[5],bm_wed_pending_intent_before[5],mgr_bm_wed_before[5],51,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_11");
			else
				set_bm(13,bm_wed[5],bm_wed_pending_intent_after[5],mgr_bm_wed_after[5],52,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_12");
			
		} // end of if(open_bm_day_file.contains("name_1to2"))
		else{
			cancel_alarm(51);
			cancel_alarm(52);
		}
		
		if(open_bm_day_file.contains("name_2to3")){
			// i.e., Wednesday's 2pm to 3pm has a BM counter on it
			
			if(hours <= 14)
				set_bm(14,bm_wed[6],bm_wed_pending_intent_before[6],mgr_bm_wed_before[6],53,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_13");
			else
				set_bm(14,bm_wed[6],bm_wed_pending_intent_after[6],mgr_bm_wed_after[6],54,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_14");
			
		} // end of if(open_bm_day_file.contains("name_2to3"))
		else{
			cancel_alarm(53);
			cancel_alarm(54);
		}
		
		if(open_bm_day_file.contains("name_3to4")){
			// i.e., Wednesday's 3pm to 4pm has a BM counter on it
			
			if(hours <= 15)
				set_bm(15,bm_wed[7],bm_wed_pending_intent_before[7],mgr_bm_wed_before[7],55,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_15");
			else
				set_bm(15,bm_wed[7],bm_wed_pending_intent_after[7],mgr_bm_wed_after[7],56,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_16");
			
		} // end of if(open_bm_day_file.contains("name_3to4"))
		else{
			cancel_alarm(55);
			cancel_alarm(56);
		}
		
		if(open_bm_day_file.contains("name_4to5")){
			// i.e., Wednesday's 4pm to 5pm has a BM counter on it
			
			if(hours <= 16)
				set_bm(16,bm_wed[8],bm_wed_pending_intent_before[8],mgr_bm_wed_before[8],57,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_17");
			else
				set_bm(16,bm_wed[8],bm_wed_pending_intent_after[8],mgr_bm_wed_after[8],58,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_18");
			
		} // end of if(open_bm_day_file.contains("name_4to5"))
		else{
			cancel_alarm(57);
			cancel_alarm(58);
		}
		
		if(open_bm_day_file.contains("name_5to6")){
			// i.e., Wednesday's 5pm to 6pm has a BM counter on it
			
			if(hours <= 17)
				set_bm(17,bm_wed[9],bm_wed_pending_intent_before[9],mgr_bm_wed_before[9],59,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_19");
			else
				set_bm(17,bm_wed[9],bm_wed_pending_intent_after[9],mgr_bm_wed_after[9],60,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "wed_20");
			
		} // end of if(open_bm_day_file.contains("name_5to6"))
		else{
			cancel_alarm(59);
			cancel_alarm(60);
		}
		
	}
	
	private void check_all_slots_for_bm_thurs(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.contains("name_8to9")){
			// i.e., Thursday's 8am to 9am has a BM counter on it
			
			if(hours <= 8)
				set_bm(8,bm_thurs[0],bm_thurs_pending_intent_before[0],mgr_bm_thurs_before[0],61,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_1");
			else
				set_bm(8,bm_thurs[0],bm_thurs_pending_intent_after[0],mgr_bm_thurs_after[0],62,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_2");
			
		} // end of if(open_bm_day_file.contains("name_8to9"))
		else{
			cancel_alarm(61);
			cancel_alarm(62);
		}
		
		if(open_bm_day_file.contains("name_9to10")){
			// i.e., Thursday's 9am to 10am has a BM counter on it
			
			if(hours <= 9)
				set_bm(9,bm_thurs[1],bm_thurs_pending_intent_before[1],mgr_bm_thurs_before[1],63,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_3");
			else
				set_bm(9,bm_thurs[1],bm_thurs_pending_intent_after[1],mgr_bm_thurs_after[1],64,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_4");
			
		} // end of if(open_bm_day_file.contains("name_9to10"))
		else{
			cancel_alarm(63);
			cancel_alarm(64);
		}
		
		if(open_bm_day_file.contains("name_10to11")){
			// i.e., Thursday's 10am to 11am has a BM counter on it
			
			if(hours <= 10)
				set_bm(10,bm_thurs[2],bm_thurs_pending_intent_before[2],mgr_bm_thurs_before[2],65,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_5");
			else
				set_bm(10,bm_thurs[2],bm_thurs_pending_intent_after[2],mgr_bm_thurs_after[2],66,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_6");
			
		} // end of if(open_bm_day_file.contains("name_10to11"))
		else{
			cancel_alarm(65);
			cancel_alarm(66);
		}
		
		if(open_bm_day_file.contains("name_11to12")){
			// i.e., Thursday's 11am to 12pm has a BM counter on it
			
			if(hours <= 11)
				set_bm(11,bm_thurs[3],bm_thurs_pending_intent_before[3],mgr_bm_thurs_before[3],67,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_7");
			else
				set_bm(11,bm_thurs[3],bm_thurs_pending_intent_after[3],mgr_bm_thurs_after[3],68,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_8");
			
		} // end of if(open_bm_day_file.contains("name_11to12"))
		else{
			cancel_alarm(67);
			cancel_alarm(68);
		}
		
		if(open_bm_day_file.contains("name_12to1")){
			// i.e., Thursday's 12pm to 1pm has a BM counter on it
			
			if(hours <= 12)
				set_bm(12,bm_thurs[4],bm_thurs_pending_intent_before[4],mgr_bm_thurs_before[4],69,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_9");
			else
				set_bm(12,bm_thurs[4],bm_thurs_pending_intent_after[4],mgr_bm_thurs_after[4],70,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_10");
			
		} // end of if(open_bm_day_file.contains("name_12to1"))
		else{
			cancel_alarm(69);
			cancel_alarm(70);
		}
		
		if(open_bm_day_file.contains("name_1to2")){
			// i.e., Thursday's 1pm to 2pm has a BM counter on it
			
			if(hours <= 13)
				set_bm(13,bm_thurs[5],bm_thurs_pending_intent_before[5],mgr_bm_thurs_before[5],71,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_11");
			else
				set_bm(13,bm_thurs[5],bm_thurs_pending_intent_after[5],mgr_bm_thurs_after[5],72,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_12");
			
		} // end of if(open_bm_day_file.contains("name_1to2"))
		else{
			cancel_alarm(71);
			cancel_alarm(72);
		}
		
		if(open_bm_day_file.contains("name_2to3")){
			// i.e., Thursday's 2pm to 3pm has a BM counter on it
			
			if(hours <= 14)
				set_bm(14,bm_thurs[6],bm_thurs_pending_intent_before[6],mgr_bm_thurs_before[6],73,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_13");
			else
				set_bm(14,bm_thurs[6],bm_thurs_pending_intent_after[6],mgr_bm_thurs_after[6],74,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_14");
			
		} // end of if(open_bm_day_file.contains("name_2to3"))
		else{
			cancel_alarm(73);
			cancel_alarm(74);
		}
		
		if(open_bm_day_file.contains("name_3to4")){
			// i.e., Thursday's 3pm to 4pm has a BM counter on it
			
			if(hours <= 15)
				set_bm(15,bm_thurs[7],bm_thurs_pending_intent_before[7],mgr_bm_thurs_before[7],75,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_15");
			else
				set_bm(15,bm_thurs[7],bm_thurs_pending_intent_after[7],mgr_bm_thurs_after[7],76,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_16");
			
		} // end of if(open_bm_day_file.contains("name_3to4"))
		else{
			cancel_alarm(75);
			cancel_alarm(76);
		}
		
		if(open_bm_day_file.contains("name_4to5")){
			// i.e., Thursday's 4pm to 5pm has a BM counter on it
			
			if(hours <= 16)
				set_bm(16,bm_thurs[8],bm_thurs_pending_intent_before[8],mgr_bm_thurs_before[8],77,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_17");
			else
				set_bm(16,bm_thurs[8],bm_thurs_pending_intent_after[8],mgr_bm_thurs_after[8],78,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_18");
			
		} // end of if(open_bm_day_file.contains("name_4to5"))
		else{
			cancel_alarm(77);
			cancel_alarm(78);
		}
		
		if(open_bm_day_file.contains("name_5to6")){
			// i.e., Thursday's 5pm to 6pm has a BM counter on it
			
			if(hours <= 17)
				set_bm(17,bm_thurs[9],bm_thurs_pending_intent_before[9],mgr_bm_thurs_before[9],79,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_19");
			else
				set_bm(17,bm_thurs[9],bm_thurs_pending_intent_after[9],mgr_bm_thurs_after[9],80,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "thurs_20");
			
		} // end of if(open_bm_day_file.contains("name_5to6"))
		else{
			cancel_alarm(79);
			cancel_alarm(80);
		}
		
	}
	
	private void check_all_slots_for_bm_fri(SharedPreferences open_bm_day_file,
			int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours){
		
		if(open_bm_day_file.contains("name_8to9")){
			// i.e., Friday's 8am to 9am has a BM counter on it
			
			if(hours <= 8)
				set_bm(8,bm_fri[0],bm_fri_pending_intent_before[0],mgr_bm_fri_before[0],81,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_1");
			else
				set_bm(8,bm_fri[0],bm_fri_pending_intent_after[0],mgr_bm_fri_after[0],82,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_2");
			
		} // end of if(open_bm_day_file.contains("name_8to9"))
		else{
			cancel_alarm(81);
			cancel_alarm(82);
		}
		
		if(open_bm_day_file.contains("name_9to10")){
			// i.e., Friday's 9am to 10am has a BM counter on it
			
			if(hours <= 9)
				set_bm(9,bm_fri[1],bm_fri_pending_intent_before[1],mgr_bm_fri_before[1],83,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_3");
			else
				set_bm(9,bm_fri[1],bm_fri_pending_intent_after[1],mgr_bm_fri_after[1],84,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_4");
			
		} // end of if(open_bm_day_file.contains("name_9to10"))
		else{
			cancel_alarm(83);
			cancel_alarm(84);
		}
		
		if(open_bm_day_file.contains("name_10to11")){
			// i.e., Friday's 10am to 11am has a BM counter on it
			
			if(hours <= 10)
				set_bm(10,bm_fri[2],bm_fri_pending_intent_before[2],mgr_bm_fri_before[2],85,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_5");
			else
				set_bm(10,bm_fri[2],bm_fri_pending_intent_after[2],mgr_bm_fri_after[2],86,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_6");
			
		} // end of if(open_bm_day_file.contains("name_10to11"))
		else{
			cancel_alarm(85);
			cancel_alarm(86);
		}
		
		if(open_bm_day_file.contains("name_11to12")){
			// i.e., Friday's 11am to 12pm has a BM counter on it
			
			if(hours <= 11)
				set_bm(11,bm_fri[3],bm_fri_pending_intent_before[3],mgr_bm_fri_before[3],87,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_7");
			else
				set_bm(11,bm_fri[3],bm_fri_pending_intent_after[3],mgr_bm_fri_after[3],88,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_8");
			
		} // end of if(open_bm_day_file.contains("name_11to12"))
		else{
			cancel_alarm(87);
			cancel_alarm(88);
		}
		
		if(open_bm_day_file.contains("name_12to1")){
			// i.e., Friday's 12pm to 1pm has a BM counter on it
			
			if(hours <= 12)
				set_bm(12,bm_fri[4],bm_fri_pending_intent_before[4],mgr_bm_fri_before[4],89,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_9");
			else
				set_bm(12,bm_fri[4],bm_fri_pending_intent_after[4],mgr_bm_fri_after[4],90,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_10");
			
		} // end of if(open_bm_day_file.contains("name_12to1"))
		else{
			cancel_alarm(89);
			cancel_alarm(90);
		}
		
		if(open_bm_day_file.contains("name_1to2")){
			// i.e., Friday's 1pm to 2pm has a BM counter on it
			
			if(hours <= 13)
				set_bm(13,bm_fri[5],bm_fri_pending_intent_before[5],mgr_bm_fri_before[5],91,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_11");
			else
				set_bm(13,bm_fri[5],bm_fri_pending_intent_after[5],mgr_bm_fri_after[5],92,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_12");
			
		} // end of if(open_bm_day_file.contains("name_1to2"))
		else{
			cancel_alarm(91);
			cancel_alarm(92);
		}
		
		if(open_bm_day_file.contains("name_2to3")){
			// i.e., Friday's 2pm to 3pm has a BM counter on it
			
			if(hours <= 14)
				set_bm(14,bm_fri[6],bm_fri_pending_intent_before[6],mgr_bm_fri_before[6],93,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_13");
			else
				set_bm(14,bm_fri[6],bm_fri_pending_intent_after[6],mgr_bm_fri_after[6],94,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_14");
			
		} // end of if(open_bm_day_file.contains("name_2to3"))
		else{
			cancel_alarm(93);
			cancel_alarm(94);
		}
		
		if(open_bm_day_file.contains("name_3to4")){
			// i.e., Friday's 3pm to 4pm has a BM counter on it
			
			if(hours <= 15)
				set_bm(15,bm_fri[7],bm_fri_pending_intent_before[7],mgr_bm_fri_before[7],95,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_15");
			else
				set_bm(15,bm_fri[7],bm_fri_pending_intent_after[7],mgr_bm_fri_after[7],96,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_16");
			
		} // end of if(open_bm_day_file.contains("name_3to4"))
		else{
			cancel_alarm(95);
			cancel_alarm(96);
		}
		
		if(open_bm_day_file.contains("name_4to5")){
			// i.e., Friday's 4pm to 5pm has a BM counter on it
			
			if(hours <= 16)
				set_bm(16,bm_fri[8],bm_fri_pending_intent_before[8],mgr_bm_fri_before[8],97,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_17");
			else
				set_bm(16,bm_fri[8],bm_fri_pending_intent_after[8],mgr_bm_fri_after[8],98,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_18");
			
		} // end of if(open_bm_day_file.contains("name_4to5"))
		else{
			cancel_alarm(97);
			cancel_alarm(98);
		}
		
		if(open_bm_day_file.contains("name_5to6")){
			// i.e., Friday's 5pm to 6pm has a BM counter on it
			
			if(hours <= 17)
				set_bm(17,bm_fri[9],bm_fri_pending_intent_before[9],mgr_bm_fri_before[9],99,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_19");
			else
				set_bm(17,bm_fri[9],bm_fri_pending_intent_after[9],mgr_bm_fri_after[9],100,day_count_for_less_than_class_hours, day_count_for_more_than_class_hours, "fri_20");
			
		} // end of if(open_bm_day_file.contains("name_5to6"))
		else{
			cancel_alarm(99);
			cancel_alarm(100);
		}
		
	}

	private void cancel_alarm(int id) {
		
		try {
			Intent intent = new Intent(getBaseContext(), BunkMeterCheck.class);
			PendingIntent displayIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alm = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
			
			if(displayIntent != null) {
				
				alm.cancel(displayIntent);
				displayIntent.cancel();
			}
	    } catch (Exception e) {
	    	// Do nothing
	    }
	}
	
	private void cancel_pt_alarm(int id) {
		
		try {
			Intent intent = new Intent(getBaseContext(), NSO_NSS_NCC_BunkMeterCheck.class);
			PendingIntent displayIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alm = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
			
			if(displayIntent != null) {
				
				alm.cancel(displayIntent);
				displayIntent.cancel();
			}
	    } catch (Exception e) {
	    	// Do nothing
	    }
	}
	
	private void cancel_nso_nss_ncc_alarm(int id) {
		
		try {
			Intent intent = new Intent(getBaseContext(), NSO_NSS_NCC_BunkMeterCheck.class);
			PendingIntent displayIntent = PendingIntent.getActivity(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alm = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
			
			if(displayIntent != null) {
				
				alm.cancel(displayIntent);
				displayIntent.cancel();
			}
	    } catch (Exception e) {
	    	// Do nothing
	    }
		
		//Toast.makeText(AppHome.this, "Cancel"+Integer.toString(id), Toast.LENGTH_SHORT).show();
	}

	private void set_bm(int class_start_time, Intent bm_day_time, PendingIntent bm_day_time_before_after, AlarmManager mgr_bm_day_time_before_after,
								int pending_id, int day_count_for_less_than_class_hours, int day_count_for_more_than_class_hours,
								String test) {
		
		long delay_time = 0;

		if(hours <= class_start_time){
			// if today is Monday - i.e., the class will be held today or is ongoing right now.
			// otherwise, calculate time left in class_start_time on that day, and add the required no. of days to it
			
			bm_day_time = new Intent(getBaseContext(), BunkMeterCheck.class);
			delay_time = ((class_start_time - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((day_count_for_less_than_class_hours*24)*60*60*1000);
			// delay_time = (Time left in class_start_time on today) + (Time to the class_start_time + 1 hours from 12 am Tuesday) + (6 more days);
			
			// For testing
			bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
			//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
			bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
			mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
			mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
			
		}
		else{
			// i.e., the class will now be held in the next week's Monday (irrespective of whether today's Monday or not)
			
			bm_day_time = new Intent(getBaseContext(), BunkMeterCheck.class);
			delay_time = ((23 - hours)*60*60*1000) + ((61 - minutes)*60*1000) + ((class_start_time + 1)*60*60*1000) + ((day_count_for_more_than_class_hours*24)*60*60*1000);
			// delay_time = (Time left in 12am of next day) + (Time to the class_start_time + 1 hours from 12 am next day) + (day_count_for_more_than_class_hours more days);
			
			// For testing
			bm_day_time.putExtra("test",test+" "+Long.toString(delay_time/60000));
			//bm_day_time_before_after = PendingIntent.getActivity(getApplicationContext(), pending_id, bm_day_time, 0);
			bm_day_time_before_after = PendingIntent.getActivity(getBaseContext(), pending_id, bm_day_time, PendingIntent.FLAG_UPDATE_CURRENT);
			mgr_bm_day_time_before_after = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
			mgr_bm_day_time_before_after.set(AlarmManager.RTC, System.currentTimeMillis() + delay_time, bm_day_time_before_after);
		}
		
	}

	@Override
    public void onBackPressed() {
		
		new AlertDialog.Builder(this).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	finish();
                    System.exit(0);
                }
            }).setNegativeButton("No", null).show();
    }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_app_home,
					container, false);
			return rootView;
		}
	}

}
