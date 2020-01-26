package com.arpit.iitg.presentsir;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewWeekLongTT extends Activity{
	
	ImageButton up,down;
	TextView day_slot_tv;
	String today;
	
	private ListView mainListView ;
	private CustomListAdapter listAdapter ;
	
	int day_to_disp = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_week_long_tt_page);
	    
	    // get action bar, // Enabling Up - Back navigation
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Remove app name from display
        actionBar.setDisplayShowTitleEnabled(false);
        
        Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    up = (ImageButton) findViewById(R.id.up_view_week_tt_vwlp);
	    down = (ImageButton) findViewById(R.id.down_view_week_tt_vwlp);
	    day_slot_tv = (TextView) findViewById(R.id.day_slot_view_week_tt_vwlp);
	    
	    mainListView = (ListView) findViewById( R.id.show_week_long_day_wise_list_vwlp);
	    
	    day_slot_tv.setTypeface(comic_font);
	    
	    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
	    Calendar calendar = Calendar.getInstance();
	    today = dayFormat.format(calendar.getTime());	// Get today's day
	    
	    if(today.equals("Monday")){
	    	show_day_tt(1);
	    	day_to_disp = 1;
	    }
	    else if(today.equals("Tuesday")){
	    	show_day_tt(2);
	    	day_to_disp = 2;
	    }
	    else if(today.equals("Wednesday")){
	    	show_day_tt(3);
	    	day_to_disp = 3;
	    }
	    else if(today.equals("Thursday")){
	    	show_day_tt(4);
	    	day_to_disp = 4;
	    }
	    else if(today.equals("Friday")){
	    	show_day_tt(5);
	    	day_to_disp = 5;
	    }
	    else{
	    	show_day_tt(1);
	    	day_to_disp = 1;
	    }
	    
	    up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(day_to_disp < 5){
					day_to_disp ++;
					show_day_tt(day_to_disp);
				}else
					if(day_to_disp == 5){
						day_to_disp = 1;
						show_day_tt(day_to_disp);
					}
				
			}
		});
	    
	    down.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(day_to_disp > 1){
					day_to_disp --;
					show_day_tt(day_to_disp);
				}else
					if(day_to_disp == 1){
						day_to_disp = 5;
						show_day_tt(day_to_disp);
					}
				
			}
		});
	    
	    
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_between_weekly_or_course_wise, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.weekly:
            //go_to_home();
            return true;
        case R.id.course_wise:
        	Intent all_courses_screen = new Intent(ViewWeekLongTT.this, ShowAllCoursesToEdit.class);
        	all_courses_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(all_courses_screen);
    		finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	protected void show_day_tt(int day_displayed) {
		
		String day_to_show = null;
		
		switch(day_displayed){
			case 1: day_to_show = "Monday"; break;
			case 2: day_to_show = "Tuesday"; break;
			case 3: day_to_show = "Wednesday"; break;
			case 4: day_to_show = "Thursday"; break;
			case 5: day_to_show = "Friday"; break;
		}
		
		day_slot_tv.setText(day_to_show);
		
		SharedPreferences get_tt_to_display = getSharedPreferences(day_to_show,Context.MODE_PRIVATE);
		
		int str_len = 0;
		
		if(get_tt_to_display.contains("name_8to9"))
			str_len++;
    	
		if(get_tt_to_display.contains("name_9to10"))
			str_len++;
		
		if(get_tt_to_display.contains("name_10to11"))
			str_len++;
		
		if(get_tt_to_display.contains("name_11to12"))
			str_len++;
		
		if(get_tt_to_display.contains("name_12to1"))
			str_len++;
		
		if(get_tt_to_display.contains("name_1to2"))
			str_len++;
		
		if(get_tt_to_display.contains("name_2to3"))
			str_len++;
		
		if(get_tt_to_display.contains("name_3to4"))
			str_len++;
		
		if(get_tt_to_display.contains("name_4to5"))
			str_len++;
		
		if(get_tt_to_display.contains("name_5to6"))
			str_len++;
		
		// The code for str_len is added as the CustomListAdapter was not working properly
		// with pre-defined array size of the array day_tt_array.
		
		if(str_len != 0){
			// We have course to show on that day
			
			String[] day_tt_array = new String[str_len];
			
			int i = 0;
			
			if(get_tt_to_display.contains("name_8to9")){
				day_tt_array[i] = "8am - 9am: " + get_tt_to_display.getString("name_8to9", "N/A") + "\n";
				i++;
			}
	    	
			if(get_tt_to_display.contains("name_9to10")){
				day_tt_array[i] = "9am - 10am: " + get_tt_to_display.getString("name_9to10", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_10to11")){
				day_tt_array[i] = "10am - 11am: " + get_tt_to_display.getString("name_10to11", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_11to12")){
				day_tt_array[i] = "11am - 12pm: " + get_tt_to_display.getString("name_11to12", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_12to1")){
				day_tt_array[i] = "12pm - 1pm: " + get_tt_to_display.getString("name_12to1", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_1to2")){
				day_tt_array[i] = "1pm - 2pm: " + get_tt_to_display.getString("name_1to2", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_2to3")){
				day_tt_array[i] = "2pm - 3pm: " + get_tt_to_display.getString("name_2to3", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_3to4")){
				day_tt_array[i] = "3pm - 4pm: " + get_tt_to_display.getString("name_3to4", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_4to5")){
				day_tt_array[i] = "4pm - 5pm: " + get_tt_to_display.getString("name_4to5", "N/A") + "\n";
				i++;
			}
			
			if(get_tt_to_display.contains("name_5to6")){
				day_tt_array[i] = "5pm - 6pm: " + get_tt_to_display.getString("name_5to6", "N/A") + "\n";
				i++;
			}
	    	
	    	ArrayList<String> CourseList = new ArrayList<String>();
	    	CourseList.addAll( Arrays.asList(day_tt_array) );
	    	
	    	listAdapter = new CustomListAdapter(ViewWeekLongTT.this , R.layout.tv_for_custom_list_view , CourseList);
	    	mainListView.setAdapter(listAdapter);
			
		}else{
			// We do not have any course to display for that day
			
			String[] no_course_array = new String[1];
			no_course_array[0] = "There is no class on "
					+ day_to_show
					+ " in your timetable";
			
			ArrayList<String> CourseList = new ArrayList<String>();
	    	CourseList.addAll( Arrays.asList(no_course_array) );
	    	
	    	listAdapter = new CustomListAdapter(ViewWeekLongTT.this , R.layout.tv_for_custom_list_view , CourseList);
	    	mainListView.setAdapter(listAdapter);
		}
		
		
    	
    	//listAdapter = new ArrayAdapter<String>(this, R.layout.list_row_page, CourseList);
    	//mainListView.setAdapter( listAdapter );
	}

	@Override
    public void onBackPressed() {
		go_to_my_tt();
	}

	private void go_to_my_tt() {
		Intent my_tt_screen = new Intent(ViewWeekLongTT.this, MyTimeTable.class);
		my_tt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(my_tt_screen);
		finish();
	}

}
