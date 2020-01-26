package com.arpit.iitg.presentsir;

import java.io.File;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowInBuildTemplates extends Activity {
	
	Button add;
	
	TextView your_courses, auto_add_msg;
	
	int year, branch;
	private ListView mainListView ;
	private CustomListAdapter listAdapter ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_in_built_templates_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    year = getIntent().getExtras().getInt("year");
	    branch = getIntent().getExtras().getInt("branch");
	    
	    mainListView = (ListView) findViewById( R.id.compulsory_courses_list_sibtp);
	    add = (Button) findViewById(R.id.add_courses_sibtp);
	    your_courses = (TextView) findViewById( R.id.your_courses_tv_sibtp);
	    auto_add_msg = (TextView) findViewById( R.id.auto_add_msg_tv_sibtp);
	    
	    your_courses.setTypeface(comic_font);
	    your_courses.setPaintFlags(your_courses.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    auto_add_msg.setTypeface(comic_font);
	    add.setTypeface(comic_font);
	    
	    decide_courses();
	    
	    add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences first_time_update_sp = getApplicationContext().getSharedPreferences("FirstTimeUpdate",Context.MODE_PRIVATE);
				int first_update = first_time_update_sp.getInt("First", 0);
				
				if(first_update == 1)
					BuildMsgAreYouSure();
				else
					update();
			}
		});
	
	}

	protected void BuildMsgAreYouSure() {
		
		new AlertDialog.Builder(this).setTitle("Auto-Add Courses")
        .setMessage(getResources().getString(R.string.sure_add_courses))
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	
        	update();
        }
        }).setNegativeButton("Cancel", null).show();
	}

	protected void update() {

		delete_course();
    	set_new_time_table();
    	
    	set_first_time_update_done();
    	
    	if(year == 4 && branch == 3)
    		// Special Message for 4th Year, CL about CL 403 class.
    		BuildSpecialMsgForChemical4thYear();
    	
    	if(year == 4 && branch == 5)
    		// Special Message for 4th Year, CST about CH 402 class.
    		BuildSpecialMsgForCST4thYear();
    	
    	if(year == 4 && branch == 6)
    		// Special Message for 4th Year, DD about DD 401 and BTP (DD) class.
    		BuildSpecialMsgForDesign4thYear();
    	
    	else if(year == 3 && branch == 3)
    		// Special Message for 3rd Year, CL about CL 304 class.
    		BuildSpecialMsgForChemical3rdYear();
    	
    	else if(year == 3 && branch == 6)
    		// Special Message for 3rd Year, DD about DD 301 and DD 305 class.
    		BuildSpecialMsgForDesign3rdYear();
    	
    	else if(year == 3 && branch == 10)
    		// Special Message for 3rd Year, ME about 1005/3..
    		BuildSpecialMsgForMech3rdYear();
    	
    	else if(year == 2 && branch == 10)
    		// Special Message for 2nd Year, ME about 1005/3.
    		BuildSpecialMsgForMech2ndYear();
    	
    	else if(year == 2 && (branch == 3 || branch == 5))
    		// Special Message for 2nd, CL, CST about CL 202 Tutorial on Wednesday
    		BuildSpecialMsgForChemicalCST2ndYear();
    	
    	else
    		BuildUpdateMsg();
	}

	protected void set_first_time_update_done() {

		SharedPreferences first_time_update_sp = getApplicationContext().getSharedPreferences("FirstTimeUpdate",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_first_update = first_time_update_sp.edit();
		edit_first_update.putInt("First", 1);
		edit_first_update.commit();
	}

	protected void BuildSpecialMsgForChemical4thYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_chem_4th_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForCST4thYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_cst_4th_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForDesign4thYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_dd_4th_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForChemical3rdYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_chem_3rd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForDesign3rdYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_dd_3rd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForMech3rdYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_mech_3rd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForMech2ndYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_mech_2nd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForChemicalCST2ndYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.spcl_msg_chem_cst_2nd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildSpecialMsgForDesign2ndYear() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Sorry")
    		   .setMessage(getResources().getString(R.string.spcl_msg_dd_2nd_yr))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_without_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	protected void BuildUpdateMsg() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Update Successful")
    		   .setMessage(getResources().getString(R.string.auto_tt_update_success_msg))
    	       .setCancelable(false)
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   go_to_home_with_toast();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	

	protected void delete_course() {
		// Delete anything from past
		
		// Delete all courses files
		
		SharedPreferences delete_course_files = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		int course_counter = delete_course_files.getInt("counter",0);
		
		for (int i = 0; i < course_counter ; i++){
			
			String course_name = delete_course_files.getString("c" + Integer.toString(i+1), "N/A");
			SharedPreferences delete_course = getSharedPreferences(course_name,Context.MODE_PRIVATE);
			delete_course.edit().clear().commit();
		}
		
		delete_course_files.edit().clear().commit();
		
		// Delete all day wise time tables
		SharedPreferences del_mon = getSharedPreferences("Monday",Context.MODE_PRIVATE);
		del_mon.edit().clear().commit();
		SharedPreferences del_tues = getSharedPreferences("Tuesday",Context.MODE_PRIVATE);
		del_tues.edit().clear().commit();
		SharedPreferences del_wed = getSharedPreferences("Wednesday",Context.MODE_PRIVATE);
		del_wed.edit().clear().commit();
		SharedPreferences del_thur = getSharedPreferences("Thursday",Context.MODE_PRIVATE);
		del_thur.edit().clear().commit();
		SharedPreferences del_fri = getSharedPreferences("Friday",Context.MODE_PRIVATE);
		del_fri.edit().clear().commit();
		
		SharedPreferences del_updated_days = getSharedPreferences("DaysUpdated",Context.MODE_PRIVATE);
		del_updated_days.edit().clear().commit();
		
		// Delete all bm files
		SharedPreferences del_bm_mon = getSharedPreferences("bm_Monday",Context.MODE_PRIVATE);
		del_bm_mon.edit().clear().commit();
		SharedPreferences del_bm_tues = getSharedPreferences("bm_Tuesday",Context.MODE_PRIVATE);
		del_bm_tues.edit().clear().commit();
		SharedPreferences del_bm_wed = getSharedPreferences("bm_Wednesday",Context.MODE_PRIVATE);
		del_bm_wed.edit().clear().commit();
		SharedPreferences del_bm_thur = getSharedPreferences("bm_Thursday",Context.MODE_PRIVATE);
		del_bm_thur.edit().clear().commit();
		SharedPreferences del_bm_fri = getSharedPreferences("bm_Friday",Context.MODE_PRIVATE);
		del_bm_fri.edit().clear().commit();
		
		// Delete PT, NSO files
		SharedPreferences del_pt = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
		del_pt.edit().clear().commit();
		SharedPreferences del_nso = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		del_nso.edit().clear().commit();
		
	}
	
	protected void set_new_time_table() {

		if(year == 1 && (branch == 2 || branch == 10 || branch == 1 || branch == 7 || branch == 8)){
			
			if(branch == 2 || branch == 10)
				// 1st Year, Division 1 - ME, CE
				add_1st_year_div1_or_2_tt(1);
			else if(branch == 1 || branch == 7 || branch == 8)
				// 1st Year, Division 2 - BT, ECE, EEE
				add_1st_year_div1_or_2_tt(2);
	    }else
	    	
    	if(year == 1 && (branch == 4 || branch == 9 || branch == 11 || branch == 3 || branch == 5 || branch == 6)){
			
			if(branch == 4 || branch == 9 || branch == 11)
				// 1st Year, Division 3 - CSE, EP, MnC
				add_1st_year_div3_or_4_tt(3);
			else if(branch == 3 || branch == 5 || branch == 6)
				// 1st Year, Division 2 - CL, CST, DD
				add_1st_year_div3_or_4_tt(4);
	    }
		
		if(year == 2){
			
			String venue_ma201 = null;
			
			if (branch == 4 || branch == 11 || branch == 7 || branch == 8 || branch == 9)
				// Division 1 and 2
				// CSE, MnC, ECE, EEE, EP
				venue_ma201 = "L3";
			else if(branch == 1 || branch == 3 || branch == 5)
				// CL, CST, BT
				venue_ma201 = "L3";
			else
				// ME, CE
				venue_ma201 = "L4";
			
			switch(branch){
			
				case 1: // 2nd Year, BT
					add_2nd_year_bt(venue_ma201);
					break;
					
				case 2: // 2nd Year, CE
					add_2nd_year_ce(venue_ma201);
					break;
					
				case 3: // 2nd Year, CL
					add_2nd_year_cl(venue_ma201);
					break;
					
				case 4: // 2nd Year, CSE
					add_2nd_year_cse(venue_ma201);
					break;
					
				case 5: // 2nd Year, CST
					add_2nd_year_cst(venue_ma201);
					break;
				
				case 7: // 2nd Year, ECE
					add_2nd_year_ece_eee(venue_ma201);
					break;
					
				case 8: // 2nd Year, ECE
					add_2nd_year_ece_eee(venue_ma201);
					break;
					
				case 9: // 2nd Year, EP
					add_2nd_year_ep(venue_ma201);
					break;
					
				case 10: // 2nd Year, ME
					add_2nd_year_me(venue_ma201);
					break;
					
				case 11: // 2nd Year, ME
					add_2nd_year_mndc(venue_ma201);
					break;
			}
			
		}
		
		if(year == 3){
			
			switch(branch){
			
				case 1: // 3rd Year, BT
					add_3rd_year_bt();
					break;
					
				case 2: // 3rd Year, CE
					add_3rd_year_ce();
					break;
					
				case 3: // 3rd Year, CL
					add_3rd_year_cl();
					break;
					
				case 4: // 3rd Year, CSE
					add_3rd_year_cse();
					break;
					
				case 5: // 3rd Year, CST
					add_3rd_year_cst();
					break;
					
				case 6: // 3rd Year, DD
					add_3rd_year_dd();
					break;
			
				case 7: // 3rd Year, ECE
					add_3rd_year_ece();
					break;
				
				case 8: // 3rd Year, EEE
					add_3rd_year_eee();
					break;
					
				case 9: // 3rd Year, EP
					add_3rd_year_ep();
					break;
					
				case 10: // 3rd Year, ME
					add_3rd_year_me();
					break;
					
				case 11: // 3rd Year, MndC
					add_3rd_year_mndc();
					break;
					
			}
			
		}
		
		if(year == 4){
			
			switch(branch){
			
				case 1: // 4th Year, BT
					add_4th_year_bt();
					break;
					
				case 2: // 4th Year, CE
					add_4th_year_ce();
					break;
					
				case 3: // 4th Year, CL
					add_4th_year_cl();
					break;
					
				case 4: // 4th Year, CSE
					add_4th_year_cse();
					break;
					
				case 5: // 4th Year, CST
					add_4th_year_cst();
					break;
					
				case 6: // 4th Year, DD
					add_4th_year_dd();
					break;
					
				case 7: // 4th Year, ECE
					add_4th_year_ece();
					break;
				
				case 8: // 4th Year, EEE
					add_4th_year_eee();
					break;
					
				case 9: // 4th Year, EP
					add_4th_year_ep();
					break;
					
				case 10: // 4th Year, ME
					add_4th_year_me();
					break;
					
				case 11: // 4th Year, MndC
					add_4th_year_mndc();
					break;
			}
		}
		
	}

	private void decide_courses() {

		if(year == 1){
	    	// 1st year course list is same for all, irrespective of branch.
	    	String[] comp_courses_year1 = {"Chemistry 1\n", "Physics 1\n", "Electrical Sciences\n", "Engineering Drawing\n", "Mathematics 1\n"};
	    	display_courses(comp_courses_year1);
	    }else
	    	
    	if(year == 2){
	    	// 2st year course list
    		
    		switch(branch){
    			// Decide course list as per the branch
    		
    			case 1: // 2nd Year, BT
    				String[] comp_courses_bt2 = {"Mathematics 3\n", "Biochemistry\n", "Chemical Process Calculations\n", 
    											"Fluid Mechanics\n", "Chemical Engineering Thermodynamics 1\n"};
    				display_courses(comp_courses_bt2);
    				break;
    				
    			case 2: // 2nd Year, CE
    				String[] comp_courses_ce2 = {"Mathematics 3\n", "Solid Mechanics\n", "Engineering Geology\n", 
    											"Civil Engineering Materials\n"};
    				display_courses(comp_courses_ce2);
    				break;
    				
    			case 3: // 2nd Year, CL
    				String[] comp_courses_cl2 = {"Mathematics 3\n", "Chemical Process Calculations\n", "Fluid Mechanics\n", 
    											"Chemical Engineering Thermodynamics 1\n", "Solid Mechanics 1\n"};
    				display_courses(comp_courses_cl2);
    				break;
    				
    			case 4: // 2nd Year, CSE
    				String[] comp_courses_cse2 = {"Mathematics 3\n", "Data Structures\n", "Discrete Mathematics\n", 
    											"Digital Design\n"};
    				display_courses(comp_courses_cse2);
    				break;
    				
    			case 5: // 2nd Year, CST
    				String[] comp_courses_cst2 = {"Mathematics 3\n", "Chemical Process Calculations\n", "Fluid Mechanics\n", 
    											"Organic Chemistry\n","Introduction to Quantum Chemistry\n"};
    				display_courses(comp_courses_cst2);
    				break;
    				
    			case 6: // 2nd Year, DD
    				BuildSpecialMsgForDesign2ndYear();
    				//String[] comp_courses_dd2 = {"Elements of Design\n"};
    				//display_courses(comp_courses_dd2);
    				break;
    				
    			case 7: // 2nd Year, ECE
    				String[] comp_courses_ece2 = {"Semiconductor Devices\n", "Digital Circuits and Microprocessors\n", "Signals and Systems\n", 
													"Mathematics III\n"};
    				display_courses(comp_courses_ece2);
    				break;
    				
    			case 8: // 2nd Year, ECE
    				String[] comp_courses_eee2 = {"Semiconductor Devices\n", "Digital Circuits and Microprocessors\n", "Signals and Systems\n", 
													"Mathematics III\n"};
    				display_courses(comp_courses_eee2);
    				break;
    				
    			case 9: // 2nd Year, EP
    				String[] comp_courses_ep2 = {"Mathematics 3\n", "Signals, Systems and Networks\n", "Advanced Classical Mechanics\n", 
													"Semiconductor Devices\n", "Heat & Thermodynamics\n"};
    				display_courses(comp_courses_ep2);
    				break;
    				
    			case 10: // 2nd Year, ME
    				String[] comp_courses_me2 = {"Mathematics 3\n", "Thermodynamics\n", "Solid Mechanics 1\n", 
													"Engineering Materials\n"};
    				display_courses(comp_courses_me2);
    				break;
    				
    			case 11: // 2nd Year, MnC
    				String[] comp_courses_mndc2 = {"Mathematics 3\n", "Discrete Mathematics\n", "Modern Algebra\n", 
													"Probability Theory and Random Processes\n", "Digital Design\n"};
    				display_courses(comp_courses_mndc2);
    				break;
    			
    		}
	    }
		
		if(year == 3){
			
			switch(branch){
			
				case 1: //3rd Year, BT
					String[] comp_courses_bt3 = {"Bioinformatics and Computational Biology\n", "Immunology\n", "Plant Biotechnology\n", 
												"Chemical Reaction Engineering 1\n"};
					display_courses(comp_courses_bt3);
					break;
					
				case 2: //3rd Year, CE
					String[] comp_courses_ce3 = {"Hydraulics and Hydraulic Structures\n", "Structural Analysis 2\n", "Geotechnical Engineering 2\n", 
												"Reinforced Concrete Design\n"};
					display_courses(comp_courses_ce3);
					break;
					
				case 3: //3rd Year, CL
					String[] comp_courses_cl3 = {"Solid and Fluid-Solid Operations\n", "Chemical Reaction Engineering 1\n", "Process Equipment Design 2\n", 
												"Mass Transfer Operation 2\n"};
					display_courses(comp_courses_cl3);
					break;
					
				case 4: //3rd Year, CSE
					String[] comp_courses_cse3 = {"Probability Theory and Random Processes\n", "Theory of Computation\n", "Operating Systems\n", 
												"Data Communication\n"};
					display_courses(comp_courses_cse3);
					break;
					
				case 5: //3rd Year, CST
					String[] comp_courses_cst3 = {"Environmental Chemistry\n", "Industrial Chemistry\n", "Chemical Kinetics and Electrochemistry\n", 
												"Computational Physics\n"};
					display_courses(comp_courses_cst3);
					break;
					
				case 6: // 3rd Year, DD
    				String[] comp_courses_dd3 = {"Introduction to Interaction Design\n", "Physical Computing\n"};
    				display_courses(comp_courses_dd3);
    				break;
			
				case 7: // 3rd Year, ECE
					String[] comp_courses_ece3 = {"Introduction to VLSI Design\n", "Digital Signal Processing\n", "Digital Communication\n", 
													"Control Systems\n"};
					display_courses(comp_courses_ece3);
					break;
					
				case 8: // 3rd Year, EEE
					String[] comp_courses_eee3 = {"Electrical Machines\n", "Digital Signal Processing\n", "Electrical Power Systems\n", 
													"Control Systems\n"};
					display_courses(comp_courses_eee3);
					break;
					
				case 9: // 3rd Year, EP
					String[] comp_courses_ep3 = {"Microprocessor architecture and Programming\n", "Atomic and Molecular Spectroscopy\n", "Computational Physics\n", 
													"Statistical Mechanics\n"};
					display_courses(comp_courses_ep3);
					break;
					
				case 10: // 3rd Year, ME
					String[] comp_courses_me3 = {"Fluid Mechanics 2\n", "Manufacturing Technology 2\n", "Dynamics of Machinery\n", 
													"Design of Machine Elements\n", "Electrical Machines\n"};
					display_courses(comp_courses_me3);
					break;
					
				case 11: // 3rd Year, MndC
					String[] comp_courses_mndc3 = {"Scientific Computing\n", "Stochastic Calculus for Finance\n", "Operating Systems\n", 
													"Data Communication\n"};
					display_courses(comp_courses_mndc3);
					break;
			}
			
		}
		
		if(year == 4){
			
			switch(branch){
			
				case 1: // 4th Year, BT
					String[] comp_courses_bt4 = {"Bioseparation Engineering\n","Environmental Biotechnology\n"};
					display_courses(comp_courses_bt4);
					break;
					
				case 2: // 4th Year, CE
					String[] comp_courses_ce4 = {"Environmental Engineering 2\n","Transportaion Engineering 2\n"};
					display_courses(comp_courses_ce4);
					break;
					
				case 3: // 4th Year, CL
					String[] comp_courses_cl4 = {"Chemical Process Technology\n","Process Equipment Design 3\n"};
					display_courses(comp_courses_cl4);
					break;
					
				case 4: // 4th Year, CSE
					String[] comp_courses_cse4 = {"Computer Graphics\n"};
					display_courses(comp_courses_cse4);
					break;
					
				case 5: // 4th Year, CST
					String[] comp_courses_cst4 = {"Modern Chemical Technology\n", "Technical writing and Seminar\n"};
					display_courses(comp_courses_cst4);
					break;
					
				case 6: // 4th Year, DD
    				String[] comp_courses_dd4 = {"New Media Studies\n", "Design Project 3\n"};
    				display_courses(comp_courses_dd4);
    				break;
			
				case 7: // 4th Year, ECE
					String[] comp_courses_ece4 = {"Microwave Engineering\n"};
					display_courses(comp_courses_ece4);
					break;
					
				case 8: // 4th Year, EEE
					String[] comp_courses_eee4 = {"Electrical Power System Operation and Control\n"};
					display_courses(comp_courses_eee4);
					break;
					
				case 9: // 4th Year, EP
					String[] comp_courses_ep4 = {"Materials Science & Engineering\n","Lasers & Photonics\n"};
					display_courses(comp_courses_ep4);
					break;
					
				case 10: // 4th Year, ME
					String[] comp_courses_me4 = {"Applied Thermodynamics 2\n"};
					display_courses(comp_courses_me4);
					break;
					
				case 11: // 4th Year, MndC
					String[] comp_courses_mndc4 = {"Matrix Computations\n","Statistical Analysis of Financial Data\n","Theory of Computation\n"};
					display_courses(comp_courses_mndc4);
					break;
			}
			
		}
		
	}

	private void display_courses(String[] comp_courses_array) {
		
		ArrayList<String> CourseList = new ArrayList<String>();
    	CourseList.addAll( Arrays.asList(comp_courses_array) );
    	
    	listAdapter = new CustomListAdapter(ShowInBuildTemplates.this , R.layout.tv_for_custom_list_view , CourseList);
    	mainListView.setAdapter(listAdapter);
    	
    	//listAdapter = new ArrayAdapter<String>(this, R.layout.list_row_page, CourseList);
    	//mainListView.setAdapter( listAdapter );
	}

	protected void add_1st_year_div1_or_2_tt(int div) {
			
			String venue = null;
			
			if(div == 1)
				venue = "L1";
			else if (div == 2)
				venue = "L2";
			
			String[] course1_data = {"Chemistry 1","CH 101", venue};
	    	int[] course1_day_check = {0,1,1,1,0};	// 1 - for days, when the class is there.
	    	int[] course1_slot_check = {0,2,3,4,0};	// Slot of each day, 0 - default value - if no class on that day
	    											// for eg, here 5th value of array = 0, => no class on Friday
	    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
	    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
	    	
	    	String[] course2_data = {"Physics 1","PH 101", venue};
	    	int[] course2_day_check = {1,1,1,0,0};
	    	int[] course2_slot_check = {2,3,4,0,0};
	    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
	    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
	    	
	    	String[] course3_data = {"Electrical Sciences","EE 101", venue};
	    	int[] course3_day_check = {1,1,0,0,1};
	    	int[] course3_slot_check = {3,4,0,0,2};
	    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
	    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
	    	
	    	String[] course4_data = {"Engineering Drawing","ME 101", venue};
	    	int[] course4_day_check = {1,0,0,1,1};
	    	int[] course4_slot_check = {4,0,0,2,3};
	    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
	    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
	    	
	    	String[] course5_data = {"Mathematics 1","MA 101", venue};
	    	int[] course5_day_check = {0,0,1,1,1};
	    	int[] course5_slot_check = {0,0,2,3,4};
	    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
	    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
	    	
	}
	
	protected void add_1st_year_div3_or_4_tt(int div) {
		
		String venue = null;
		
		if(div == 3)
			venue = "L1";
		else if (div == 4)
			venue = "L2";
		
		String[] course1_data = {"Chemistry 1","CH 101", venue};
    	int[] course1_day_check = {0,1,1,1,0};	// 1 - for days, when the class is there.
    	int[] course1_slot_check = {0,7,8,9,0};	// Slot of each day, 0 - default value - if no class on that day
    											// for eg, here 5th value of array = 0, => no class on Friday
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Physics 1","PH 101", venue};
    	int[] course2_day_check = {1,1,1,0,0};
    	int[] course2_slot_check = {7,8,9,0,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Electrical Sciences","EE 101", venue};
    	int[] course3_day_check = {1,1,0,0,1};
    	int[] course3_slot_check = {8,9,0,0,7};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Engineering Drawing","ME 101", venue};
    	int[] course4_day_check = {1,0,0,1,1};
    	int[] course4_slot_check = {9,0,0,7,8};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Mathematics 1","MA 101", venue};
    	int[] course5_day_check = {0,0,1,1,1};
    	int[] course5_slot_check = {0,0,7,8,9};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
    	
	}
	
	private void add_2nd_year_bt(String venue_ma201) {
		
		// {"Mathematics 3\n", "Biochemistry\n", "Chemical Process Calculations\n", 
		//	"Fluid Mechanics\n", "Chemical Engineering Thermodynamics 1\n"};
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Biochemistry","BT 201", "4207"};
    	int[] course2_day_check = {0,0,1,1,1};
    	int[] course2_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);

    	String[] course3_data = {"Chemical Process Calculations","CL 201", "L4"};
    	int[] course3_day_check = {0,1,0,1,0};
    	int[] course3_slot_check = {0,9,0,9,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Fluid Mechanics","CL 202", "4G4"};
    	int[] course4_day_check = {1,1,1,0,0};
    	int[] course4_slot_check = {9,8,8,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Chemical Engineering Thermodynamics 1","CL 203", "4G4"};
    	int[] course5_day_check = {1,1,1,0,0};
    	int[] course5_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
		
	}
	
	private void add_2nd_year_ce(String venue_ma201) {
		
		// "Mathematics 3\n", "Solid Mechanics\n", "Engineering Geology\n", 
		// "Civil Engineering Materials\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);

    	String[] course2_data = {"Solid Mechanics","CE 202", "4201"};
    	int[] course2_day_check = {1,0,1,1,0};
    	int[] course2_slot_check = {1,0,3,5,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Engineering Geology","CE 207", "3102"};
    	int[] course3_day_check = {0,1,0,1,1};
    	int[] course3_slot_check = {0,8,0,8,8};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Civil Engineering Materials","CE 211", "4201"};
    	int[] course4_day_check = {0,1,0,1,1};
    	int[] course4_slot_check = {0,5,0,4,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);

	}
	
	private void add_2nd_year_cl(String venue_ma201) {
		
		// "Mathematics 3\n", "Chemical Process Calculations\n", "Fluid Mechanics\n", 
		// "Chemical Engineering Thermodynamics 1\n", "Solid Mechanics 1\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);

    	String[] course2_data = {"Chemical Process Calculations","CL 201", "L4"};
    	int[] course2_day_check = {0,1,0,1,0};
    	int[] course2_slot_check = {0,5,0,5,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Fluid Mechanics","CL 202", "L4"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {9,8,8,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Chemical Engineering Thermodynamics 1","CL 203", "4G4"};
    	int[] course4_day_check = {0,1,1,1,0};
    	int[] course4_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Solid Mechanics 1","ME 212", "1G1"};
    	int[] course5_day_check = {1,1,1,0,0};
    	int[] course5_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
    	
	}
	
	private void add_2nd_year_cst(String venue_ma201) {
		
		// "Mathematics 3\n", "Chemical Process Calculations\n", "Fluid Mechanics\n", 
		// "Organic Chemistry\n","Introduction to Quantum Chemistry\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Chemical Process Calculations","CL 201", "L4"};
    	int[] course2_day_check = {0,1,0,1,0};
    	int[] course2_slot_check = {0,9,0,9,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Fluid Mechanics","CL 202", "4G4"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {9,8,8,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Organic Chemistry","CH 221", "4006"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Introduction to Quantum Chemistry","CH 231", "4104"};
    	int[] course5_day_check = {1,0,1,0,1};
    	int[] course5_slot_check = {10,0,10,0,10};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
    	
	}
	
	private void add_2nd_year_cse(String venue_ma201) {
		
		// "Mathematics 3\n", "Data Structures\n", "Discrete Mathematics\n", 
		// "Digital Design\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Data Structures","CS 201", "1201"};
    	int[] course2_day_check = {1,1,1,0,0};
    	int[] course2_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Discrete Mathematics","CS 202", "1201"};
    	int[] course3_day_check = {0,0,1,1,1};
    	int[] course3_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Digital Design","CS 221", "1207"};
    	int[] course4_day_check = {1,0,0,1,1};
    	int[] course4_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_2nd_year_ece_eee(String venue_ma201) {
		
		// {"Semiconductor Devices\n", "Digital Circuits and Microprocessors\n", "Signals and Systems\n", 
		//"Mathematics III\n"};
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Semiconductor Devices","EE 200", "2201"};
    	int[] course2_day_check = {1,0,0,1,1};
    	int[] course2_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Digital Circuits and Microprocessors","EE 201", "2201"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Signals and Systems","EE 220", "L3"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_2nd_year_ep(String venue_ma201) {
		
		// "Mathematics 3\n", "Signals, Systems and Networks", "Advanced Classical Mechanics\n", 
		// "Semiconductor Devices\n", "Heat & Thermodynamics\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Signals, Systems and Networks","EE 220", "L3"};
    	int[] course2_day_check = {0,0,1,1,1};
    	int[] course2_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Advanced Classical Mechanics","PH 201", "4004"};
    	int[] course3_day_check = {1,0,0,1,1};
    	int[] course3_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Semiconductor Devices","PH 203", "4004"};
    	int[] course4_day_check = {1,1,1,0,0};
    	int[] course4_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Heat & Thermodynamics","PH 205", "4004"};
    	int[] course5_day_check = {1,0,1,0,1};
    	int[] course5_slot_check = {10,0,10,0,10};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
		
	}
	
	private void add_2nd_year_me(String venue_ma201) {
		
		// "Mathematics 3\n", "Thermodynamics\n", "Solid Mechanics 1\n", 
		// "Engineering Materials\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Thermodynamics","ME 211", "1005/3"};
    	int[] course2_day_check = {0,1,1,1,0};
    	int[] course2_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Solid Mechanics 1","ME 212", "1005/3"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Engineering Materials","ME 213", "1005/3"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_2nd_year_mndc(String venue_ma201) {
		
		// "Mathematics 3\n", "Discrete Mathematics\n", "Modern Algebra\n", 
		// "Probability Theory and Random Processes\n", "Digital Design\n"
		
		String[] course1_data = {"Mathematics 3","MA 201", venue_ma201};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Discrete Mathematics","MA 221", "2203"};
    	int[] course2_day_check = {0,0,1,1,1};
    	int[] course2_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Modern Algebra","MA 222", "1104"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {9,8,7,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Probability Theory and Random Processes","MA 225", "1207"};
    	int[] course4_day_check = {1,1,1,0,0};
    	int[] course4_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Digital Design","CS 221", "1207"};
    	int[] course5_day_check = {1,0,0,1,1};
    	int[] course5_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
		
	}
	
	private void add_3rd_year_bt() {
		
		// "Bioinformatics and Computational Biology\n", "Immunology\n", "Plant Biotechnology\n", 
		// "Chemical Reaction Engineering 1\n"
		
		String[] course1_data = {"Bioinformatics and Computational Biology","BT 301", "4102"};
    	int[] course1_day_check = {0,1,1,0,0};
    	int[] course1_slot_check = {0,2,3,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Immunology","BT 303", "4102"};
    	int[] course2_day_check = {0,0,1,1,1};
    	int[] course2_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Plant Biotechnology","BT 309", "4102"};
    	int[] course3_day_check = {1,1,0,0,1};
    	int[] course3_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	
    	String[] course4_data = {"Chemical Reaction Engineering 1","CL 303", "4102"};
    	int[] course4_day_check = {1,1,1,0,0};
    	int[] course4_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_ce() {
		
		// "Hydraulics and Hydraulic Structures\n", "Structural Analysis 2\n", "Geotechnical Engineering 2\n", 
		// "Reinforced Concrete Design\n"
		
		String[] course1_data = {"Hydraulics and Hydraulic Structures","CE 301", "3102"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,4,4,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Structural Analysis 2","CE 302", "3102"};
    	int[] course2_day_check = {1,0,1,0,1};
    	int[] course2_slot_check = {3,0,3,0,4};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Geotechnical Engineering 2","CE 303", "3102"};
    	int[] course3_day_check = {0,1,0,1,1};
    	int[] course3_slot_check = {0,3,0,3,2};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	
    	String[] course4_data = {"Reinforced Concrete Design","CE 305", "3102"};
    	int[] course4_day_check = {1,1,1,0,0};
    	int[] course4_slot_check = {2,2,2,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_cl() {
		
		// "Solid and Fluid-Solid Operations\n", "Chemical Reaction Engineering 1\n", "Process Equipment Design 2\n", 
		// "Mass Transfer Operation 2\n"
		
		String[] course1_data = {"Solid and Fluid-Solid Operations","CL 301", "4203"};
    	int[] course1_day_check = {0,1,1,1,0};	// Updated
    	int[] course1_slot_check = {0,2,3,4,0}; // Updated
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Chemical Reaction Engineering 1","CL 303", "4203"};
    	int[] course2_day_check = {1,1,1,0,0};
    	int[] course2_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Process Equipment Design 2","CL 304", "4203"}; // On tuesday, clashes with CL 303
    	int[] course3_day_check = {1,0,0,0,0};
    	int[] course3_slot_check = {3,0,0,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Mass Transfer Operation 2","CL 306", "4203"};
    	int[] course4_day_check = {0,0,1,1,1}; // Updated
    	int[] course4_slot_check = {0,0,2,3,4}; // Updated
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_cse() {
		
		// "Probability Theory and Random Processes\n", "Theory of Computation\n", "Operating Systems\n", 
		// "Data Communication\n"
		
		String[] course1_data = {"Probability Theory and Random Processes","MA 225", "1207"};
    	int[] course1_day_check = {1,1,1,0,0};
    	int[] course1_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Theory of Computation","CS 301", "2001"};
    	int[] course2_day_check = {1,1,0,0,1};
    	int[] course2_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Operating Systems","CS 341", "3202"};
    	int[] course3_day_check = {0,1,1,1,0};
    	int[] course3_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Data Communication","CS 343", "3202"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_cst() {
		
		// "Environmental Chemistry\n", "Industrial Chemistry\n", "Chemical Kinetics and Electrochemistry\n", 
		// "Computational Physics\n"
		
		String[] course1_data = {"Environmental Chemistry","CH 301", "4103"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Industrial Chemistry","CH 311", "4103"};
    	int[] course2_day_check = {1,1,0,0,1};
    	int[] course2_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Chemical Kinetics and Electrochemistry","CH 331", "4103"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Computational Physics","PH 305", "4206"};
    	int[] course4_day_check = {1,0,1,0,0};
    	int[] course4_slot_check = {10,0,10,0,0};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_dd() {
		
		// "Introduction to Interaction Design\n", "Physical Computing\n"
		
		String[] course1_data = {"Introduction to Interaction Design","DD 301", "3yr Lab"};
    	int[] course1_day_check = {0,0,1,0,0};
    	int[] course1_slot_check = {0,0,2,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Physical Computing","DD 305", "3yr Lab"};
    	int[] course2_day_check = {0,0,0,1,0};
    	int[] course2_slot_check = {0,0,0,3,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
	}
	
	private void add_3rd_year_ece() {
		
		// "Introduction to VLSI Design\n", "Digital Signal Processing\n", "Digital Communication\n", 
		// "Control Systems\n"
		
		String[] course1_data = {"Introduction to VLSI Design","EE 310", "2202"};
    	int[] course1_day_check = {1,1,0,0,1};
    	int[] course1_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Digital Signal Processing","EE 320", "2202"};
    	int[] course2_day_check = {0,1,1,1,0};
    	int[] course2_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Digital Communication","EE 330", "2202"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Control Systems","EE 350", "2202"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_eee() {
		
		// "Electrical Machines\n", "Digital Signal Processing\n", "Electrical Power Systems\n", 
		// "Control Systems\n"
		
		String[] course1_data = {"Electrical Machines","EE 380", "2201"};
    	int[] course1_day_check = {1,1,0,0,1};
    	int[] course1_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Digital Signal Processing","EE 320", "2202"};
    	int[] course2_day_check = {0,1,1,1,0};
    	int[] course2_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Electrical Power Systems","EE 382", "2002"};
    	int[] course3_day_check = {1,1,1,0,0};
    	int[] course3_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Control Systems","EE 350", "2202"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_ep() {
		
		// "Microprocessor architecture and Programming\n", "Atomic and Molecular Spectroscopy\n", "Computational Physics\n", 
		// "Statistical Mechanics\n"
		
		String[] course1_data = {"Microprocessor architecture and Programming","PH 301", "4206"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,4,4,1,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Atomic and Molecular Spectroscopy","PH 303", "4206"};
    	int[] course2_day_check = {1,1,1,0,0};
    	int[] course2_slot_check = {1,2,3,0,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Computational Physics","PH 305", "4206"};
    	int[] course3_day_check = {1,0,1,0,0};
    	int[] course3_slot_check = {10,0,10,0,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Statistical Mechanics","PH 307", "4206"};
    	int[] course4_day_check = {0,0,1,0,1};
    	int[] course4_slot_check = {0,0,2,0,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_3rd_year_me() {
		
		
		// "Fluid Mechanics 2\n", "Manufacturing Technology 2\n", "Dynamics of Machinery\n", 
		// "Design of Machine Elements\n", "Electrical Machines\n"
		
		String[] course1_data = {"Fluid Mechanics 2","ME 311", "1005/3"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,7,8,7,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Manufacturing Technology 2","ME 312", "1005/3"};
    	int[] course2_day_check = {0,1,0,1,1};
    	int[] course2_slot_check = {0,7,0,10,9};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Dynamics of Machinery","ME 313", "1005/3"};
    	int[] course3_day_check = {0,1,0,1,1};
    	int[] course3_slot_check = {0,10,0,9,10};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Design of Machine Elements","ME 314", "1005/3"};
    	int[] course4_day_check = {0,1,1,0,1};
    	int[] course4_slot_check = {0,8,9,0,8};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
    	
    	String[] course5_data = {"Electrical Machines","EE 380", "2201"};
    	int[] course5_day_check = {1,1,0,0,1};
    	int[] course5_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course5_data,course5_day_check,course5_slot_check);
    	update_course_wise_tt(course5_data,course5_day_check,course5_slot_check);
	}
	
	private void add_3rd_year_mndc() {
		
		// "Scientific Computing\n", "Stochastic Calculus for Finance\n", "Operating Systems\n", 
		// "Data Communication\n"
		
		String[] course1_data = {"Scientific Computing","MA 322", "2203"};
    	int[] course1_day_check = {1,1,1,0,0};
    	int[] course1_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Stochastic Calculus for Finance","MA 372", "2203"};
    	int[] course2_day_check = {1,1,0,0,1};
    	int[] course2_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Operating Systems","CS 341", "3202"};
    	int[] course3_day_check = {0,1,1,1,0};
    	int[] course3_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
    	
    	String[] course4_data = {"Data Communication","CS 343", "3202"};
    	int[] course4_day_check = {0,0,1,1,1};
    	int[] course4_slot_check = {0,0,2,3,4};
    	update_day_wise_tt_of_course(course4_data,course4_day_check,course4_slot_check);
    	update_course_wise_tt(course4_data,course4_day_check,course4_slot_check);
		
	}
	
	private void add_4th_year_bt() {
		
		// "Bioseparation Engineering\n","Environmental Biotechnology\n"
		
		String[] course1_data = {"Bioseparation Engineering","BT 404", "4102"};
    	int[] course1_day_check = {1,1,1,0,0};
    	int[] course1_slot_check = {2,3,4,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Environmental Biotechnology","BT 405", "4207"};
    	int[] course2_day_check = {1,1,0,0,1};
    	int[] course2_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
		
	}
	
	private void add_4th_year_ce() {
		
		// "Environmental Engineering 2\n","Transportaion Engineering 2\n"
		
		String[] course1_data = {"Environmental Engineering 2","CE 401", "4208"};
    	int[] course1_day_check = {1,1,1,0,0};
    	int[] course1_slot_check = {3,3,3,0,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Transportaion Engineering 2","CE 403", "4201"};
    	int[] course2_day_check = {0,1,0,1,1};
    	int[] course2_slot_check = {0,5,0,5,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
		
	}
	
	private void add_4th_year_cl() {
		
		// "Chemical Process Technology\n","Process Equipment Design 3\n"
		
		String[] course1_data = {"Chemical Process Technology","CL 402", "4211"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	// CL 403 on Wednesday is of 3 slots?
    	
    	String[] course2_data = {"Process Equipment Design 3","CL 403", "4209"};
    	int[] course2_day_check = {1,0,0,1,0};
    	int[] course2_slot_check = {2,0,0,7,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	// ^^ Special Msg - CL 403 wednesday class is of 3 hours.
		
	}
	
	private void add_4th_year_cse() {
		
		// "Computer Graphics\n"
		
		String[] course1_data = {"Computer Graphics","CS 461", "1201"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
		
	}
	
	private void add_4th_year_cst() {
		
		// Modern Chemical Technology, Technical writing and Seminar
		
		String[] course1_data = {"Modern Chemical Technology","CH 401", "4101"};
    	int[] course1_day_check = {1,1,0,0,1};
    	int[] course1_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Technical writing and Seminar","CH 402", "4101"};
    	int[] course2_day_check = {0,0,0,0,1};
    	int[] course2_slot_check = {0,0,0,0,7};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
		
	}
	
	private void add_4th_year_dd() {
		
		// New Media Studies, Design Project 3
		
		String[] course1_data = {"New Media Studies","DD 401", "4yr Lab"};
    	int[] course1_day_check = {0,0,0,0,1};
    	int[] course1_slot_check = {0,0,0,0,2};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Design Project 3","DD 498", "Update"};
    	int[] course2_day_check = {0,0,1,0,0};
    	int[] course2_slot_check = {0,0,2,0,0};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
		
	}
	
	private void add_4th_year_ece() {
		
		String[] course1_data = {"Microwave Engineering","EE 441", "3101"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,9,8,8,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
		
	}
	
	private void add_4th_year_eee() {
		
		String[] course1_data = {"Electrical Power System Operation and Control","EE 480", "3101"};
    	int[] course1_day_check = {1,0,0,1,1};
    	int[] course1_slot_check = {3,0,0,3,4};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
		
	}

	private void add_4th_year_ep() {
		
		// "Materials Science & Engineering\n","Lasers & Photonics\n"
		
		String[] course1_data = {"Materials Science & Engineering","PH 413", "4003"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Lasers & Photonics","PH 415", "4003"};
    	int[] course2_day_check = {1,0,0,1,1};
    	int[] course2_slot_check = {3,0,0,1,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
		
	}
	
	private void add_4th_year_me() {
		
		// "Applied Thermodynamics 2\n"
		
		String[] course1_data = {"Applied Thermodynamics 2","ME 441", "1203"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,3,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
	}
	
	private void add_4th_year_mndc() {
		
		
		// "Matrix Computations\n","Statistical Analysis of Financial Data\n","Theory of Computation\n"
		
		String[] course1_data = {"Matrix Computations","MA 423", "1104"};
    	int[] course1_day_check = {0,1,1,1,0};
    	int[] course1_slot_check = {0,2,3,4,0};
    	update_day_wise_tt_of_course(course1_data,course1_day_check,course1_slot_check);
    	update_course_wise_tt(course1_data,course1_day_check,course1_slot_check);
    	
    	String[] course2_data = {"Statistical Analysis of Financial Data","MA 471", "1104"};
    	int[] course2_day_check = {1,1,0,0,1};
    	int[] course2_slot_check = {3,4,0,0,2};
    	update_day_wise_tt_of_course(course2_data,course2_day_check,course2_slot_check);
    	update_course_wise_tt(course2_data,course2_day_check,course2_slot_check);
    	
    	String[] course3_data = {"Theory of Computation","MA 453", "1104"};
    	int[] course3_day_check = {1,0,0,1,1};
    	int[] course3_slot_check = {4,0,0,2,3};
    	update_day_wise_tt_of_course(course3_data,course3_day_check,course3_slot_check);
    	update_course_wise_tt(course3_data,course3_day_check,course3_slot_check);
	}
	
	private void update_day_wise_tt_of_course(String[] course_data, int[] day_checked_flag, int[] slot_check_flag) {
		
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
				
				switch(slot_check_flag[i]){
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
					default: slot_to_append = "_8to9"; break;
				}
				
				edit_tt.putString("name" + slot_to_append, course_data[0]);
				edit_tt.putString("num" + slot_to_append, course_data[1]);
				edit_tt.putString("venue" + slot_to_append,course_data[2]);
				edit_tt.commit();
				
			}
		}
	}
	
	private void update_course_wise_tt(String[] course_data, int[] day_checked_flag, int[] slot_check_flag) {
		
		int course_counter;
		
		SharedPreferences course_counter_sp = getSharedPreferences("CoursesList",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_course_counter = course_counter_sp.edit();
		
		if (course_counter_sp.contains("counter")){
			course_counter = course_counter_sp.getInt("counter",0);
		}else{
			course_counter = 0;
		}
		
		edit_course_counter.putInt("counter", course_counter + 1);
		edit_course_counter.putString("c" + Integer.toString(course_counter + 1), course_data[0]);
		edit_course_counter.commit();
		
		SharedPreferences add_course_sp = getSharedPreferences(course_data[0],Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_course_adder = add_course_sp.edit();
		edit_course_adder.putString("num", course_data[1]);
		edit_course_adder.putString("venue", course_data[2]);
		edit_course_adder.putInt("bm_check", 0);
		edit_course_adder.putInt("bunk_count", 0);
		
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
				
				switch(slot_check_flag[i]){
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
	
	@Override
    public void onBackPressed() {
		Intent select_year_branch_screen = new Intent(ShowInBuildTemplates.this, SelectYearBranch.class);
		select_year_branch_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(select_year_branch_screen);
		finish();
	}

	private void go_to_home_with_toast() {
		
		switch(year){
		// Make required Toast
	
			case 1: Toast.makeText(ShowInBuildTemplates.this, getResources().getString(R.string.toast_for_yr1), Toast.LENGTH_LONG).show();
				break;
				
			case 2: Toast.makeText(ShowInBuildTemplates.this, getResources().getString(R.string.toast_for_yr2), Toast.LENGTH_LONG).show();
				break;
				
			case 3: Toast.makeText(ShowInBuildTemplates.this, getResources().getString(R.string.toast_for_yr3), Toast.LENGTH_LONG).show();
				break;
				
			case 4: Toast.makeText(ShowInBuildTemplates.this, getResources().getString(R.string.toast_for_yr4), Toast.LENGTH_LONG).show();
				break;
		
		}
		
		Intent home_screen = new Intent(ShowInBuildTemplates.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}
	
	private void go_to_home_without_toast() {
		Intent home_screen = new Intent(ShowInBuildTemplates.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
