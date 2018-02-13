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

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;


public class CustomListChannels extends ArrayAdapter<Channel>{

    private final Activity context;
    private final Channel[] nick;

    TS3Api api = null;

    public CustomListChannels(Activity context, Channel[] web, TS3Api api) {
        super(context, R.layout.listview_channels, web);

        this.api = api;
        this.context = context;
        this.nick = web;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_channels, null, true);
        Channel c = nick[position];

        TextView channel = (TextView) rowView.findViewById(R.id.channel_name);

        ImageView icon = (ImageView) rowView.findViewById(R.id.client_icon);


        if(api == null) {
            System.out.println("API IS NULL");
        }


        channel.setText(c.getName());

        icon.setImageResource(R.mipmap.ic_normal);

        return rowView;
    }
}