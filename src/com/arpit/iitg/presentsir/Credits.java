package com.arpit.iitg.presentsir;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Credits extends Activity {
	
	TextView title, designed_developed, name_year_insti, disclaimer, foot_note;
	Button send;
	
	String[] toArr = {"arpitjain1821@gmail.com"};
	String body = "Dear Mr. Arpit\n\n";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.credits_page);
	    
	    android.app.ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		Drawable action_bar_bg = getResources().getDrawable(R.drawable.sides_chalkboard);
		actionBar.setBackgroundDrawable(action_bar_bg);
		
		Typeface comic_font_ab = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");

		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
	    TextView app_title_tv = (TextView) findViewById(titleId);
	    app_title_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	    app_title_tv.setTypeface(comic_font_ab);
		
		initalize();
	    
	    send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
				emailIntent.setType("message/rfc822");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, toArr); 
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,getResources().getString(R.string.email_subject)); 
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); 
				
				final PackageManager pm = getPackageManager();
			    final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
			    ResolveInfo best = null;
			    for(final ResolveInfo info : matches)
			        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
			            best = info;
			    if (best != null){	// If Gmail app is available, open it directly.
			        emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
			        startActivityForResult(emailIntent,0);
			    }else{	// Otherwise, provide option of available email apps.
			    	startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."),0);
			    }
			}
		});
	}

	private void initalize() {

		Typeface comic_font = Typeface.createFromAsset(getAssets(),"fonts/Action_Man.ttf");
		
		title = (TextView) findViewById(R.id.credits_title);
		designed_developed = (TextView) findViewById(R.id.app_developer_designer_cp);
		name_year_insti = (TextView) findViewById(R.id.my_name_cp);
		disclaimer = (TextView) findViewById(R.id.disclaimer_msg);
		foot_note = (TextView) findViewById(R.id.feedback_msg_cp);
		
		send = (Button) findViewById(R.id.send_feedback_b_cp);
		
		title.setTypeface(comic_font);
		designed_developed.setTypeface(comic_font);
		name_year_insti.setTypeface(comic_font);
		disclaimer.setTypeface(comic_font);
		foot_note.setTypeface(comic_font);
		send.setTypeface(comic_font);
		
		title.setPaintFlags(title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
		
		disclaimer.setText(getResources().getString(R.string.disclaimer_msg));
		
	}

}
