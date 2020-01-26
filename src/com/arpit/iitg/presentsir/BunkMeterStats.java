package com.arpit.iitg.presentsir;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class BunkMeterStats extends Activity {
	
	int course_counter;
	int bm_on_courses = 0;

	private ListView mainListView ;
	private CustomListAdapter listAdapter ;
	
	TextView title;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.bunk_meter_stats_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    mainListView = (ListView) findViewById(R.id.show_bm_stats_list_bmsp);
	    title = (TextView) findViewById(R.id.bm_stats_title_bmsp);
	    
	    title.setTypeface(comic_font);
	    title.setPaintFlags(title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    
	    SharedPreferences course_list_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
	    course_counter = course_list_sp.getInt("counter",0);
	    
	    if(course_counter != 0){
	    	
	    	for(int i = 1; i <= course_counter; i++){
	    		
	    		String course_name = course_list_sp.getString("c" + Integer.toString(i), "N/A");
	    		SharedPreferences course_sp = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    		
	    		if(course_sp.getInt("bm_check", 0) == 1){
	    			bm_on_courses++;
	    		}
	    	}
	    	
	    	SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
	    	
	    	if(pt_tt_sp.getInt("bm_check", 0) == 1)
	    		bm_on_courses++;
	    	
	    	SharedPreferences nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
	    	
	    	if(nso_nss_nss_tt_sp.getInt("bm_check", 0) == 1)
	    		bm_on_courses++;
	    	
	    	String[] bm_on_course_array = new String[bm_on_courses];
	    	
	    	int temp = 0;
	    	
	    	for(int i = 1; i <= course_counter; i++){
	    		
	    		String course_name = course_list_sp.getString("c" + Integer.toString(i), "N/A");
	    		SharedPreferences course_sp = getSharedPreferences(course_name,Context.MODE_PRIVATE);
	    		
	    		if(course_sp.getInt("bm_check", 0) == 1){
	    			bm_on_course_array[temp] = " " + Integer.toString(temp+1) + ". " + course_name + ": " + Integer.toString(course_sp.getInt("bunk_count", 0)) + "\n";
	    			temp++;
	    		}
	    	}
	    	
	    	if(pt_tt_sp.getInt("bm_check", 0) == 1){
	    		bm_on_course_array[temp] = " " + Integer.toString(temp+1) + ". Physical Training " + Integer.toString(pt_tt_sp.getInt("bunk_count", 0)) + "\n";
	    		temp++;
	    	}
	    	
	    	if(nso_nss_nss_tt_sp.getInt("bm_check", 0) == 1){
	    		
	    		String name = null;
	    		
	    		switch(nso_nss_nss_tt_sp.getInt("course_value",0)){
	    			case 1: name = "NSO"; break;
	    			case 2: name = "NSS"; break;
	    			case 3: name = "NCC"; break;
	    		}
	    		
	    		bm_on_course_array[temp] = " " + Integer.toString(temp+1) + ". " + name + ": " + Integer.toString(nso_nss_nss_tt_sp.getInt("bunk_count", 0)) + "\n";
	    		temp++;
	    	}
	    	
	    	if(bm_on_courses == 0){
	    		
	    		String[] no_course_with_bm_on_array = new String[4];
	    		no_course_with_bm_on_array[0] = "\n";
	    		no_course_with_bm_on_array[1] = "\n";
	    		no_course_with_bm_on_array[2] = "\n";
	    		no_course_with_bm_on_array[3] = "There are no courses with Bunk Meter On\n";
	    		
	    		ArrayList<String> NoCourseList = new ArrayList<String>();
	    		NoCourseList.addAll( Arrays.asList(no_course_with_bm_on_array) );
		    	
		    	listAdapter = new CustomListAdapter(BunkMeterStats.this , R.layout.tv_for_custom_list_view , NoCourseList);
		    	mainListView.setAdapter(listAdapter);
	    	}else{
	    		
	    		ArrayList<String> CourseList = new ArrayList<String>();
		    	CourseList.addAll( Arrays.asList(bm_on_course_array) );
		    	
		    	//listAdapter = new ArrayAdapter<String>(this, R.layout.list_row_page, CourseList);
		    	//mainListView.setAdapter( listAdapter );
		    	
		    	listAdapter = new CustomListAdapter(BunkMeterStats.this , R.layout.tv_for_custom_list_view , CourseList);
		    	mainListView.setAdapter(listAdapter);
	    	}
	    	
    	}
	    
	    /*
	    mainListView.setOnItemClickListener(new OnItemClickListener() {
  		  public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
  		    long arg3) {
  		//	Toast.makeText(BunkMeterStats.this,Integer.toString(temp_pos[pos]), Toast.LENGTH_LONG).show();
  			  
  			  
	  		  Intent view_indi_course = new Intent(BunkMeterStats.this, ViewIndividualCourseTT.class);
	  		  view_indi_course.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  		  view_indi_course.putExtra("pos_selected_course", pos);
	  		  startActivity(view_indi_course);  
	  		  
  		  }
  		  
	    });
	    */

	}
	
	@Override
    public void onBackPressed() {
		go_to_home();
	}

	private void go_to_home() {
		
		Intent app_home_screen = new Intent(BunkMeterStats.this, AppHome.class);
		app_home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(app_home_screen);
		finish();
	}

}
