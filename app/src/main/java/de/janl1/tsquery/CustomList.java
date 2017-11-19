/*
 * Copyright (c) 2017.
 * Jan "Janl1" Lahmer
 *
 * Janl1.DE for team-hmsk.com
 */

package de.janl1.tsquery;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] nick;
    public CustomList(Activity context, String[] web) {
        super(context, R.layout.listview, web);

        this.context = context;
        this.nick = web;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview, null, true);

        TextView channel = (TextView) rowView.findViewById(R.id.client_channel);
        TextView name = (TextView) rowView.findViewById(R.id.server_label);
        ImageView icon = (ImageView) rowView.findViewById(R.id.client_icon);

        String[] split = nick[position].split("###");

        name.setText(split[0]);
        channel.setText(split[1]);

        if(split[2].equals("MIC_MUTED")) {
            icon.setImageResource(R.mipmap.ic_mic_muted);
        } else if (split[2].equals("SOUND_MUTED")) {
            icon.setImageResource(R.mipmap.ic_sound_muted);
        } else if (split[2].equals("NO_MIC")) {
            icon.setImageResource(R.mipmap.ic_mic_off);
        } else if (split[2].equals("NORMAL")) {
            icon.setImageResource(R.mipmap.ic_normal);
        }

        return rowView;
    }
}