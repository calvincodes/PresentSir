package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EditIndividualCourseTT extends Activity {

	int course_clicked;
	int bm_meter;
	String course_name;
	String name, num, venue;
	CheckBox[] day_check = new CheckBox[5]; 
	TextView[] slot_tv = new TextView[5];
	TextView[] disp_day = new TextView[5];
	Button save, reset;
	int[] slot_to_display_in_tvs = new int[5];
	int[] day_checked_flag = new int[5];
	ImageButton[] up = new ImageButton[5];
	ImageButton[] down = new ImageButton[5];
	
	int bm_count;
	int past_bm_check;
	
	int[] past_slot_displayed_in_tvs = new int[5];
	int[] past_day_checked_flag = new int[5];
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_individual_course_tt_page);
	    
	    name = getIntent().getExtras().getString("name");
	    num = getIntent().getExtras().getString("num");
	    venue = getIntent().getExtras().getString("venue");
	    bm_count = getIntent().getExtras().getInt("bm_count");
	    bm_meter = getIntent().getExtras().getInt("bm_check");
	    course_clicked = getIntent().getExtras().getInt("pos_selected_course");
	    past_bm_check = getIntent().getExtras().getInt("past_bm_check");
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font_ab);
	    
	    initialize();
	    
	    set_previous_time_table();
	    
	    up[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[0] < 10){
					slot_to_display_in_tvs[0]++;
					set_display(slot_to_display_in_tvs[0],0);
				}
			}
		});
		
		up[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[1] < 10){
					slot_to_display_in_tvs[1]++;
					set_display(slot_to_display_in_tvs[1],1);
				}
			}
		});
		
		up[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[2] < 10){
					slot_to_display_in_tvs[2]++;
					set_display(slot_to_display_in_tvs[2],2);
				}
			}
		});
		
		up[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[3] < 10){
					slot_to_display_in_tvs[3]++;
					set_display(slot_to_display_in_tvs[3],3);
				}
			}
		});
		
		up[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[4] < 10){
					slot_to_display_in_tvs[4]++;
					set_display(slot_to_display_in_tvs[4],4);
				}
			}
		});
	    
	    down[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[0] > 1){
					slot_to_display_in_tvs[0]--;
					set_display(slot_to_display_in_tvs[0],0);
				}
			}
		});
	    
	    down[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[1] > 1){
					slot_to_display_in_tvs[1]--;
					set_display(slot_to_display_in_tvs[1],1);
				}
			}
		});
	    
	    down[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[2] > 1){
					slot_to_display_in_tvs[2]--;
					set_display(slot_to_display_in_tvs[2],2);
				}
			}
		});
	    
	    down[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[3] > 1){
					slot_to_display_in_tvs[3]--;
					set_display(slot_to_display_in_tvs[3],3);
				}
			}
		});
	    
	    down[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[4] > 1){
					slot_to_display_in_tvs[4]--;
					set_display(slot_to_display_in_tvs[4],4);
				}
			}
		});
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.set_tt_menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case R.id.reset_tt_sttm:
            reset_action();
            return true;
            
        case R.id.save_tt_sttm:
            add_action();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	private void add_action() {

		if((day_check[0].isChecked() || day_check[1].isChecked()) || day_check[2].isChecked() || day_check[3].isChecked() || day_check[4].isChecked()){
			
			SharedPreferences get_course_name_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
    		course_name = get_course_name_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
    		SharedPreferences delete_old_course_tt = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    	
    		// NOTE (IMP): The sequence of the following functions (calling) is specific to functioning required.
    		// Any change in the sequence may lead to unexpected results
    		
    		// If bm_check has been changed, then we need to update the bm_prefs accordingly
    		
	    	if(past_bm_check == 1){
	    		// Delete the course from the old bm files
	    		
	    		delete_past_bm_prefs();
	    	}
	    	
	    	// Delete the course from previous day wise time table
	    	delete_from_day_wise_tt();
	    	
	    	// Delete previous time table of the course
	    	delete_old_course_tt.edit().clear().commit();
	    	
	    	update_course_time_table();
	    	
	    	if (bm_meter == 1){
	    		// If the bm_meter is On, update the bm files
	    		update_bm_prefs();
			}
	    	
	    	// Note: If the bm check was previously On, and has been disabled now, then the
	    	// first if statement will give the delete updated from the bm_prefs and we won't
	    	// be entering to the second if statement. Hence, the removal of the required bm_prefs
	    	// will take place.
	    	
	    	update_day_wise_tt();
	    	
	    	BuildUpdateSuccessfulMsg();
		}
		else{
				Toast.makeText(EditIndividualCourseTT.this, "Please set time table of at least one day.", Toast.LENGTH_LONG).show();
		}
	}

	private void reset_action() {
		for(int i = 0; i <= 4; i++){
			day_check[i].setChecked(false);
			slot_tv[i].setText("8am - 9am");
		}
	}

	private void initialize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		day_check[0] = (CheckBox) findViewById(R.id.check_mon_eictp);
	    day_check[1] = (CheckBox) findViewById(R.id.check_tues_eictp);
	    day_check[2] = (CheckBox) findViewById(R.id.check_wed_eictp);
	    day_check[3] = (CheckBox) findViewById(R.id.check_thurs_eictp);
	    day_check[4] = (CheckBox) findViewById(R.id.check_fri_eictp);
	    
	    slot_tv[0] = (TextView) findViewById(R.id.time_slot_tv_mon_eictp);
	    slot_tv[1] = (TextView) findViewById(R.id.time_slot_tv_tues_eictp);
	    slot_tv[2] = (TextView) findViewById(R.id.time_slot_tv_wed_eictp);
	    slot_tv[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_eictp);
	    slot_tv[4] = (TextView) findViewById(R.id.time_slot_tv_fri_eictp);
	    
	    disp_day[0] = (TextView) findViewById(R.id.disp_mon_tv_eictp);
	    disp_day[1] = (TextView) findViewById(R.id.disp_tues_tv_eictp);
	    disp_day[2] = (TextView) findViewById(R.id.disp_wed_tv_eictp);
	    disp_day[3] = (TextView) findViewById(R.id.disp_thurs_tv_eictp);
	    disp_day[4] = (TextView) findViewById(R.id.disp_fri_tv_eictp);
	    
	    for(int i = 0; i <= 4; i++){
	    	slot_to_display_in_tvs[i] = 1;
	    	day_checked_flag[i] = 0;
	    	slot_tv[i].setTypeface(comic_font);
	    	disp_day[i].setTypeface(comic_font);
	    }
	    
	    up[0] = (ImageButton) findViewById(R.id.up_mon_eictp);
		up[1] = (ImageButton) findViewById(R.id.up_tues_eictp);
		up[2] = (ImageButton) findViewById(R.id.up_wed_eictp);
		up[3] = (ImageButton) findViewById(R.id.up_thurs_eictp);
		up[4] = (ImageButton) findViewById(R.id.up_fri_eictp);
	    down[0] = (ImageButton) findViewById(R.id.down_mon_eictp);
	    down[1] = (ImageButton) findViewById(R.id.down_tues_eictp);
	    down[2] = (ImageButton) findViewById(R.id.down_wed_eictp);
	    down[3] = (ImageButton) findViewById(R.id.down_thurs_eictp);
	    down[4] = (ImageButton) findViewById(R.id.down_fri_eictp);
	    
	}
	
	private void set_display(int slot_to_display,int day_to_display_in) {

		switch(slot_to_display){
			
			case 1:  slot_tv[day_to_display_in].setText("8am - 9am"); break;
			case 2:  slot_tv[day_to_display_in].setText("9am - 10am"); break;
			case 3:  slot_tv[day_to_display_in].setText("10am - 11am"); break;
			case 4:  slot_tv[day_to_display_in].setText("11am - 12pm"); break;
			case 5:  slot_tv[day_to_display_in].setText("12pm - 1pm"); break;
			case 6:  slot_tv[day_to_display_in].setText("1pm - 2pm"); break;
			case 7:  slot_tv[day_to_display_in].setText("2pm - 3pm"); break;
			case 8:  slot_tv[day_to_display_in].setText("3pm - 4pm"); break;
			case 9:  slot_tv[day_to_display_in].setText("4pm - 5pm"); break;
			case 10: slot_tv[day_to_display_in].setText("5pm - 6pm"); break;
			default: slot_tv[day_to_display_in].setText("8am - 9am"); break;
		}
	}
	
	private void set_previous_time_table() {
		
		String day = null;
		String slot_to_append = null;
		String slot_to_show = null;
		
		SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    course_name = course_list_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
	    
	    SharedPreferences course_sp = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    
	    for(int i = 0; i <= 4; i++){
			
			switch(i){
				case 0: day = "Monday"; break;
				case 1: day = "Tuesday"; break;
				case 2: day = "Wednesday"; break;
				case 3: day = "Thursday"; break;
				case 4: day = "Friday"; break;
			}
			
			if(course_sp.contains(day)){
				
				day_check[i].setChecked(true);
				past_day_checked_flag[i] = 1;
				// While setting previous time table, update the day_checked_flag array as well.
				
				for(int j = 1; j <= 10; j++){
					
					switch(j){
						case 1: slot_to_append = "8to9"; break;
						case 2: slot_to_append = "9to10"; break;
						case 3: slot_to_append = "10to11"; break;
						case 4: slot_to_append = "11to12"; break;
						case 5: slot_to_append = "12to1"; break;
						case 6: slot_to_append = "1to2"; break;
						case 7: slot_to_append = "2to3"; break;
						case 8: slot_to_append = "3to4"; break;
						case 9: slot_to_append = "4to5"; break;
						case 10: slot_to_append = "5to6"; break;	
					}
					
					
					if(course_sp.contains(day+slot_to_append)){
						
						switch(j){
							case 1: slot_to_show = "8am - 9am"; break;
							case 2: slot_to_show = "9am - 10am"; break;
							case 3: slot_to_show = "10am - 11am"; break;
							case 4: slot_to_show = "11am - 12pm"; break;
							case 5: slot_to_show = "12pm - 1pm"; break;
							case 6: slot_to_show = "1pm - 2pm"; break;
							case 7: slot_to_show = "2pm - 3pm"; break;
							case 8: slot_to_show = "3pm - 4pm"; break;
							case 9: slot_to_show = "4pm - 5pm"; break;
							case 10: slot_to_show = "5pm - 6pm"; break;	
						}
						// While setting previous time table, we will update the slot_to_display_in_tvs array
						// which will be used by the up and down buttons. Also update the past_slot_displayed_in_tvs array
						
						slot_to_display_in_tvs[i] = j;
						past_slot_displayed_in_tvs[i] = j;
						slot_tv[i].setText(slot_to_show);
						break;
					}
					
				}
			}
				
		}// end of for loop
		
	}
	
	protected void delete_past_bm_prefs() {
		
		SharedPreferences get_course_name_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_name = get_course_name_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
		SharedPreferences delete_old_course_tt = getSharedPreferences(course_name,Context.MODE_PRIVATE);

		String past_day = null;
		String past_slot = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(past_day_checked_flag[i] == 1){
				
				switch(i){
    				case 0: past_day = "Monday"; break;
    				case 1: past_day = "Tuesday"; break;
    				case 2: past_day = "Wednesday"; break;
    				case 3: past_day = "Thursday"; break;
    				case 4: past_day = "Friday"; break;
    			}
				
				SharedPreferences past_bm_day_remove = getSharedPreferences("bm_"+past_day,Context.MODE_PRIVATE);
				
				for(int j = 1; j <= 10; j++){
					
					switch(j){
						case 1: past_slot = "8to9"; break;
						case 2: past_slot = "9to10"; break;
						case 3: past_slot = "10to11"; break;
						case 4: past_slot = "11to12"; break;
						case 5: past_slot = "12to1"; break;
						case 6: past_slot = "1to2"; break;
						case 7: past_slot = "2to3"; break;
						case 8: past_slot = "3to4"; break;
						case 9: past_slot = "4to5"; break;
						case 10: past_slot = "5to6"; break;	
					}
					
					if(delete_old_course_tt.contains(past_day+past_slot)){
						// The course has a particular class on a day+slot
						// Then that slot is to be removed from the bm_day file
						past_bm_day_remove.edit().remove("name_"+past_slot).commit();
						past_bm_day_remove.edit().remove("num_"+past_slot).commit();
						past_bm_day_remove.edit().remove("venue_"+past_slot).commit();
					}
					
				}// end of for(int j = 1; j <= 10; j++)
				
			}// end of if(past_day_checked_flag[i] == 1)
			
		}// end of for(int i = 0; i <= 4; i++)
	}
	
	protected void delete_from_day_wise_tt() {
		
		SharedPreferences get_course_name_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_name = get_course_name_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
		SharedPreferences delete_old_course_tt = getSharedPreferences(course_name,Context.MODE_PRIVATE);
		
		String past_day = null;
		String past_slot = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(past_day_checked_flag[i] == 1){
				
				switch(i){
    				case 0: past_day = "Monday"; break;
    				case 1: past_day = "Tuesday"; break;
    				case 2: past_day = "Wednesday"; break;
    				case 3: past_day = "Thursday"; break;
    				case 4: past_day = "Friday"; break;
    			}
				
				SharedPreferences past_day_remove = getSharedPreferences(past_day,Context.MODE_PRIVATE);
				
				for(int j = 1; j <= 10; j++){
					
					switch(j){
						case 1: past_slot = "8to9"; break;
						case 2: past_slot = "9to10"; break;
						case 3: past_slot = "10to11"; break;
						case 4: past_slot = "11to12"; break;
						case 5: past_slot = "12to1"; break;
						case 6: past_slot = "1to2"; break;
						case 7: past_slot = "2to3"; break;
						case 8: past_slot = "3to4"; break;
						case 9: past_slot = "4to5"; break;
						case 10: past_slot = "5to6"; break;	
					}
					
					if(delete_old_course_tt.contains(past_day+past_slot)){
						// The course has a particular class on a day+slot
						// Then that slot is to be removed from the bm_day file
						past_day_remove.edit().remove("name_"+past_slot).commit();
						past_day_remove.edit().remove("num_"+past_slot).commit();
						past_day_remove.edit().remove("venue_"+past_slot).commit();
					}
					
				}// end of for(int j = 1; j <= 10; j++)
				
			}// end of if(past_day_checked_flag[i] == 1)
			
		}// end of for(int i = 0; i <= 4; i++)
		
	}

	protected void update_course_time_table() {
		
		SharedPreferences course_counter_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_course_counter = course_counter_sp.edit();
		
		edit_course_counter.putString("c" + Integer.toString(course_clicked + 1), name);
		edit_course_counter.commit();
		// Edit the name of the course in the course list - replace old name with the new one
		
		SharedPreferences add_course_sp = getSharedPreferences(name,Context.MODE_PRIVATE);
		// Create a SharedPreference with the name of the updated course name
		SharedPreferences.Editor edit_course_adder = add_course_sp.edit();
		edit_course_adder.putString("num", num);
		edit_course_adder.putString("venue", venue);
		edit_course_adder.putInt("bm_check", bm_meter);
		edit_course_adder.putInt("bunk_count", bm_count);
		
		set_day_check_flags();
		
		String day = null;
		String slot_to_append = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i]	== 1){
				
				switch(i){
					case 0: day = "Monday"; break;
					case 1: day = "Tuesday"; break;
					case 2: day = "Wednesday"; break;
					case 3: day = "Thursday"; break;
					case 4: day = "Friday"; break;
				}
				
				switch(slot_to_display_in_tvs[i]){
					case 1: slot_to_append = "8to9"; break;
					case 2: slot_to_append = "9to10"; break;
					case 3: slot_to_append = "10to11"; break;
					case 4: slot_to_append = "11to12"; break;
					case 5: slot_to_append = "12to1"; break;
					case 6: slot_to_append = "1to2"; break;
					case 7: slot_to_append = "2to3"; break;
					case 8: slot_to_append = "3to4"; break;
					case 9: slot_to_append = "4to5"; break;
					case 10: slot_to_append = "5to6"; break;
						
				}
				
				edit_course_adder.putString(day, "Yes");
				edit_course_adder.putString(day+slot_to_append, "Yes");
				
			}// end of if
		}// end of for loop
		
		edit_course_adder.commit();
	}
	
	protected void update_bm_prefs() {
		
		String day = null;
		String slot_to_append = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i]	== 1){
				
				switch(i){
					case 0: day = "Monday"; break;
					case 1: day = "Tuesday"; break;
					case 2: day = "Wednesday"; break;
					case 3: day = "Thursday"; break;
					case 4: day = "Friday"; break;
				}
				
				SharedPreferences updated_days = getSharedPreferences("BMDaysUpdated",Context.MODE_PRIVATE);
				SharedPreferences.Editor edit_ud = updated_days.edit();
				edit_ud.putString(day, "yes");
				edit_ud.commit();
				
				SharedPreferences update_tt = getSharedPreferences("bm_"+day,Context.MODE_PRIVATE);
				SharedPreferences.Editor edit_tt = update_tt.edit();
				
				switch(slot_to_display_in_tvs[i]){
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
				
				edit_tt.putString("name" + slot_to_append, name);
				edit_tt.putString("num" + slot_to_append, num);
				edit_tt.putString("venue" + slot_to_append, venue);
				edit_tt.commit();
				
			}
		}
	}
	
	protected void update_day_wise_tt() {
		
		set_day_check_flags();
		
		String day = null;
		String slot_to_append = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i]	== 1){
				
				switch(i){
					case 0: day = "Monday"; break;
					case 1: day = "Tuesday"; break;
					case 2: day = "Wednesday"; break;
					case 3: day = "Thursday"; break;
					case 4: day = "Friday"; break;
				}
				
				SharedPreferences updated_days = getSharedPreferences("DaysUpdated",Context.MODE_PRIVATE);
				SharedPreferences.Editor edit_ud = updated_days.edit();
				edit_ud.putString(day, "yes");
				edit_ud.commit();
				
				SharedPreferences update_tt = getSharedPreferences(day,Context.MODE_PRIVATE);
				SharedPreferences.Editor edit_tt = update_tt.edit();
				
				switch(slot_to_display_in_tvs[i]){
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
				
				edit_tt.putString("name" + slot_to_append, name);
				edit_tt.putString("num" + slot_to_append, num);
				edit_tt.putString("venue" + slot_to_append, venue);
				edit_tt.commit();
			}
		}
	}
	
	private void set_day_check_flags() {
		if (day_check[0].isChecked()) {	//Monday checked
            day_checked_flag[0] = 1;
        }else day_checked_flag[0] = 0;
	    
	    if (day_check[1].isChecked()) { //Tuesday checked
	    	day_checked_flag[1] = 1;
        }else day_checked_flag[1] = 0;

		if (day_check[2].isChecked()) { //Wednesday checked
			day_checked_flag[2] = 1; 
		}else day_checked_flag[2] = 0;
		
		if (day_check[3].isChecked()) {	//Thursday checked
			day_checked_flag[3] = 1; 
		}else day_checked_flag[3] = 0;
		
		if (day_check[4].isChecked()) { //Friday checked
			day_checked_flag[4] = 1; 
		}else day_checked_flag[4] = 0;
	}

	@Override
    public void onBackPressed() {
		BuildChangesNotSavedMsg();
	}
	
	private void BuildChangesNotSavedMsg() {

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Changes Not Saved")
        .setMessage(getResources().getString(R.string.changes_not_saved_msg))
        .setCancelable(true)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	Intent show_all_course_screen = new Intent(EditIndividualCourseTT.this, ShowAllCoursesToEdit.class);
    		show_all_course_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(show_all_course_screen);
    		finish();
        }
        }).setNegativeButton("No", null).show();
	}
	
	private void BuildUpdateSuccessfulMsg() {

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Changes Saved")
        .setMessage(getResources().getString(R.string.changes_saved_msg))
        .setCancelable(false)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	go_to_home();
        }
        }).show();
	}
	
	protected void go_to_home() {
		Intent home_screen = new Intent(EditIndividualCourseTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
