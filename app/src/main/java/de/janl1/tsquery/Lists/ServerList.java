/*
 * Copyright (c) 2017.
 * Jan "Janl1" Lahmer
 *
 * Janl1.DE for team-hmsk.com
 */

package de.janl1.tsquery.Lists;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.janl1.tsquery.R;


public class ServerList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] nick;
    public ServerList(Activity context, String[] web) {
        super(context, R.layout.listview_clients, web);

        this.context = context;
        this.nick = web;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listviewserver, null, true);

        TextView label = (TextView) rowView.findViewById(R.id.server_label);
        TextView info = (TextView) rowView.findViewById(R.id.server_info);

        String[] split = nick[position].split("###");

        label.setText(split[0]);
        info.setText(split[1]);

        return rowView;
    }
}