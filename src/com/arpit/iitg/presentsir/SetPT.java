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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SetPT extends Activity {
	
	String name, num, venue;
	int bunk_meter_flag;
	
	ImageButton[] up = new ImageButton[5];
	ImageButton[] down = new ImageButton[5];
	TextView[] time_slot = new TextView[5];
	TextView[] disp_day = new TextView[5];
	CheckBox[] check = new CheckBox[5];
	int[] slot_to_display = new int[5];
	int[] day_checked_flag = new int[5];
	int past_pt_bunk_count;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.set_pt_page);
	    
	    name = getIntent().getExtras().getString("name");
	    num = getIntent().getExtras().getString("num");
	    venue = getIntent().getExtras().getString("venue");
	    bunk_meter_flag = getIntent().getExtras().getInt("bm_flag");
	    
	    android.app.ActionBar actionBar = getActionBar();
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font_ab);
		
		initailize_layout();
		
		up[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[0] < 2){
					slot_to_display[0]++;
					set_display(slot_to_display[0],0);
				}
			}
		});
		
		up[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[1] < 2){
					slot_to_display[1]++;
					set_display(slot_to_display[1],1);
				}
			}
		});
		
		up[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[2] < 2){
					slot_to_display[2]++;
					set_display(slot_to_display[2],2);
				}
			}
		});
		
		up[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[3] < 2){
					slot_to_display[3]++;
					set_display(slot_to_display[3],3);
				}
			}
		});
		
		up[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[4] < 2){
					slot_to_display[4]++;
					set_display(slot_to_display[4],4);
				}
			}
		});
	    
	    down[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[0] > 1){
					slot_to_display[0]--;
					set_display(slot_to_display[0],0);
				}
			}
		});
	    
	    down[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[1] > 1){
					slot_to_display[1]--;
					set_display(slot_to_display[1],1);
				}
			}
		});
	    
	    down[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[2] > 1){
					slot_to_display[2]--;
					set_display(slot_to_display[2],2);
				}
			}
		});
	    
	    down[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[3] > 1){
					slot_to_display[3]--;
					set_display(slot_to_display[3],3);
				}
			}
		});
	    
	    down[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display[4] > 1){
					slot_to_display[4]--;
					set_display(slot_to_display[4],4);
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

		if((check[0].isChecked() || check[1].isChecked()) || check[2].isChecked() || check[3].isChecked() || check[4].isChecked()){
			
			SharedPreferences del_pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
			// Delete previous time PT table
			
			past_pt_bunk_count = del_pt_tt_sp.getInt("bunk_count",0);
			
			del_pt_tt_sp.edit().clear().commit();
			
			update_pt_time_table();
			
			BuildUpdateSuccessfulMsg();
			
		}else{

				Toast.makeText(SetPT.this, getResources().getString(R.string.select_at_least_one_day_msg), Toast.LENGTH_LONG).show();
		}
	}

	private void reset_action() {
		for(int i = 0; i <= 4; i++){
			check[i].setChecked(false);
			slot_to_display[i] = 1;
			set_display(slot_to_display[i],i);
		}
	}

	private void initailize_layout() {
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		up[0] = (ImageButton) findViewById(R.id.up_mon_spt);
		up[1] = (ImageButton) findViewById(R.id.up_tues_spt);
		up[2] = (ImageButton) findViewById(R.id.up_wed_spt);
		up[3] = (ImageButton) findViewById(R.id.up_thurs_spt);
		up[4] = (ImageButton) findViewById(R.id.up_fri_spt);
	    down[0] = (ImageButton) findViewById(R.id.down_mon_spt);
	    down[1] = (ImageButton) findViewById(R.id.down_tues_spt);
	    down[2] = (ImageButton) findViewById(R.id.down_wed_spt);
	    down[3] = (ImageButton) findViewById(R.id.down_thurs_spt);
	    down[4] = (ImageButton) findViewById(R.id.down_fri_spt);
	    time_slot[0] = (TextView) findViewById(R.id.time_slot_tv_mon_spt);
	    time_slot[1] = (TextView) findViewById(R.id.time_slot_tv_tues_spt);
	    time_slot[2] = (TextView) findViewById(R.id.time_slot_tv_wed_spt);
	    time_slot[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_spt);
	    time_slot[4] = (TextView) findViewById(R.id.time_slot_tv_fri_spt);
	    disp_day[0] = (TextView) findViewById(R.id.disp_mon_tv_spt);
	    disp_day[1] = (TextView) findViewById(R.id.disp_tues_tv_spt);
	    disp_day[2] = (TextView) findViewById(R.id.disp_wed_tv_spt);
	    disp_day[3] = (TextView) findViewById(R.id.disp_thurs_tv_spt);
	    disp_day[4] = (TextView) findViewById(R.id.disp_fri_tv_spt);
	    check[0] = (CheckBox) findViewById(R.id.check_mon_spt);
	    check[1] = (CheckBox) findViewById(R.id.check_tues_spt);
	    check[2] = (CheckBox) findViewById(R.id.check_wed_spt);
	    check[3] = (CheckBox) findViewById(R.id.check_thurs_spt);
	    check[4] = (CheckBox) findViewById(R.id.check_fri_spt);
	    for(int i = 0; i <= 4; i++){
	    	slot_to_display[i] = 1;
	    	day_checked_flag[i] = 0;
	    }
	    
	    time_slot[0].setTypeface(comic_font);
	    time_slot[1].setTypeface(comic_font);
	    time_slot[2].setTypeface(comic_font);
	    time_slot[3].setTypeface(comic_font);
	    time_slot[4].setTypeface(comic_font);
	    disp_day[0].setTypeface(comic_font);
	    disp_day[1].setTypeface(comic_font);
	    disp_day[2].setTypeface(comic_font);
	    disp_day[3].setTypeface(comic_font);
	    disp_day[4].setTypeface(comic_font);
	}
	
	private void set_display(int slot_to_display,int day_to_display_in) {
		
		switch(slot_to_display){
			
			case 1:  time_slot[day_to_display_in].setText("5.45am - 6.30am"); break;
			case 2:  time_slot[day_to_display_in].setText("5.45pm - 6.30pm"); break;
			default: time_slot[day_to_display_in].setText("5.45am - 6.30am"); break;
		}
	}

	private void update_pt_time_table(){
		
		SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_pt_tt = pt_tt_sp.edit();
		
		edit_pt_tt.putString("name", name);
		edit_pt_tt.putString("num", num);
		edit_pt_tt.putString("venue", venue);
		edit_pt_tt.putInt("bm_check", bunk_meter_flag);
		edit_pt_tt.putInt("bunk_count", past_pt_bunk_count);
		
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
				
				switch(slot_to_display[i]){
					case 1: slot_to_append = "_morn5_45to6_30"; break;
					case 2: slot_to_append = "_eve5_45to6_30"; break;
				}
				
				edit_pt_tt.putString(day, "Yes");
				edit_pt_tt.putString(day+slot_to_append, "Yes");
				
			}// end of if
		}// end of for loop
		
		edit_pt_tt.commit();
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
        	go_to_settings();
        }
        }).setNegativeButton("No", null).show();
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
	
	private void go_to_settings() {
		Intent home_screen = new Intent(SetPT.this, Settings.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

	private void go_to_home() {
		Intent home_screen = new Intent(SetPT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
