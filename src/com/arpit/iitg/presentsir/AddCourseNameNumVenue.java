package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AddCourseNameNumVenue extends Activity {
	
	EditText course_name, course_num, course_venue;
	Switch BunkMeterSwitch;
	
	String name, num, venue;
	int bm_meter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_course_name_num_venue_page);
	
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	
	    course_name = (EditText) findViewById(R.id.course_name_acnnv_et);
	    course_num = (EditText) findViewById(R.id.course_num_acnnv_et);
	    course_venue = (EditText) findViewById(R.id.course_venue_acnnv_et);
	    BunkMeterSwitch = (Switch) findViewById(R.id.bunk_meter_switch_acnnv);
	    
	    course_name.setTypeface(comic_font);
	    course_num.setTypeface(comic_font);
	    course_venue.setTypeface(comic_font);
	    BunkMeterSwitch.setTypeface(comic_font);
	    
	    BunkMeterSwitch.setChecked(false);
	    
	    BunkMeterSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    	
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
        	if(course_name.getText().toString().equals("")
        		||course_num.getText().toString().equals("")
        		||course_venue.getText().toString().equals("")){
        		Toast.makeText(AddCourseNameNumVenue.this, "Please fill all the required fields.", Toast.LENGTH_LONG).show();
        	}else
        		set_tt();
            return true;
            
        case R.id.acnnvm_reset_tt:
        	course_name.setText("");
        	course_num.setText("");
        	course_venue.setText("");
        	BunkMeterSwitch.setChecked(false);
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	private void set_tt() {

		name = course_name.getText().toString();
		num = course_num.getText().toString();
		venue = course_venue.getText().toString();
		
		Intent set_tt_screen = new Intent(AddCourseNameNumVenue.this, SetTT.class);
		set_tt_screen.putExtra("name", name);
		set_tt_screen.putExtra("num", num);
		set_tt_screen.putExtra("venue", venue);
		set_tt_screen.putExtra("bm_flag", bm_meter);
		startActivity(set_tt_screen);
	}
	
	@Override
    public void onBackPressed() {
		Intent home_screen = new Intent(AddCourseNameNumVenue.this, SelectCoursePtNsoToAdd.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
