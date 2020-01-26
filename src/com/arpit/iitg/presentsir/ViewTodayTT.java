package com.arpit.iitg.presentsir;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class ViewTodayTT extends Activity {

	TextView tv, title_tv;
	String today;
	int hours;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_today_tt_page);
	    
	 // Enable Back Navigation   
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	    
		Drawable d = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(d);
        
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
		
		title_tv = (TextView) findViewById(R.id.course_name_num_tv_vttt);
	    tv = (TextView) findViewById(R.id.course_day_time_venue_tv_vttt);
	    
	    title_tv.setTypeface(comic_font);
	    tv.setTypeface(comic_font);
	    
	    title_tv.setPaintFlags(title_tv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	
	    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
	    Calendar calendar = Calendar.getInstance();
	    today = dayFormat.format(calendar.getTime());	// Get today's day
	    
	    Time dtNow = new Time();
	    dtNow.setToNow();
	    hours = dtNow.hour;	// Get current hour
	    
	    if(today.equals("Saturday") || today.equals("Sunday")){
	    	// Today is an holiday
	    	
	    	SharedPreferences read_existing_days = getApplicationContext().getSharedPreferences("DaysUpdated",Context.MODE_PRIVATE);
			
			if (read_existing_days.contains("Monday"))
				display_next_class_next_day("Monday");
			else
				if (read_existing_days.contains("Tuesday"))
					display_next_class_next_day("Tuesday");
				else
					if (read_existing_days.contains("Wednesday"))
						display_next_class_next_day("Wednesday");
					else
						if (read_existing_days.contains("Thursday"))
							display_next_class_next_day("Thursday");
						else
							if (read_existing_days.contains("Friday"))
								display_next_class_next_day("Friday");
			
			tv.append("\n\nPS: Enjoy your weekend!\nDo not worry about the classes.");
				
	    }
	    else{
	    	// Today is not an holiday
	    	
	    	SharedPreferences read_existing_days = getApplicationContext().getSharedPreferences("DaysUpdated",Context.MODE_PRIVATE);
			
			if (read_existing_days.contains(today) && hours >= 0 && hours < 18){
				// Current day, time between 12am to 6pm -
				// TimeTable of same day to be displayed
				display_next_class_today(today);
			}
			else if (read_existing_days.contains(today) && hours >= 18){
				// Current day, time is more than 6pm = or +
				// TimeTable of next day to be displayed
				
				if(today.equals("Monday")){
					if (read_existing_days.contains("Tuesday")){
						display_next_class_next_day("Tuesday");
					}else{
						title_tv.setText("");
						tv.setText("Enjoy!\nYou have no classes on Tuesday");
					}
				}else
					if(today.equals("Tuesday")){
						if (read_existing_days.contains("Wednesday")){
							display_next_class_next_day("Wednesday");
						}else{
							title_tv.setText("");
							tv.setText("Enjoy!\nYou have no classes on Wednesday");
						}
					}else
						if(today.equals("Wednesday")){
							if (read_existing_days.contains("Thursday")){
								display_next_class_next_day("Thursday");
							}else{
								title_tv.setText("");
								tv.setText("Enjoy!\nYou have no classes on Thursday");
							}
						}else
							if(today.equals("Thursday")){
							if (read_existing_days.contains("Friday")){
								display_next_class_next_day("Friday");
							}else{
								title_tv.setText("");
								tv.setText("Enjoy!\nYou have no classes on Friday");
							}
						}else
							if(today.equals("Friday")){
								if (read_existing_days.contains("Monday")){
									display_next_class_next_day("Monday");
								}else{
									title_tv.setText("");
									tv.setText("Enjoy your weekend!\nYou have no classes on Monday");
								}
							}
				
			}
			else
			{	// Current day does not exist.
				title_tv.setText("");
				tv.setText("Sorry! We could not find your " + today + "'s time-table. Please update your time table");
			}
	    }
	    
	    if(tv.getText().toString().equals("")){
	    	title_tv.setText("");
	    	tv.setText("Sorry! We could not complete your request.\nPlease try again. If problem persists, report it to us.");
	    }
	    
	}
	
	private void display_next_class_next_day(String next_day_to_show_tt) {
		
		SharedPreferences open_tt_to_display = getApplicationContext().getSharedPreferences(next_day_to_show_tt,Context.MODE_PRIVATE);
		
		if(open_tt_to_display.contains("name_8to9")){
			// Current time is 8am -, then next class will be of 8am
			tv.setText(next_day_to_show_tt+" (8am - 9am)");
			append_tv(next_day_to_show_tt,8,9);
		}else
			if(open_tt_to_display.contains("name_9to10")){
				// Current time is 9am -, then next class will be of 9am
				tv.setText(next_day_to_show_tt+" (9am - 10am)");
				append_tv(next_day_to_show_tt,9,10);
			}else
				if(open_tt_to_display.contains("name_10to11")){
					// Current time is 10am -, then next class will be of 10am
					tv.setText(next_day_to_show_tt+" (10am - 11am)");
					append_tv(next_day_to_show_tt,10,11);
				}else
					if(open_tt_to_display.contains("name_11to12")){
						// Current time is 11am -, then next class will be of 11am
						tv.setText(next_day_to_show_tt+" (11am - 12pm)");
						append_tv(next_day_to_show_tt,11,12);
					}else
						if(open_tt_to_display.contains("name_12to1")){
							// Current time is 12pm -, then next class will be of 12pm
							tv.setText(next_day_to_show_tt+" (12pm - 1pm)");
							append_tv(next_day_to_show_tt,12,1);
						}else
							if(open_tt_to_display.contains("name_1to2")){
								// Current time is 1pm -, then next class will be of 1pm
								tv.setText(next_day_to_show_tt+" (1pm - 2pm)");
								append_tv(next_day_to_show_tt,1,2);
							}else
								if(open_tt_to_display.contains("name_2to3")){
									// Current time is 2pm -, then next class will be of 2pm
									tv.setText(next_day_to_show_tt+" (2pm - 3pm)");
									append_tv(next_day_to_show_tt,2,3);
								}else
									if(open_tt_to_display.contains("name_3to4")){
										// Current time is 3pm -, then next class will be of 3pm
										tv.setText(next_day_to_show_tt+" (3pm - 4pm)");
										append_tv(next_day_to_show_tt,3,4);
									}else
										if(open_tt_to_display.contains("name_4to5")){
											// Current time is 4pm -, then next class will be of 4pm
											tv.setText(next_day_to_show_tt+" (4pm - 5pm)");
											append_tv(next_day_to_show_tt,4,5);
										}else
											if(open_tt_to_display.contains("name_5to6")){
												// Current time is 5pm -, then next class will be of 5pm
												tv.setText(next_day_to_show_tt+" (5pm - 6pm)");
												append_tv(next_day_to_show_tt,5,6);
											}
											else{
												tv.setText("Enjoy!\nYou have no classes on "+next_day_to_show_tt+" till 6pm.");
											}
	}

	private void display_next_class_today(String show_tt_day) {
		
		SharedPreferences open_tt_to_display = getApplicationContext().getSharedPreferences(show_tt_day,Context.MODE_PRIVATE);
		
		if(open_tt_to_display.contains("name_8to9") && hours < 8){
			// Current time is 8am -, then next class will be of 8am
			tv.setText(show_tt_day+" (8am - 9am)");
			append_tv(show_tt_day,8,9);
		}else
			if(open_tt_to_display.contains("name_9to10") && hours < 9){
				// Current time is 9am -, then next class will be of 9am
				tv.setText(show_tt_day+" (9am - 10am)");
				append_tv(show_tt_day,9,10);
			}else
				if(open_tt_to_display.contains("name_10to11") && hours < 10){
					// Current time is 10am -, then next class will be of 10am
					tv.setText(show_tt_day+" (10am - 11am)");
					append_tv(show_tt_day,10,11);
				}else
					if(open_tt_to_display.contains("name_11to12") && hours < 11){
						// Current time is 11am -, then next class will be of 11am
						tv.setText(show_tt_day+" (11am - 12pm)");
						append_tv(show_tt_day,11,12);
					}else
						if(open_tt_to_display.contains("name_12to1") && hours < 12){
							// Current time is 12pm -, then next class will be of 12pm
							tv.setText(show_tt_day+" (12pm - 1pm)");
							append_tv(show_tt_day,12,1);
						}else
							if(open_tt_to_display.contains("name_1to2") && hours < 13){
								// Current time is 1pm -, then next class will be of 1pm
								tv.setText(show_tt_day+" (1pm - 2pm)");
								append_tv(show_tt_day,1,2);
							}else
								if(open_tt_to_display.contains("name_2to3") && hours < 14){
									// Current time is 2pm -, then next class will be of 2pm
									tv.setText(show_tt_day+" (2pm - 3pm)");
									append_tv(show_tt_day,2,3);
								}else
									if(open_tt_to_display.contains("name_3to4") && hours < 15){
										// Current time is 3pm -, then next class will be of 3pm
										tv.setText(show_tt_day+" (3pm - 4pm)");
										append_tv(show_tt_day,3,4);
									}else
										if(open_tt_to_display.contains("name_4to5") && hours < 16){
											// Current time is 4pm -, then next class will be of 4pm
											tv.setText(show_tt_day+" (4pm - 5pm)");
											append_tv(show_tt_day,4,5);
										}else
											if(open_tt_to_display.contains("name_5to6") && hours < 17){
												// Current time is 5pm -, then next class will be of 5pm
												tv.setText(show_tt_day+" (5pm - 6pm)");
												append_tv(show_tt_day,5,6);
											}
											else{
												tv.setText("Enjoy!\nYou have no classes on "+show_tt_day+" till 6pm.");
											}
	}

	private void append_tv(String day_to_append, int start_time, int end_time) {
		SharedPreferences open_tt_to_display = getApplicationContext().getSharedPreferences(day_to_append,Context.MODE_PRIVATE);
		
		title_tv.setText(open_tt_to_display.getString("name_"+Integer.toString(start_time)+"to"+Integer.toString(end_time), "N/A"));
		title_tv.append("\n\n(" + open_tt_to_display.getString("num_"+Integer.toString(start_time)+"to"+Integer.toString(end_time), "N/A")+")");
		
		tv.append("\nVenue: " + open_tt_to_display.getString("venue_"+Integer.toString(start_time)+"to"+Integer.toString(end_time), "N/A"));
	}
	
	@Override
    public void onBackPressed() {
		Intent home_screen = new Intent(ViewTodayTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
