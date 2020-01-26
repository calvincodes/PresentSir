package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.content.Context;
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

public class EditPtNameNumVenue extends Activity {
	
	int bm_meter;
	EditText name, num, venue;
	Switch bm_switch;
	
	TextView bm_count_tv;
	ImageButton bm_count_dcr, bm_count_incr;
	int bm_count_temp;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit_pt_name_num_venue_page);
	    
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
        inflater.inflate(R.menu.add_course_name_num_venue_menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case R.id.acnnvm_set_tt:
        	if(name.getText().toString().equals("")
        		||num.getText().toString().equals("")
        		||venue.getText().toString().equals("")){
        		Toast.makeText(EditPtNameNumVenue.this, "Please fill all the required fields.", Toast.LENGTH_LONG).show();
        	}else
        		set_tt();
            return true;
            
        case R.id.acnnvm_reset_tt:
        	
        	name.setText("Physical Training");
        	num.setText("");
        	venue.setText("SAC Ground");
        	bm_switch.setChecked(false);
        	bm_count_temp = 0;
        	bm_count_tv.setText("Bunk Count: "+Integer.toString(bm_count_temp));
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	private void initialize() {
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		name = (EditText) findViewById(R.id.course_name_eptnnv_et);
	    num = (EditText) findViewById(R.id.course_num_eptnnv_et);
	    venue = (EditText) findViewById(R.id.course_venue_eptnnv_et);
	    bm_count_tv = (TextView) findViewById(R.id.bm_count_tv_eptnnv);
	    bm_switch = (Switch) findViewById(R.id.bunk_meter_switch_eptnnv);
	    
	    bm_count_dcr = (ImageButton) findViewById(R.id.down_bm_count_eptnnv);
	    bm_count_incr = (ImageButton) findViewById(R.id.up_bm_count_eptnnv);
	    
	    name.setTypeface(comic_font);
	    num.setTypeface(comic_font);
	    venue.setTypeface(comic_font);
	    bm_count_tv.setTypeface(comic_font);
	    bm_switch.setTypeface(comic_font);
	    
	}
	
	private void set_previous_time_table() {
		
	    SharedPreferences pt_tt_sp = getSharedPreferences("PTTimeTable",Context.MODE_PRIVATE);
	    
	    name.setText(pt_tt_sp.getString("name", "Physical Training"));
	    num.setText(pt_tt_sp.getString("num", ""));
	    venue.setText(pt_tt_sp.getString("venue", "SAC Ground"));
	    bm_count_tv.setText("Bunk Count: "+Integer.toString(pt_tt_sp.getInt("bunk_count", 0)));
	    
	    bm_count_temp = pt_tt_sp.getInt("bunk_count", 0);
	    
	    bm_meter = pt_tt_sp.getInt("bm_check", 0);
	    
	    if(bm_meter == 1){
	    	bm_switch.setChecked(true);
	    }else{
	    	bm_switch.setChecked(false);
	    }
	    
	}
	
	private void set_tt() {
		
		Intent edit_pt_screen = new Intent(EditPtNameNumVenue.this, EditPTTT.class);
		edit_pt_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		edit_pt_screen.putExtra("name", name.getText().toString());
		edit_pt_screen.putExtra("num", num.getText().toString());
		edit_pt_screen.putExtra("venue", venue.getText().toString());
		edit_pt_screen.putExtra("bm_count", bm_count_temp);
		edit_pt_screen.putExtra("bm_check", bm_meter);
		startActivity(edit_pt_screen);
		finish();
	}

	@Override
    public void onBackPressed() {
		Intent select_year_branch_screen = new Intent(EditPtNameNumVenue.this, ViewPTNSOTT.class);
		select_year_branch_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		select_year_branch_screen.putExtra("PT_or_NSO", 1);
		startActivity(select_year_branch_screen);
		finish();
	}

}
