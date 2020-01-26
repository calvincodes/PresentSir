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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddNSO_NCC_NSS extends Activity {
	
	Switch BunkMeterSwitch;
	CheckBox[] check = new CheckBox[5];
	int[] day_checked_flag = new int[5];
	int bunk_meter_flag = 0;
	RadioGroup course_select_rg;
	RadioButton nso_rb, nss_rb, ncc_rb;
	
	TextView[] day_disp = new TextView[5];
	TextView[] slot_disp = new TextView[5];
	
	int course_num = 0;
	
	int past_pt_bunk_count;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_nso_ncc_nss_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	   
		Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font_ab);
		
	    initalize();
	    
	  //set the switch to OFF 
	    BunkMeterSwitch.setChecked(false);
	    
	    //attach a listener to check for changes in state
	    BunkMeterSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    	
    	@Override
	     public void onCheckedChanged(CompoundButton buttonView,
	       boolean isChecked) {
	   
		      if(isChecked){
		    	  bunk_meter_flag = 1;
		      }else{
		    	  bunk_meter_flag = 0;
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
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	private void add_action() {

		if(nso_rb.isChecked())
			course_num = 1;
		else
			if(nss_rb.isChecked())
				course_num = 2;
			else
				if(ncc_rb.isChecked())
					course_num = 3;
				else
					course_num = 0;
		
		if(course_num != 0
				&&(check[0].isChecked() || check[1].isChecked()) || check[2].isChecked() || check[3].isChecked() || check[4].isChecked()){
			
			SharedPreferences del_nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
			// Delete previous time PT table
			
			past_pt_bunk_count = del_nso_nss_nss_tt_sp.getInt("bunk_count",0);
			
			del_nso_nss_nss_tt_sp.edit().clear().commit();
			
			update_nso_time_table();
			
			BuildUpdateSuccessfulMsg();
			
		}else{

			if(course_num == 0)
				Toast.makeText(AddNSO_NCC_NSS.this, "Please select one among NSO, NSS and NCC.", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(AddNSO_NCC_NSS.this, getResources().getString(R.string.select_at_least_one_day_msg), Toast.LENGTH_LONG).show();
		}
	}

	private void reset_action() {
		course_num = 0;
		course_select_rg.clearCheck();
		BunkMeterSwitch.setChecked(false);
		for(int i = 0; i <= 4; i++){
			check[i].setChecked(false);
		}
	}
	
	private void initalize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		course_select_rg = (RadioGroup) findViewById(R.id.nso_nss_ncc_rg_annnb);
		nso_rb = (RadioButton) findViewById(R.id.rb_nso_annnp);
		nss_rb = (RadioButton) findViewById(R.id.rb_nss_annnp);
		ncc_rb = (RadioButton) findViewById(R.id.rb_ncc_annnp);
	    check[0] = (CheckBox) findViewById(R.id.check_mon_annnp);
	    check[1] = (CheckBox) findViewById(R.id.check_tues_annnp);
	    check[2] = (CheckBox) findViewById(R.id.check_wed_annnp);
	    check[3] = (CheckBox) findViewById(R.id.check_thurs_annnp);
	    check[4] = (CheckBox) findViewById(R.id.check_fri_annnp);
	    BunkMeterSwitch = (Switch) findViewById(R.id.bunk_meter_switch_annnp);
	    for(int i = 0; i <= 4; i++){
	    	day_checked_flag[i] = 0;
	    }
	    
	    day_disp[0] = (TextView) findViewById(R.id.disp_mon_tv_annnp);
	    day_disp[1] = (TextView) findViewById(R.id.disp_tues_tv_annnp);
	    day_disp[2] = (TextView) findViewById(R.id.disp_wed_tv_annnp);
	    day_disp[3] = (TextView) findViewById(R.id.disp_thurs_tv_annnp);
	    day_disp[4] = (TextView) findViewById(R.id.disp_fri_tv_annnp);
	    slot_disp[0] = (TextView) findViewById(R.id.time_slot_tv_mon_annnp);
	    slot_disp[1] = (TextView) findViewById(R.id.time_slot_tv_tues_annnp);
	    slot_disp[2] = (TextView) findViewById(R.id.time_slot_tv_wed_annnp);
	    slot_disp[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_annnp);
	    slot_disp[4] = (TextView) findViewById(R.id.time_slot_tv_fri_annnp);
	    
	    nso_rb.setTypeface(comic_font);
	    nss_rb.setTypeface(comic_font);
	    ncc_rb.setTypeface(comic_font);
	    BunkMeterSwitch.setTypeface(comic_font);
	    day_disp[0].setTypeface(comic_font);
	    day_disp[1].setTypeface(comic_font);
	    day_disp[2].setTypeface(comic_font);
	    day_disp[3].setTypeface(comic_font);
	    day_disp[4].setTypeface(comic_font);
	    slot_disp[0].setTypeface(comic_font);
	    slot_disp[1].setTypeface(comic_font);
	    slot_disp[2].setTypeface(comic_font);
	    slot_disp[3].setTypeface(comic_font);
	    slot_disp[4].setTypeface(comic_font);
	}
	
	private void update_nso_time_table(){
		
		SharedPreferences nso_nss_nss_tt_sp = getSharedPreferences("NSO_NSS_NCC_TimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_nso_nss_ncc_tt = nso_nss_nss_tt_sp.edit();
		
		edit_nso_nss_ncc_tt.putInt("course_value", course_num);
		// 1 - NSO, 2 - NSS, 3 - NCC
		edit_nso_nss_ncc_tt.putInt("bm_check", bunk_meter_flag);
		edit_nso_nss_ncc_tt.putInt("bunk_count", past_pt_bunk_count);
		
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
		if (check[0].isChecked()) {	//Monday checked
            day_checked_flag[0] = 1;
        }else day_checked_flag[0] = 0;
	    
	    if (check[1].isChecked()) { //Tuesday checked
	    	day_checked_flag[1] = 1;
        }else day_checked_flag[1] = 0;

		if (check[2].isChecked()) { //Wednesday checked
			day_checked_flag[2] = 1; 
		}else day_checked_flag[2] = 0;
		
		if (check[3].isChecked()) {	//Thursday checked
			day_checked_flag[3] = 1; 
		}else day_checked_flag[3] = 0;
		
		if (check[4].isChecked()) { //Friday checked
			day_checked_flag[4] = 1; 
		}else day_checked_flag[4] = 0;
	}
	
	private void BuildUpdateSuccessfulMsg() {

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Successfully Added")
        .setMessage(getResources().getString(R.string.course_added_msg))
        .setCancelable(false)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	go_to_home();
        }
        }).show();
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
        	go_to_select_screen();
        }
        }).setNegativeButton("No", null).show();
	}
	
	private void go_to_select_screen() {
		Intent home_screen = new Intent(AddNSO_NCC_NSS.this, SelectCoursePtNsoToAdd.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}
	
	private void go_to_home() {
		Intent home_screen = new Intent(AddNSO_NCC_NSS.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
