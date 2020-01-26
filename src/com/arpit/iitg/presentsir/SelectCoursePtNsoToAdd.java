package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCoursePtNsoToAdd extends Activity {
	
	TextView course, pt, nso;
	TextView title, hint;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.select_course_pt_nso_to_add_page);
		
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
		
		course = (TextView) findViewById(R.id.add_course_scpntap);
		pt = (TextView) findViewById(R.id.add_pt_scpntap);
		nso = (TextView) findViewById(R.id.add_nso_nss_ncc_scpntap);
		
		title = (TextView) findViewById(R.id.title_scpntap);
		hint = (TextView) findViewById(R.id.select_what_to_add_hint_scpntap);
		
		course.setTypeface(comic_font);
		pt.setTypeface(comic_font);
		nso.setTypeface(comic_font);
		title.setTypeface(comic_font);
		hint.setTypeface(comic_font);
		
		title.setPaintFlags(title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
		
		title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Toast.makeText(SelectCoursePtNsoToAdd.this, "Please select one among Course, PT or NSO/NSS/NCC", Toast.LENGTH_SHORT).show();
			}
		});
		
		course.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent ett_course_tt_screen = new Intent(SelectCoursePtNsoToAdd.this, AddCourseNameNumVenue.class);
				startActivity(ett_course_tt_screen);
			}
		});
		
		pt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent pt_tt_screen = new Intent(SelectCoursePtNsoToAdd.this, AddPtNameNumVenue.class);
				startActivity(pt_tt_screen);
			}
		});
		
		nso.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent nso_tt_screen = new Intent(SelectCoursePtNsoToAdd.this, AddNSO_NCC_NSS.class);
				startActivity(nso_tt_screen);
			}
		});


	    
	}

}
