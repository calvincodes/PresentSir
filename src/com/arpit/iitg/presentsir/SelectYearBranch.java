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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectYearBranch extends Activity {
	
	protected static final OnCheckedChangeListener listener2 = null;
	protected static final OnCheckedChangeListener listener1 = null;
	Spinner year, branch;
	Button show;
	
	RadioGroup year_grp, branch_grp1, branch_grp2;
	RadioButton year_1, year_2, year_3, year_4;
	RadioButton bt, ce, cl, cse, cst, dd, ece, eee, ep, me, mndc;
	
	int year_pos = -1;
	int branch_pos = -1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.select_year_branch_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
	    
	    int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    year_grp = (RadioGroup) findViewById(R.id.year_rg);
	    
	    year_1 = (RadioButton) findViewById(R.id.year_1_rb);
	    year_2 = (RadioButton) findViewById(R.id.year_2_rb);
	    year_3 = (RadioButton) findViewById(R.id.year_3_rb);
	    year_4 = (RadioButton) findViewById(R.id.year_4_rb);
	    
	    branch_grp1 = (RadioGroup) findViewById(R.id.branch_rb1);
	    
	    bt = (RadioButton) findViewById(R.id.branch_bt);
	    ce = (RadioButton) findViewById(R.id.branch_ce);
	    cl = (RadioButton) findViewById(R.id.branch_cl);
	    cse = (RadioButton) findViewById(R.id.branch_cse);
	    cst = (RadioButton) findViewById(R.id.branch_cst);
	    
	    branch_grp2 = (RadioGroup) findViewById(R.id.branch_rb2);
	    
	    dd = (RadioButton) findViewById(R.id.branch_dd);
	    ece = (RadioButton) findViewById(R.id.branch_ece);
	    eee = (RadioButton) findViewById(R.id.branch_eee);
	    ep = (RadioButton) findViewById(R.id.branch_ep);
	    me = (RadioButton) findViewById(R.id.branch_me);
	    mndc = (RadioButton) findViewById(R.id.branch_mndc);
	    
	    year_1.setTypeface(comic_font);
	    year_2.setTypeface(comic_font);
	    year_3.setTypeface(comic_font);
	    year_4.setTypeface(comic_font);
	    
	    bt.setTypeface(comic_font);
	    ce.setTypeface(comic_font);
	    cl.setTypeface(comic_font);
	    cse.setTypeface(comic_font);
	    cst.setTypeface(comic_font);
	    dd.setTypeface(comic_font);
	    ece.setTypeface(comic_font);
	    eee.setTypeface(comic_font);
	    ep.setTypeface(comic_font);
	    me.setTypeface(comic_font);
	    mndc.setTypeface(comic_font);
	    
	    year_grp.clearCheck();
		branch_grp1.clearCheck();
		branch_grp2.clearCheck();
		
		year_1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year_pos = 1;
				if (branch_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(1);
			}
		});
		
		year_2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year_pos = 2;
				if (branch_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(1);
			}
		});
		
		year_3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year_pos = 3;
				if (branch_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(1);
			}
		});
		
		year_4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year_pos = 4;
				if (branch_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(1);
			}
		});
		
		// These many on click listeners are required to make the layout in the form of a grid.
		// It is not possible to make a radio group in the form of a group directly.
		
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(bt.isChecked()){
					// Do nothing
				}else{
					branch_grp1.clearCheck();
					bt.setChecked(true);
				}
				set_grp_2_false();
				
				branch_pos = 1;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		ce.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(ce.isChecked()){
					// Do nothing
				}else{
					branch_grp1.clearCheck();
					ce.setChecked(true);
				}
				set_grp_2_false();
				
				branch_pos = 2;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		cl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(cl.isChecked()){
					// Do nothing
				}else{
					branch_grp1.clearCheck();
					cl.setChecked(true);
				}
				set_grp_2_false();
				
				branch_pos = 3;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		cse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(cse.isChecked()){
					// Do nothing
				}else{
					branch_grp1.clearCheck();
					cse.setChecked(true);
				}
				set_grp_2_false();
				
				branch_pos = 4;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		cst.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(cst.isChecked()){
					// Do nothing
				}else{
					branch_grp1.clearCheck();
					cst.setChecked(true);
				}
				set_grp_2_false();
				
				branch_pos = 5;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		dd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(dd.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					dd.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 6;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		ece.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(ece.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					ece.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 7;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		eee.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(eee.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					eee.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 8;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		ep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(ep.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					ep.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 9;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		me.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(me.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					me.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 10;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
		mndc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mndc.isChecked()){
					// Do nothing
				}else{
					branch_grp2.clearCheck();
					mndc.setChecked(true);
				}
				set_grp_1_false();
				
				branch_pos = 11;
				
				if (year_pos != -1)
					show_courses(year_pos, branch_pos);
				else
					make_toast(2);
			}
		});
		
	}
	
	protected void make_toast(int i) {
		
		if(i == 1)
			Toast.makeText(SelectYearBranch.this, "Select Branch", Toast.LENGTH_SHORT).show();
		else if (i == 2)
			Toast.makeText(SelectYearBranch.this, "Select Year", Toast.LENGTH_SHORT).show();
	}

	protected void set_grp_1_false() {

		bt.setChecked(false);
		ce.setChecked(false);
		cl.setChecked(false);
		cse.setChecked(false);
		cst.setChecked(false);
		
	}

	
	protected void set_grp_2_false() {

		dd.setChecked(false);
		ece.setChecked(false);
		eee.setChecked(false);
		ep.setChecked(false);
		me.setChecked(false);
		mndc.setChecked(false);
		
	}

	protected void show_courses(int year, int branch) {
			
			Intent template_screen = new Intent(SelectYearBranch.this, ShowInBuildTemplates.class);
			template_screen.putExtra("year", year);
			template_screen.putExtra("branch", branch);
			startActivity(template_screen);
		
	}

	@Override
    public void onBackPressed() {
		go_to_settings();
	}

	private void go_to_settings() {
		Intent home_screen = new Intent(SelectYearBranch.this, Settings.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}
	
}
