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
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EditPTTT extends Activity {

	int bm_meter, bm_count;
	String name, num, venue;
	CheckBox[] day_check = new CheckBox[5]; 
	TextView[] slot_tv = new TextView[5];
	TextView[] disp_day = new TextView[5];
	int[] slot_to_display_in_tvs = new int[5];
	int[] day_checked_flag = new int[5];
	ImageButton[] up = new ImageButton[5];
	ImageButton[] down = new ImageButton[5];
	
	int[] past_slot_displayed_in_tvs = new int[5];
	int[] past_day_checked_flag = new int[5];
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_pt_tt_page);
	    
	    name = getIntent().getExtras().getString("name");
	    num = getIntent().getExtras().getString("num");
	    venue = getIntent().getExtras().getString("venue");
	    bm_count = getIntent().getExtras().getInt("bm_count");
	    bm_meter = getIntent().getExtras().getInt("bm_check");
	    
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
	    
	    up[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[0] < 2){
					slot_to_display_in_tvs[0]++;
					set_display(slot_to_display_in_tvs[0],0);
				}
			}
		});
		
		up[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[1] < 2){
					slot_to_display_in_tvs[1]++;
					set_display(slot_to_display_in_tvs[1],1);
				}
			}
		});
		
		up[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[2] < 2){
					slot_to_display_in_tvs[2]++;
					set_display(slot_to_display_in_tvs[2],2);
				}
			}
		});
		
		up[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[3] < 2){
					slot_to_display_in_tvs[3]++;
					set_display(slot_to_display_in_tvs[3],3);
				}
			}
		});
		
		up[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(slot_to_display_in_tvs[4] < 2){
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
			
			SharedPreferences del_pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
			// Delete previous time PT table
			
			del_pt_tt_sp.edit().clear().commit();
			
			update_pt_time_table();
			
			BuildUpdateSuccessfulMsg();
		}
		else{
				Toast.makeText(EditPTTT.this, getResources().getString(R.string.select_at_least_one_day_msg), Toast.LENGTH_LONG).show();
		}
	}

	private void reset_action() {
		for(int i = 0; i <= 4; i++){
			day_check[i].setChecked(false);
			slot_tv[i].setText("5.45am - 6.30am");
		}
	}

	private void initialize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		day_check[0] = (CheckBox) findViewById(R.id.check_mon_eptp);
	    day_check[1] = (CheckBox) findViewById(R.id.check_tues_eptp);
	    day_check[2] = (CheckBox) findViewById(R.id.check_wed_eptp);
	    day_check[3] = (CheckBox) findViewById(R.id.check_thurs_eptp);
	    day_check[4] = (CheckBox) findViewById(R.id.check_fri_eptp);
	    
	    slot_tv[0] = (TextView) findViewById(R.id.time_slot_tv_mon_eptp);
	    slot_tv[1] = (TextView) findViewById(R.id.time_slot_tv_tues_eptp);
	    slot_tv[2] = (TextView) findViewById(R.id.time_slot_tv_wed_eptp);
	    slot_tv[3] = (TextView) findViewById(R.id.time_slot_tv_thurs_eptp);
	    slot_tv[4] = (TextView) findViewById(R.id.time_slot_tv_fri_eptp);
	    
	    disp_day[0] = (TextView) findViewById(R.id.disp_mon_tv_eptp);
	    disp_day[1] = (TextView) findViewById(R.id.disp_tues_tv_eptp);
	    disp_day[2] = (TextView) findViewById(R.id.disp_wed_tv_eptp);
	    disp_day[3] = (TextView) findViewById(R.id.disp_thurs_tv_eptp);
	    disp_day[4] = (TextView) findViewById(R.id.disp_fri_tv_eptp);
	    
	    for(int i = 0; i <= 4; i++){
	    	slot_to_display_in_tvs[i] = 1;
	    	day_checked_flag[i] = 0;
	    	slot_tv[i].setTypeface(comic_font);
	    	disp_day[i].setTypeface(comic_font);
	    }
	    
	    up[0] = (ImageButton) findViewById(R.id.up_mon_eptp);
		up[1] = (ImageButton) findViewById(R.id.up_tues_eptp);
		up[2] = (ImageButton) findViewById(R.id.up_wed_eptp);
		up[3] = (ImageButton) findViewById(R.id.up_thurs_eptp);
		up[4] = (ImageButton) findViewById(R.id.up_fri_eptp);
	    down[0] = (ImageButton) findViewById(R.id.down_mon_eptp);
	    down[1] = (ImageButton) findViewById(R.id.down_tues_eptp);
	    down[2] = (ImageButton) findViewById(R.id.down_wed_eptp);
	    down[3] = (ImageButton) findViewById(R.id.down_thurs_eptp);
	    down[4] = (ImageButton) findViewById(R.id.down_fri_eptp);
	}

	
	private void set_display(int slot_to_display,int day_to_display_in) {

		switch(slot_to_display){
		
			case 1:  slot_tv[day_to_display_in].setText("5.45am - 6.30am"); break;
			case 2:  slot_tv[day_to_display_in].setText("5.45pm - 6.30pm"); break;
			default: slot_tv[day_to_display_in].setText("5.45am - 6.30am"); break;
		}
	}
	
	private void set_previous_time_table() {
		
		String day = null;
		String slot_to_append = null;
		String slot_to_show = null;
		
	    SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
	    
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
				past_day_checked_flag[i] = 1;
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
						past_slot_displayed_in_tvs[i] = j;
						slot_tv[i].setText(slot_to_show);
						break;
					}
					
				}
			}
				
		}// end of for loop
		
	}
	
	private void update_pt_time_table(){
		
		SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
		SharedPreferences.Editor edit_pt_tt = pt_tt_sp.edit();
		
		edit_pt_tt.putString("name", name);
		edit_pt_tt.putString("num", num);
		edit_pt_tt.putString("venue", venue);
		edit_pt_tt.putInt("bm_check", bm_meter);
		edit_pt_tt.putInt("bunk_count", bm_count);
		
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
        	Intent select_year_branch_screen = new Intent(EditPTTT.this, ViewPTNSOTT.class);
    		select_year_branch_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		select_year_branch_screen.putExtra("PT_or_NSO", 1);
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
		Intent home_screen = new Intent(EditPTTT.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}
	
}
