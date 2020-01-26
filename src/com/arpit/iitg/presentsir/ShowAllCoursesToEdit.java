package com.arpit.iitg.presentsir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowAllCoursesToEdit extends Activity  {
	
	int course_counter;

	private ListView mainListView ;
	private CustomListAdapter listAdapter ;
	
	TextView title, select_course;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_all_courses_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    // 	Remove app name from display
        actionBar.setDisplayShowTitleEnabled(false);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	   
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.show_all_course_list_sactep);
	    title = (TextView) findViewById(R.id.all_courses_title_sactep);
	    select_course = (TextView) findViewById(R.id.select_any_course_tv_sactep);
	    
	    title.setTypeface(comic_font);
	    select_course.setTypeface(comic_font);
	    title.setPaintFlags(title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    
	    SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    course_counter = course_list_sp.getInt("counter",0);
	    
	    if(course_counter != 0){
	    	
	    	String[] course_array = new String[course_counter];
	    	
	    	for(int i = 1; i <= course_counter; i++){
	    		
	    		String course_name = course_list_sp.getString("c" + Integer.toString(i), "N/A");
	    		SharedPreferences course_sp = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    		course_array[i-1] = " " + Integer.toString(i) + ". " + course_name + "\n";
	    	}
	    	
	    	ArrayList<String> CourseList = new ArrayList<String>();
	    	CourseList.addAll( Arrays.asList(course_array) );
	    	
	    	//listAdapter = new ArrayAdapter<String>(this, R.layout.list_row_page, CourseList);
	    	//mainListView.setAdapter( listAdapter );
	    	
	    	listAdapter = new CustomListAdapter(ShowAllCoursesToEdit.this , R.layout.tv_for_custom_list_view , CourseList);
	    	mainListView.setAdapter(listAdapter);
	    	
    	}else{
    		
    		BuildAlertNoCourseToShow();
    	}
	    
	    mainListView.setOnItemClickListener(new OnItemClickListener() {
  		  public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
  		    long arg3) {
  			  
	  		  Intent view_indi_course = new Intent(ShowAllCoursesToEdit.this, ViewIndividualCourseTT.class);
	  		  view_indi_course.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  		  view_indi_course.putExtra("pos_selected_course", pos);
	  		  startActivity(view_indi_course);  
	  		  
  		  }

	    });
	   
	}
	
	private void BuildAlertNoCourseToShow() {

		new AlertDialog.Builder(this).setTitle("Empty Course List")
        .setMessage(getResources().getString(R.string.no_courses_to_show_msg))
        .setCancelable(false)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	Intent settings_screen = new Intent(ShowAllCoursesToEdit.this, Settings.class);
        	settings_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(settings_screen);
    		finish();
        }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	Intent my_tt_screen = new Intent(ShowAllCoursesToEdit.this, MyTimeTable.class);
        	my_tt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(my_tt_screen);
    		finish();
        }
        })
        .show();
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
        	Intent week_long_screen = new Intent(ShowAllCoursesToEdit.this, ViewWeekLongTT.class);
        	week_long_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(week_long_screen);
    		finish();
            return true;
        case R.id.course_wise:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
    public void onBackPressed() {
		go_to_my_tt();
	}

	private void go_to_my_tt() {
		Intent my_tt_screen = new Intent(ShowAllCoursesToEdit.this, MyTimeTable.class);
		my_tt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(my_tt_screen);
		finish();
	}

}
