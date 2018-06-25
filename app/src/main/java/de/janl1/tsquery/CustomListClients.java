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
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;


public class CustomListClients extends ArrayAdapter<ClientChannelObject>{

    private final Activity context;
    private final ClientChannelObject[] nick;

    public CustomListClients(Activity context, ClientChannelObject[] web) {
        super(context, R.layout.listview_clients, web);

        this.context = context;
        this.nick = web;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_clients, null, true);

        ClientChannelObject obj = nick[position];

        if (obj.isChannel())
        {
            Channel c = nick[position].getChannel();
            TextView channel = (TextView) rowView.findViewById(R.id.client_channel);
            TextView ip = (TextView) rowView.findViewById(R.id.client_ip);
            TextView client_uid = (TextView) rowView.findViewById(R.id.client_uid);
            TextView name = (TextView) rowView.findViewById(R.id.server_label);
            ImageView icon = (ImageView) rowView.findViewById(R.id.client_icon);


            ip.setText("");
            client_uid.setText("");
            name.setText(c.getName());
            //channel.setText(api.getChannelInfo(c.getChannelId()).getName());
            channel.setText("");

            icon.setImageResource(R.mipmap.ic_normal);
            icon.setImageAlpha(255);

        } else {

            Client c = nick[position].getClient();
            TextView channel = (TextView) rowView.findViewById(R.id.client_channel);
            TextView ip = (TextView) rowView.findViewById(R.id.client_ip);
            TextView client_uid = (TextView) rowView.findViewById(R.id.client_uid);
            TextView name = (TextView) rowView.findViewById(R.id.server_label);
            ImageView icon = (ImageView) rowView.findViewById(R.id.client_icon);


            ip.setText(c.getPlatform() + " | " + c.getCountry());
            client_uid.setText(c.getUniqueIdentifier());
            name.setText(c.getNickname());
            //channel.setText(api.getChannelInfo(c.getChannelId()).getName());
            channel.setText(c.channel_name);

            icon.setImageResource(R.mipmap.ic_normal);

            if(c.isInputMuted()) {
                icon.setImageResource(R.mipmap.ic_mic_muted);
            }

            if(c.isOutputMuted()) {
                icon.setImageResource(R.mipmap.ic_sound_muted);
            }

            if(!c.isInputHardware()) {
                icon.setImageResource(R.mipmap.ic_mic_off);
            }

        }

        return rowView;
    }
}