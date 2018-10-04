package de.janl1.tsquery.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.janl1.tsquery.Lists.ServerList;
import de.janl1.tsquery.R;

public class ServerFragment extends Fragment {

    FragmentManager fm;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public ServerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pref = getActivity().getSharedPreferences("TSQUERY", Context.MODE_PRIVATE);
        editor = pref.edit();
        fm = getFragmentManager();
        final View root;

        if(pref.getString("serverconfig", "").equals("")){
            root = inflater.inflate(R.layout.fragment_noservers, container, false);

            Button addServer = (Button)root.findViewById(R.id.button);

            addServer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().setTitle("TSQuery | Add server");
                    fm.beginTransaction().replace(R.id.content_frame, new LoginFragment()).commit();
                }
            });

        } else {
            root = inflater.inflate(R.layout.fragment_servers, container, false);
            initServerLoading(root);
        }

        return root;
    }

    private void initServerLoading(View view) {
        if(!pref.getString("serverconfig", "").equals("")) {
            ArrayList<String> serverview = new ArrayList<String>();
            ListView listv = (ListView)view.findViewById(R.id.list);
            try {
                System.out.println("RAW_JSON_ARRAY: " + pref.getString("serverconfig", ""));
                JSONArray servers = new JSONArray(pref.getString("serverconfig", ""));

                for (int i = 0; i < servers.length(); i++)
                {
                    JSONObject server = new JSONObject(servers.getString(i));
                    serverview.add(server.getString("v_label") + "###" + server.getString("v_login_host") + ":" + server.getString("v_login_port") + ":" + server.getString("v_login_qport"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String[] serverslist = serverview.toArray(new String[0]);
            final ServerList adapter = new ServerList(getActivity(), serverslist);
            listv.setAdapter(adapter);

            listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    editor.putString("selectedserver",serverslist[i].split("###")[1]);
                    editor.commit();
                    fm.beginTransaction().replace(R.id.content_frame, new UsersFragment()).commit();

                    return false;
                }
            });

            listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                    String[] options = new String[]{"Select","Edit","Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(serverslist[position].split("###")[0])
                            .setItems(options, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which)
                                    {
                                        case 0:
                                            editor.putString("selectedserver",serverslist[position].split("###")[1]);
                                            editor.commit();
                                            Snackbar.make(view, "Server selected!", Snackbar.LENGTH_SHORT).show();
                                            break;
                                        case 1:
                                            editor.putString("selectedserver",serverslist[position].split("###")[1]);
                                            editor.putString("serveredit_mode", "EDIT_SERVER");
                                            editor.commit();
                                            fm.beginTransaction().replace(R.id.content_frame, new LoginFragment()).commit();

                                            break;
                                        case 2:
                                            deleteConfig(serverslist[position].split("###")[1]);
                                            break;
                                    }
                                }
                            });
                    builder.create().show();
                }
            });
        }
    }

    private void deleteConfig(final String ident)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteConfigAction(ident);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    private void deleteConfigAction(String ident)
    {
        try {
            JSONArray servers = new JSONArray(pref.getString("serverconfig", ""));

            for (int i = 0; i < servers.length(); i++)
            {
                JSONObject server = new JSONObject(servers.getString(i));
                if(ident.equals(server.getString("v_login_host") + ":" + server.getString("v_login_port") + ":" + server.getString("v_login_qport"))) {
                    servers.remove(i);


                    editor.putString("serverconfig",servers.toString());
                    editor.commit();

                    Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "Server deleted!", Snackbar.LENGTH_SHORT).show();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        initServerLoading(getActivity().getWindow().getDecorView().findViewById(android.R.id.content));
    }
}
