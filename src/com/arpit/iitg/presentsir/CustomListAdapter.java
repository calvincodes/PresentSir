package com.arpit.iitg.presentsir;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    private Context mContext;
    private int id;
    private List <String>items ;
    
    Typeface comic_font;

    public CustomListAdapter(Context context, int textViewResourceId , ArrayList<String> list ) 
    {
        super(context, textViewResourceId, list);
        comic_font = Typeface.createFromAsset(context.getAssets(), "fonts/Action_Man.ttf");
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.tv_for_custom_adapter_tvfca);

        if(items.get(position) != null )
        {
        	text.setText(items.get(position));
        	text.setTypeface(comic_font);
        }

        return mView;
    }

}
