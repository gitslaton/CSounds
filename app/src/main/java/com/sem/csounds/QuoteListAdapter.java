package com.sem.csounds;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Slaton on 5/22/2017.
 */

public class QuoteListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> quoteList;
    private ArrayList<Integer> soundIdList;
    private Cursor cursor;
    Context mContext;

    private static class ViewHolder {
        TextView quote;
        ImageButton button;
    }

    public QuoteListAdapter(ArrayList<String> data, ArrayList<Integer> soundIdList, Context context) {
        super(context, R.layout.quote_item, data);
        this.quoteList = data;
        this.soundIdList = soundIdList;
        this.mContext = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.quote_item, null);
        }

        String quote = getItem(position);
        TextView quote_text = (TextView)v.findViewById(R.id.quote_text);
        ImageButton button = (ImageButton)v.findViewById(R.id.image_button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.e("CLICKED", "CLICKED SO GOOD");
                MediaPlayer player = MediaPlayer.create(mContext, soundIdList.get(position));
                player.start();
            }
        });

        quote_text.setText(quote);

        return v;
    }

}
