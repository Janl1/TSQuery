package de.janl1.tsquery;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UsersFragment extends Fragment {

    FragmentManager fm;
    SharedPreferences pref;
    ProgressDialog pdiag;
    public UsersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_users, container, false);

        pref = getActivity().getSharedPreferences("TSQUERY", Context.MODE_PRIVATE);
        fm = getFragmentManager();

        pdiag = new ProgressDialog(getActivity());
        pdiag = new ProgressDialog(getActivity());
        pdiag.setTitle("Please wait");
        pdiag.setMessage("Loading data ...");
        pdiag.setCancelable(false);
        pdiag.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initClientLoading(root);
                } catch (TS3ConnectionFailedException ex) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("TSQuery stopped working!\n\nError message: " + ex.getMessage() + "\n\nPlease try again later or check the credential configuration of the server.").setTitle("An error occured")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                pdiag.dismiss();
                            }catch (Exception e){}
                            builder.create().show();
                        }
                    });
                }

            }
        }).start();



        return root;
    }

    private void initClientLoading(View view) {

        if(!pref.getString("selectedserver", "").equals("")) {

            String HOST = "";
            String USER = "";
            String PASSWORD = "";
            String NICKNAME = "";
            int PORT = 0;
            int QPORT = 0;

            try {
                String[] tagetServer = pref.getString("selectedserver", "").split(":");
                JSONArray serverList = new JSONArray(pref.getString("serverconfig","").toString());

                for(int i = 0; i < serverList.length(); i++) {
                    JSONObject objs = new JSONObject(serverList.get(i).toString());

                    if(objs.getString("v_login_host").equals(tagetServer[0]) && objs.getString("v_login_port").equals(tagetServer[1])) {
                        HOST = objs.getString("v_login_host");
                        USER = objs.getString("v_login_username");
                        PASSWORD = objs.getString("v_login_pw");
                        NICKNAME = objs.getString("v_login_nickname");
                        PORT = Integer.parseInt(objs.getString("v_login_port"));
                        QPORT = Integer.parseInt(objs.getString("v_login_qport"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            System.out.println("[SERVER_CONFIG] " + HOST + " " + USER + " " + PASSWORD + " " + PORT + " " + QPORT);
            final ListView listv = (ListView) view.findViewById(R.id.list);
            final TS3Config config = new TS3Config();
            config.setHost(HOST);
            config.setDebugLevel(Level.WARNING);
            config.setLoginCredentials(USER, PASSWORD);
            config.setQueryPort(QPORT);
            final TS3Query query = new TS3Query(config);
            query.connect();

            final TS3Api api = query.getApi();
            api.selectVirtualServerByPort(PORT);
            api.setNickname(NICKNAME);

            ArrayList<String> clients = new ArrayList<String>();
            for (Client c : api.getClients()) {

                if(c.getNickname().equals(NICKNAME)) {
                    continue;
                }

                String status = "NORMAL";
                if(c.isInputMuted()) {
                    status = "MIC_MUTED";
                }

                if(c.isOutputMuted()) {
                    status = "SOUND_MUTED";
                }

                if(!c.isInputHardware()) {
                    status = "NO_MIC";
                }

                System.out.println("[TSQUERY-LOG] " + c.getNickname() + "###" + api.getChannelInfo(c.getChannelId()).getName() + "###" + status);
                clients.add(c.getNickname() + "###" + api.getChannelInfo(c.getChannelId()).getName() + "###" + status);
            }

            final CustomList adapter = new CustomList(getActivity(), clients.toArray(new String[0]));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listv.setAdapter(adapter);
                    pdiag.dismiss();
                }
            });
            query.exit();

        } else {
            fm.beginTransaction().replace(R.id.content_frame, new ServerFragment()).commit();
            getActivity().setTitle("TSQuery | Servers");
        }

    }

}
