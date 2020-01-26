package com.arpit.iitg.presentsir;

import java.io.File;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class ViewIndividualCourseTT extends Activity {
	
	int course_clicked;
	int course_counter;
	
	TextView name, num, venue, bm_count;
	Switch bm_switch;
	CheckBox[] day_check = new CheckBox[5]; 
	TextView[] slot_tv = new TextView[5];
	TextView[] day_tv = new TextView[5];
	int[] slot_to_display_in_tvs = new int[5];
	int[] day_checked_flag = new int[5];
	
	int bm_meter;
	String course_name;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_individual_course_tt_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    // Remove app name from display
        actionBar.setDisplayShowTitleEnabled(false);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	
	    course_clicked = getIntent().getExtras().getInt("pos_selected_course");
	    
	    initialize();
	    
	    set_previous_time_table();
	    
	    // Freeze bm_switch
	    bm_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	
	    	@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  bm_switch.setChecked(false);
			      }else{
			    	  bm_switch.setChecked(true);
			      }
			      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
	     		}
    	});
	    
	    day_check[0].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	
	    	@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  day_check[0].setChecked(false);
			    	 
			      }else{
			    	  day_check[0].setChecked(true);
			      }
			      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
	     		}
	    });
	    
		day_check[1].setOnCheckedChangeListener(new OnCheckedChangeListener() {
			    	
			    	@Override
				     public void onCheckedChanged(CompoundButton buttonView,
				       boolean isChecked) {
				   
					      if(isChecked){
					    	  day_check[1].setChecked(false);
					    	 
					      }else{
					    	  day_check[1].setChecked(true);
					      }
					      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
			     		}
			    });
		
		day_check[2].setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  day_check[2].setChecked(false);
			    	 
			      }else{
			    	  day_check[2].setChecked(true);
			      }
			      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
		 		}
		});
		
		day_check[3].setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  day_check[3].setChecked(false);
			    	 
			      }else{
			    	  day_check[3].setChecked(true);
			      }
			      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
		 		}
		});
		
		day_check[4].setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  day_check[4].setChecked(false);
			    	 
			      }else{
			    	  day_check[4].setChecked(true);
			      }
			      Toast.makeText(ViewIndividualCourseTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
		 		}
		});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_courses_menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.vcm_delete_course:
        	BuildMsgAreYouSure();
            return true;
        case R.id.vcm_edit_course:
            
        	edit_course();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	private void edit_course() {
		Intent edit_indiv_screen = new Intent(ViewIndividualCourseTT.this, EditIndividualCourseNameNumVenuBC.class);
		edit_indiv_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		edit_indiv_screen.putExtra("pos_selected_course", course_clicked);
  		startActivity(edit_indiv_screen);
	}

	private void initialize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		name = (TextView) findViewById(R.id.course_name_victp);
	    num = (TextView) findViewById(R.id.course_num_victp);
	    venue = (TextView) findViewById(R.id.course_venue_victp);
	    bm_count = (TextView) findViewById(R.id.bm_count_tv_victp);
	    bm_switch = (Switch) findViewById(R.id.bunk_meter_switch_victp);
	    
	    day_check[0] = (CheckBox) findViewById(R.id.check_mon_victp);
	    day_check[1] = (CheckBox) findViewById(R.id.check_tues_victp);
	    day_check[2] = (CheckBox) findViewById(R.id.check_wed_victp);
	    day_check[3] = (CheckBox) findViewById(R.id.check_thurs_victp);
	    day_check[4] = (CheckBox) findViewById(R.id.check_fri_victp);
	    
	    slot_tv[0] = (TextView) findViewById(R.id.time_slot_tv_mon_victp);
	    slot_tv[1] = (TextView) findViewById(R.id.time_slot_tv_tues_victp);
	    slot_tv[2] = (TextView) findViewById(R.id.time_slot_tv_wed_victp);
	    slot_tv[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_victp);
	    slot_tv[4] = (TextView) findViewById(R.id.time_slot_tv_fri_victp);
	    
	    day_tv[0] = (TextView) findViewById(R.id.mon_disp_tv_victp);
	    day_tv[1] = (TextView) findViewById(R.id.tues_disp_tv_victp);
	    day_tv[2] = (TextView) findViewById(R.id.wed_disp_tv_victp);
	    day_tv[3] = (TextView) findViewById(R.id.thurs_disp_tv_victp);
	    day_tv[4] = (TextView) findViewById(R.id.fri_disp_tv_victp);
	    
	    for(int i = 0; i <= 4; i++){
	    	slot_to_display_in_tvs[i] = 1;
	    	day_checked_flag[i] = 0;
	    	slot_tv[i].setTypeface(comic_font);
	    	day_tv[i].setTypeface(comic_font);
	    }
	    
	    name.setTypeface(comic_font);
	    name.setPaintFlags(name.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    num.setTypeface(comic_font);
	    venue.setTypeface(comic_font);
	    bm_count.setTypeface(comic_font);
	    bm_switch.setTypeface(comic_font);
		
	}
	
	private void set_previous_time_table() {
		
		String course_name = null;
		String day = null;
		String slot_to_append = null;
		String slot_to_show = null;
		
		SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    course_name = course_list_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
	    
	    SharedPreferences course_sp = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    
	    name.setText(course_name);
	    num.setText(course_sp.getString("num", "N/A"));
	    venue.setText("Venue: "+course_sp.getString("venue", "N/A"));
	    bm_count.setText("Bunk Count: "+Integer.toString(course_sp.getInt("bunk_count", 0)));
	    bm_meter = course_sp.getInt("bm_check", 0);
	    
	    if(bm_meter == 1){
	    	bm_switch.setChecked(true);
	    }else{
	    	bm_switch.setChecked(false);
	    }
		
	    
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
				day_checked_flag[i] = 1;
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
						slot_tv[i].setText(slot_to_show);
						break;
					}
					
				}
			}else{
				slot_tv[i].setText("No class");
				slot_tv[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			}
				
		}// end of for loop
		
	}
	
	protected void BuildMsgAreYouSure() {
		
		new AlertDialog.Builder(this).setTitle("Delete Course")
        .setMessage(getResources().getString(R.string.course_delete_msg))
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	delete_course();
        }
        }).setNegativeButton("Cancel", null).show();
		
	}

	protected void delete_course() {
		
		SharedPreferences get_course_name_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_name = get_course_name_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
		SharedPreferences delete_old_course_tt = getSharedPreferences(course_name,Context.MODE_PRIVATE);
    	
		// NOTE (IMP): The sequence of the following functions (calling) is specific to functioning required.
		// Any change in the sequence may lead to unexpected results
		
		// If bm_check has been changed, then we need to update the bm_prefs accordingly
		
    	if(bm_meter == 1){
    		// Delete the course from the old bm files
    		delete_past_bm_prefs();
    	}
    	
    	// Delete the course from previous day wise time table
    	delete_from_day_wise_tt();
    	
    	// Delete previous time table of the course
    	delete_old_course_tt.edit().clear().commit();
    	
    	// Update the CourseList
    	update_course_list();
    	
    	go_to_home();
    	// Go to home is must to set the updated alarms.
	}

	private void delete_past_bm_prefs() {

		SharedPreferences delete_past_bm_prefs_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_name = delete_past_bm_prefs_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
		SharedPreferences delete_old_course_tt_under_past_bm_prefs = getSharedPreferences(course_name,Context.MODE_PRIVATE);

		String past_day = null;
		String past_slot = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i] == 1){
				
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
					
					if(delete_old_course_tt_under_past_bm_prefs.contains(past_day+past_slot)){
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
	
	private void delete_from_day_wise_tt() {

		SharedPreferences get_course_name_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_name = get_course_name_sp.getString("c" + Integer.toString(course_clicked+1), "N/A");
		SharedPreferences delete_old_course_tt = getSharedPreferences(course_name,Context.MODE_PRIVATE);
		
		String past_day = null;
		String past_slot = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i] == 1){
				
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
	
	private void update_course_list() {
		
		// IMPORTANT: The course selected is (course_clicked + 1) and not just (course_clicked)
		
		SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		course_counter = course_list_sp.getInt("counter",0);
		String temp_name = null;

		if((course_clicked+1) < course_counter){ // If the course under the deletion process
												// is not the last course in the database.
												// In this case, we will shift courses upwards.
			for (int i = course_clicked + 2; i <= course_counter; i++){
				
					SharedPreferences.Editor course_list_editor = course_list_sp.edit();
					temp_name = course_list_sp.getString("c" + Integer.toString(i), "N/A");
					course_list_editor.putString("c" + Integer.toString(i-1), temp_name);
					course_list_editor.commit();
					
					if(i == course_counter){
						course_list_sp.edit().remove("c"+Integer.toString(i)).commit();
					}
			}
		}else if((course_clicked+1) == course_counter){ // If the course under the deletion process
														// is the last course in the database.
														// Then simply delete it.
			course_list_sp.edit().remove("c"+Integer.toString(course_counter)).commit();
		}
		
		SharedPreferences.Editor course_counter_editor = course_list_sp.edit();
		course_counter_editor.putInt("counter", course_counter - 1);
		course_counter_editor.commit();
	}
	
	@Override
    public void onBackPressed() {
		Intent show_all_courses_screen = new Intent(ViewIndividualCourseTT.this, ShowAllCoursesToEdit.class);
		show_all_courses_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(show_all_courses_screen);
		finish();
	}
	private void go_to_home() {
		Intent home_screen = new Intent(ViewIndividualCourseTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
