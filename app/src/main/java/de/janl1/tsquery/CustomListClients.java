/*
 * Copyright (c) 2017.
 * Jan "Janl1" Lahmer
 *
 * Janl1.DE for team-hmsk.com
 */

package de.janl1.tsquery;

import android.app.Activity;
import android.view.Gravity;
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

    private static class ViewHolderChannel {
        TextView name;
    }

    private static class ViewHolderClient {
        TextView name;
        TextView client_uid;
        TextView ip;
        ImageView icon;

    }

    public CustomListClients(Activity context, ClientChannelObject[] web) {
        super(context, R.layout.listview_clients, web);

        this.context = context;
        this.nick = web;

    }

    @Override
    public int getItemViewType(int position) {

        if(getItem(position).isChannel()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        ClientChannelObject obj = nick[position];

        if (obj.isChannel())
        {
            ViewHolderChannel vChannel;
            View v = rowView;
            if(v == null)
            {
                v= LayoutInflater.from(getContext()).inflate(R.layout.listview_clientschannel, null);
                vChannel = new ViewHolderChannel();
                vChannel.name = (TextView) v.findViewById(R.id.channel_name);
                v.setTag(vChannel);
            } else {
                vChannel = (ViewHolderChannel) v.getTag();
            }


            Channel c = nick[position].getChannel();
            String channelname = c.getName();
            if(channelname.contains("[cspacer"))
            {
                channelname = channelname.substring(channelname.indexOf("]")+1, channelname.length());
                vChannel.name.setGravity(Gravity.CENTER);
            }
            if(channelname.contains("[spacer"))
            {
                channelname = " ";
                vChannel.name.setGravity(Gravity.CENTER);
            }
            if (channelname.contains("[*spacer") || channelname.contains("[*cspacer"))
            {
                String channelnamepart = channelname.substring(channelname.indexOf("]")+1, channelname.length());
                String channelnamefinal = "";
                for (int i = 0; i < 10; i++) {
                    channelnamefinal += channelnamepart;
                }
                channelname = channelnamefinal;
                vChannel.name.setGravity(Gravity.CENTER);
            }
            vChannel.name.setText(channelname);
            return v;
        } else {

            ViewHolderClient vClient;
            View v = rowView;
            if(v == null)
            {
                v= LayoutInflater.from(getContext()).inflate(R.layout.listview_clients, null);
                vClient = new ViewHolderClient();

                vClient.ip = (TextView) v.findViewById(R.id.client_ip);
                vClient.client_uid = (TextView) v.findViewById(R.id.client_uid);
                vClient.name = (TextView) v.findViewById(R.id.server_label);
                vClient.icon = (ImageView) v.findViewById(R.id.client_icon);

                v.setTag(vClient);
            } else {
                vClient = (ViewHolderClient) v.getTag();
            }


            Client c = nick[position].getClient();



            vClient.ip.setText(c.getPlatform() + " | " + c.getCountry());
            vClient.client_uid.setText(c.getUniqueIdentifier());
            vClient.name.setText(c.getNickname());

            vClient.icon.setImageResource(R.mipmap.ic_normal);

            if(c.isInputMuted()) {
                vClient.icon.setImageResource(R.mipmap.ic_mic_muted);
            }

            if(c.isOutputMuted()) {
                vClient.icon.setImageResource(R.mipmap.ic_sound_muted);
            }

            if(!c.isInputHardware()) {
                vClient.icon.setImageResource(R.mipmap.ic_mic_off);
            }
            return v;
        }
    }
}
