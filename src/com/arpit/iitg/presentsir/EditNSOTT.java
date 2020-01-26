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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EditNSOTT extends Activity {
	
	int bm_meter;
	String course_name;
	Switch bm_switch;
	CheckBox[] day_check = new CheckBox[5];
	int[] day_checked_flag = new int[5];
	
	RadioGroup select_nso_nss_ncc;
	RadioButton nso, nss, ncc;
	
	TextView bm_count_tv;
	ImageButton bm_count_dcr, bm_count_incr;
	int bm_count_temp;
	
	int past_bm_check;
	int past_course_value;
	int[] past_day_checked_flag = new int[5];
	
	TextView[] day_disp = new TextView[5];
	TextView[] slot_disp = new TextView[5];

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_nso_nss_ncc_bc_tt_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font_ab);
	
	    initialize();
	    
	    set_previous_time_table();
	    
	    bm_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	
	    	@Override
		     public void onCheckedChanged(CompoundButton buttonView,
		       boolean isChecked) {
		   
			      if(isChecked){
			    	  bm_meter = 1;
			      }else{
			    	  bm_meter = 0;
			      }
	     		}
	    });
	    
	    bm_count_dcr.setOnClickListener(new View.OnClickListener(
	    		) {
			
			@Override
			public void onClick(View v) {
				
				if(bm_count_temp > 0){
					bm_count_temp --;
				}
				
				bm_count_tv.setText("Bunk Count: "+Integer.toString(bm_count_temp));
			}
		});
	    
	    bm_count_incr.setOnClickListener(new View.OnClickListener(
	    		) {
			
			@Override
			public void onClick(View v) {
				
				bm_count_temp ++;
				bm_count_tv.setText("Bunk Count: "+Integer.toString(bm_count_temp));
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
        	bm_switch.setChecked(false);
        	select_nso_nss_ncc.clearCheck();
			for(int i = 0; i <= 4; i++){
				day_check[i].setChecked(false);
			}
			bm_count_temp = 0;
			bm_count_tv.setText("Bunk Count: "+Integer.toString(bm_count_temp));
            return true;
            
        case R.id.save_tt_sttm:
            add_action();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	private void add_action() {
		
		if((nso.isChecked() || nss.isChecked() || ncc.isChecked())
				&&(day_check[0].isChecked() || day_check[1].isChecked()) || day_check[2].isChecked() || day_check[3].isChecked() || day_check[4].isChecked()){
			
			SharedPreferences del_nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
			// Delete previous time PT table
			
			del_nso_nss_nss_tt_sp.edit().clear().commit();
			
			update_nso_time_table();
			
			BuildUpdateSuccessfulMsg();
			
		}else{

			if(!(nso.isChecked() || nss.isChecked() || ncc.isChecked()))
				Toast.makeText(EditNSOTT.this, "Please select one among NSO, NSS and NCC.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(EditNSOTT.this, "Please set time table of at least one day.", Toast.LENGTH_LONG).show();
		}
		
	}

	private void initialize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		bm_count_tv = (TextView) findViewById(R.id.bm_count_tv_ennnbtp);
	    bm_switch = (Switch) findViewById(R.id.bunk_meter_switch_ennnbtp);
	    
	    select_nso_nss_ncc = (RadioGroup) findViewById(R.id.nso_nss_ncc_rg_ennnbtp);
	    
	    nso = (RadioButton) findViewById(R.id.rb_nso_ennnbtp);
	    nss = (RadioButton) findViewById(R.id.rb_nss_ennnbtp);
	    ncc = (RadioButton) findViewById(R.id.rb_ncc_ennnbtp);
	    
	    bm_count_dcr = (ImageButton) findViewById(R.id.down_bm_count_ennnbtp);
	    bm_count_incr = (ImageButton) findViewById(R.id.up_bm_count_ennnbtp);
	    
	    day_check[0] = (CheckBox) findViewById(R.id.check_mon_ennnbtp);
	    day_check[1] = (CheckBox) findViewById(R.id.check_tues_ennnbtp);
	    day_check[2] = (CheckBox) findViewById(R.id.check_wed_ennnbtp);
	    day_check[3] = (CheckBox) findViewById(R.id.check_thurs_ennnbtp);
	    day_check[4] = (CheckBox) findViewById(R.id.check_fri_ennnbtp);
	    
	    day_disp[0] = (TextView) findViewById(R.id.disp_mon_tv_ennnbtp);
	    day_disp[1] = (TextView) findViewById(R.id.disp_tues_tv_ennnbtp);
	    day_disp[2] = (TextView) findViewById(R.id.disp_wed_tv_ennnbtp);
	    day_disp[3] = (TextView) findViewById(R.id.disp_thurs_tv_ennnbtp);
	    day_disp[4] = (TextView) findViewById(R.id.disp_fri_tv_ennnbtp);
	    slot_disp[0] = (TextView) findViewById(R.id.time_slot_tv_mon_ennnbtp);
	    slot_disp[1] = (TextView) findViewById(R.id.time_slot_tv_tues_ennnbtp);
	    slot_disp[2] = (TextView) findViewById(R.id.time_slot_tv_wed_ennnbtp);
	    slot_disp[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_ennnbtp);
	    slot_disp[4] = (TextView) findViewById(R.id.time_slot_tv_fri_ennnbtp);
	    
	    for(int i = 0; i <= 4; i++){
	    	day_checked_flag[i] = 0;
	    	day_disp[i].setTypeface(comic_font);
	    	slot_disp[i].setTypeface(comic_font);
	    }
	    
	    nso.setTypeface(comic_font);
	    nss.setTypeface(comic_font);
	    ncc.setTypeface(comic_font);
	    bm_switch.setTypeface(comic_font);
	    bm_count_tv.setTypeface(comic_font);
	    
	}
	
	private void set_previous_time_table() {
		
		String day = null;
		
		SharedPreferences nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
	    
		past_course_value = nso_nss_nss_tt_sp.getInt("course_value",0);
		
		if(past_course_value == 1)
			nso.setChecked(true);
		else
			if(past_course_value == 2)
				nss.setChecked(true);
			else
				if(past_course_value == 3)
					ncc.setChecked(true);
		
		
		bm_count_tv.setText("Bunk Count: "+Integer.toString(nso_nss_nss_tt_sp.getInt("bunk_count", 0)));
	    
	    bm_count_temp = nso_nss_nss_tt_sp.getInt("bunk_count", 0);
	    
	    bm_meter = nso_nss_nss_tt_sp.getInt("bm_check", 0);
	    
	    if(bm_meter == 1){
	    	bm_switch.setChecked(true);
	    	past_bm_check = 1;
	    }else{
	    	bm_switch.setChecked(false);
	    	past_bm_check = 0;
	    }
	    
		for(int i = 0; i <= 4; i++){
			
			switch(i){
				case 0: day = "Monday"; break;
				case 1: day = "Tuesday"; break;
				case 2: day = "Wednesday"; break;
				case 3: day = "Thursday"; break;
				case 4: day = "Friday"; break;
			}
			
			if(nso_nss_nss_tt_sp.getString(day+"_eve6_30to8","No").equals("Yes")){
				
				day_check[i].setChecked(true);
				past_day_checked_flag[i] = 1;
				// While setting previous time table, update the day_checked_flag array as well.
				
			}
				
		}// end of for loop
		
	}
	
	private void update_nso_time_table(){
		
		SharedPreferences nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_nso_nss_ncc_tt = nso_nss_nss_tt_sp.edit();
		
		int new_course_val = 0;
		
		if(nso.isChecked())
			new_course_val = 1;
		else
			if(nss.isChecked())
				new_course_val = 2;
			else
				if(ncc.isChecked())
					new_course_val = 3;
		
		edit_nso_nss_ncc_tt.putInt("course_value", new_course_val);
		// 1 - NSO, 2 - NSS, 3 - NCC
		edit_nso_nss_ncc_tt.putInt("bm_check", bm_meter);
		edit_nso_nss_ncc_tt.putInt("bunk_count", bm_count_temp);
		
		set_day_check_flags();
		
		String day = null;
		
		for(int i = 0; i <= 4; i++){
			
			if(day_checked_flag[i]	== 1){
				
				switch(i){
					case 0: day = "Monday"; break;
					case 1: day = "Tuesday"; break;
					case 2: day = "Wednesday"; break;
					case 3: day = "Thursday"; break;
					case 4: day = "Friday"; break;
				}
				
				edit_nso_nss_ncc_tt.putString(day+"_eve6_30to8", "Yes");
				
			}// end of if
		}// end of for loop
		
		edit_nso_nss_ncc_tt.commit();
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
        	Intent select_year_branch_screen = new Intent(EditNSOTT.this, ViewPTNSOTT.class);
    		select_year_branch_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		select_year_branch_screen.putExtra("PT_or_NSO", 2);
    		startActivity(select_year_branch_screen);
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
	
	private void go_to_home() {
		Intent home_screen = new Intent(EditNSOTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
