package com.arpit.iitg.presentsir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class Settings extends Activity {
	
	TextView use_tt_template, add_course, sounds;
	TextView settings_title;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
	    
	    Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font);
	    
	    use_tt_template = (TextView) findViewById(R.id.use_tt_templates_tv_stngp);
	    add_course = (TextView) findViewById(R.id.add_course_tv_stngp);
	    sounds = (TextView) findViewById(R.id.sounds_tv_stngp);
	    settings_title = (TextView) findViewById(R.id.settings_title_st);
	    
	    use_tt_template.setTypeface(comic_font);
	    add_course.setTypeface(comic_font);
	    sounds.setTypeface(comic_font);
	    settings_title.setTypeface(comic_font);
	    
	    settings_title.setPaintFlags(settings_title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	    
	    use_tt_template.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				BuildOnlyIITGJuntaAlert();
				
			}
		});
	    
	    add_course.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent add_course_screen = new Intent(Settings.this, SelectCoursePtNsoToAdd.class);
				startActivity(add_course_screen);
			}
		});
	    
	    sounds.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent sound_screen = new Intent(Settings.this, SoundSettings.class);
				startActivity(sound_screen);
			}
		});
	    
	}
	
	protected void BuildOnlyIITGJuntaAlert() {

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Please Note")
        .setMessage(getResources().getString(R.string.only_iitg_feature))
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        	Intent select_year_branch_screen = new Intent(Settings.this, SelectYearBranch.class);
			startActivity(select_year_branch_screen);
        }
        }).show();
	}

	@Override
    public void onBackPressed() {
		go_to_home();
	}

	private void go_to_home() {
		Intent home_screen = new Intent(Settings.this, AppHome.class);
		home_screen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_screen);
		finish();
	}

}
