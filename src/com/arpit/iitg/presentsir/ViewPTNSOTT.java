package com.arpit.iitg.presentsir;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class ViewPTNSOTT extends Activity {
	
	int pt_or_nso;
	
	TextView name, bm_count;
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
	    setContentView(R.layout.view_pt_nso_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    // Remove app name from display
        actionBar.setDisplayShowTitleEnabled(false);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    pt_or_nso = getIntent().getExtras().getInt("PT_or_NSO");
	    
	    Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
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
			      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
			      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
					      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
			      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
			      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
			      Toast.makeText(ViewPTNSOTT.this, getResources().getString(R.string.click_edit_to_edit), Toast.LENGTH_SHORT).show();
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
		if(pt_or_nso == 1){
			Intent edit_pt_screen = new Intent(ViewPTNSOTT.this, EditPtNameNumVenue.class);
			edit_pt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  		startActivity(edit_pt_screen);
		}else
		if(pt_or_nso == 2){
			Intent edit_nso_screen = new Intent(ViewPTNSOTT.this, EditNSOTT.class);
			edit_nso_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  		startActivity(edit_nso_screen);
		}
	}

	protected void BuildMsgAreYouSure() {
		
		new AlertDialog.Builder(this).setTitle("Delete Course")
        .setMessage(getResources().getString(R.string.course_delete_msg))
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	if(pt_or_nso == 1){
				delete_pt();
			}else
			if(pt_or_nso == 2){
				delete_nso();
			}
        }
        }).setNegativeButton("Cancel", null).show();
	}

	protected void delete_nso() {
		
		SharedPreferences del_nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		del_nso_nss_nss_tt_sp.edit().clear().commit();
		go_to_home();
	}

	protected void delete_pt() {
		
		SharedPreferences del_pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
		del_pt_tt_sp.edit().clear().commit();
		go_to_home();
	}

	private void initialize() {

		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		name = (TextView) findViewById(R.id.course_name_view_pt_nso_tv);
	    bm_count = (TextView) findViewById(R.id.bm_count_tv_view_pt_nso_tt);
	    bm_switch = (Switch) findViewById(R.id.bunk_meter_switch_view_pt_nso_ctt);
	    
	    day_check[0] = (CheckBox) findViewById(R.id.check_mon_view_pt_nso_ctt);
	    day_check[1] = (CheckBox) findViewById(R.id.check_tues_view_pt_nso_ctt);
	    day_check[2] = (CheckBox) findViewById(R.id.check_wed_view_pt_nso_ctt);
	    day_check[3] = (CheckBox) findViewById(R.id.check_thurs_view_pt_nso_ctt);
	    day_check[4] = (CheckBox) findViewById(R.id.check_fri_view_pt_nso_ctt);
	    
	    slot_tv[0] = (TextView) findViewById(R.id.time_slot_tv_mon_view_pt_nso_ctt);
	    slot_tv[1] = (TextView) findViewById(R.id.time_slot_tv_tues_view_pt_nso_ctt);
	    slot_tv[2] = (TextView) findViewById(R.id.time_slot_tv_wed_view_pt_nso_ctt);
	    slot_tv[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_view_pt_nso_ctt);
	    slot_tv[4] = (TextView) findViewById(R.id.time_slot_tv_fri_view_pt_nso_ctt);
	    
	    day_tv[0] = (TextView) findViewById(R.id.mon_disp_tv_vpnp);
	    day_tv[1] = (TextView) findViewById(R.id.tues_disp_tv_vpnp);
	    day_tv[2] = (TextView) findViewById(R.id.wed_disp_tv_vpnp);
	    day_tv[3] = (TextView) findViewById(R.id.thurs_disp_tv_vpnp);
	    day_tv[4] = (TextView) findViewById(R.id.fri_disp_tv_vpnp);
	    
	    for(int i = 0; i <= 4; i++){
	    	slot_to_display_in_tvs[i] = 1;
	    	day_checked_flag[i] = 0;
	    	slot_tv[i].setTypeface(comic_font);
	    	day_tv[i].setTypeface(comic_font);
	    }
	    
	    name.setTypeface(comic_font);
	    name.setPaintFlags(name.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    bm_count.setTypeface(comic_font);
	    bm_switch.setTypeface(comic_font);
	}
	
	private void set_previous_time_table() {
		
		String course_name = null;
		String day = null;
		String slot_to_append = null;
		String slot_to_show = null;
		
		if(pt_or_nso == 1){
	    	// PT
	    	
			SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
			name.setText("Course: Physical Training");
			bm_count.setText("Bunk Count: "+Integer.toString(pt_tt_sp.getInt("bunk_count", 0)));
			bm_meter = pt_tt_sp.getInt("bm_check", 0);
		    
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
				
				if(pt_tt_sp.getString(day,"No").equals("Yes")){
					
					day_check[i].setChecked(true);
					day_checked_flag[i] = 1;
					// While setting previous time table, update the day_checked_flag array as well.
					
					for(int j = 1; j <= 2; j++){
						
						switch(j){
							case 1: slot_to_append = "_morn5_45to6_30"; break;
							case 2: slot_to_append = "_eve5_45to6_30"; break;
						}
						
						
						if(pt_tt_sp.getString(day+slot_to_append,"No").equals("Yes")){
							
							switch(j){
								case 1: slot_to_show = "5.45am - 6.30am"; break;
								case 2: slot_to_show = "5.45pm - 6.30pm"; break;
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
		
		else if(pt_or_nso == 2){
    		// NSO, NSS, NCC
    		
			String course = null;
			
			SharedPreferences nso_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
			
			int course_value = nso_tt_sp.getInt("course_value", 0);
			
			switch(course_value){
				case 1: course = "NSO"; break;
				case 2: course = "NSS"; break;
				case 3: course = "NCC"; break;
				default: course = "Not Updated Yet"; break;
			}
			
			name.setText("Course: "+course);
			bm_count.setText("Bunk Count: "+Integer.toString(nso_tt_sp.getInt("bunk_count", 0)));
			bm_meter = nso_tt_sp.getInt("bm_check", 0);
		    
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
				
				if(nso_tt_sp.getString(day+"_eve6_30to8","No").equals("Yes")){
					
					day_check[i].setChecked(true);
					day_checked_flag[i] = 1;
					// While setting previous time table, update the day_checked_flag array as well.
					
					slot_to_show = "6.30pm - 8pm";
					slot_to_display_in_tvs[i] = 1;
					slot_tv[i].setText(slot_to_show);
					
				}else{
					slot_tv[i].setText("No class");
					slot_tv[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				}
			}
	    
		
		}

	} // end of set_previous_time_table()
	
	@Override
    public void onBackPressed() {
		Intent show_all_courses_screen = new Intent(ViewPTNSOTT.this, MyTimeTable.class);
		show_all_courses_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(show_all_courses_screen);
		finish();
	}
	
	private void go_to_home() {
		Intent home_screen = new Intent(ViewPTNSOTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}
}
