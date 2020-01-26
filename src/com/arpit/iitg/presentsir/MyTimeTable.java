package com.arpit.iitg.presentsir;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyTimeTable extends Activity {
	
	TextView week, course, pt, nso;
	TextView my_tt;
	TextView add, add_msg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_time_table_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    week = (TextView) findViewById(R.id.show_week_tv_mttp);
	    course = (TextView) findViewById(R.id.show_course_tv_mttp);
	    pt = (TextView) findViewById(R.id.show_pt_tv_mttp);
	    nso = (TextView) findViewById(R.id.show_nso_nss_ncc_tv_mttp);
	    
	    my_tt = (TextView) findViewById(R.id.my_tt_tv_mttp);
	    
	    add = (TextView) findViewById(R.id.add_new_course_mttp);
	    add_msg = (TextView) findViewById(R.id.add_new_course_msg_mttp);
	    
	    week.setTypeface(comic_font);
	    course.setTypeface(comic_font);
	    pt.setTypeface(comic_font);
	    nso.setTypeface(comic_font);
	    my_tt.setTypeface(comic_font);
	    add.setTypeface(comic_font);
	    add_msg.setTypeface(comic_font);
	    
	    my_tt.setPaintFlags(my_tt.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    
	    week.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_any_course_exists(1);
			}
		});
	    
	    course.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_any_course_exists(2);
			}
		});
	    
	    pt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_pt_nso_exists(1);
			}
		});
	    
	    nso.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				check_if_pt_nso_exists(2);
			}
		});
	    
	    add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent select_to_add_screen = new Intent(MyTimeTable.this, SelectCoursePtNsoToAdd.class);
				startActivity(select_to_add_screen);
			}
		});
	    
	}
	
	protected void check_if_pt_nso_exists(int i) {

		if(i == 1){
			SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
			
			if(pt_tt_sp.getString("num", "N/A").equals("N/A")){
				// i.e,. the PT course has not been updated yet
				BuildAlertNoPTNSOToShow(1);
			}else{
				Intent show_pt_tt_screen = new Intent(MyTimeTable.this, ViewPTNSOTT.class);
				show_pt_tt_screen.putExtra("PT_or_NSO", 1);
				startActivity(show_pt_tt_screen);
			}
		}
		
		if(i == 2){
			SharedPreferences nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
			
			if(nso_nss_nss_tt_sp.getInt("course_value", 0) == 0){
				// i.e,. the NSO, NSS, NCC course has not been updated yet
				BuildAlertNoPTNSOToShow(2);
			}else{
				Intent show_pt_tt_screen = new Intent(MyTimeTable.this, ViewPTNSOTT.class);
				show_pt_tt_screen.putExtra("PT_or_NSO", 2);
				startActivity(show_pt_tt_screen);
			}
		}
		
		
	}

	private void BuildAlertNoPTNSOToShow(final int i) {
		
		String alert_msg = null;
		
		if(i == 1)
			alert_msg = getResources().getString(R.string.no_pt_to_show_msg);
		else if(i == 2)
			alert_msg = getResources().getString(R.string.no_nso_nss_nss_to_show_msg);

		new AlertDialog.Builder(this).setTitle("Empty Course List")
        .setMessage(alert_msg)
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	
        	if(i == 1){
        		Intent add_pt_screen = new Intent(MyTimeTable.this, AddPtNameNumVenue.class);
            	add_pt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(add_pt_screen);
        		finish();
        	}else if(i == 2){
        		Intent add_nso_screen = new Intent(MyTimeTable.this, AddNSO_NCC_NSS.class);
        		add_nso_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(add_nso_screen);
        		finish();
        	}
        	
        }
        })
        .setNegativeButton("Cancel", null)
        .show();
		
	}

	protected void check_if_any_course_exists(int i) {

		SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    int course_counter = course_list_sp.getInt("counter",0);
	    
	    if(course_counter != 0){
	    	
	    	if(i == 1){ // Show week
	    		Intent show_week_tt_screen = new Intent(MyTimeTable.this, ViewWeekLongTT.class);
				startActivity(show_week_tt_screen);
	    	}else
		    	if(i == 2){ // Show courses
		    		Intent show_course_tt_screen = new Intent(MyTimeTable.this, ShowAllCoursesToEdit.class);
					startActivity(show_course_tt_screen);
		    	}
	    	
    	}else{
    		
    		BuildAlertNoCourseToShow();
    	}
	}

	private void BuildAlertNoCourseToShow() {

		new AlertDialog.Builder(this).setTitle("Empty Course List")
        .setMessage(getResources().getString(R.string.no_courses_to_show_msg))
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	Intent settings_screen = new Intent(MyTimeTable.this, Settings.class);
        	settings_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(settings_screen);
    		finish();
    		Toast.makeText(MyTimeTable.this, getResources().getString(R.string.use_temp_or_add_course_toast), Toast.LENGTH_LONG).show();
        }
        })
        .setNegativeButton("Cancel", null)
        .show();
		
	}

	@Override
    public void onBackPressed() {
		go_to_home();
	}

	private void go_to_home() {
		Intent select_year_branch_screen = new Intent(MyTimeTable.this, AppHome.class);
		select_year_branch_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(select_year_branch_screen);
		finish();
	}

}
