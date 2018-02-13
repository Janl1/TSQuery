package de.janl1.tsquery;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;

public class ChannelsFragment extends Fragment {

    FragmentManager fm;
    SharedPreferences pref;
    ProgressDialog pdiag;
    TS3Config config = null;
    TS3Query query = null;
    TS3Api api = null;
    ListView listv = null;

    String HOST = "";
    String USER = "";
    String PASSWORD = "";
    String NICKNAME = "";
    int PORT = 0;
    int QPORT = 0;

    public ChannelsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_channels, container, false);

        pref = getActivity().getSharedPreferences("TSQUERY", Context.MODE_PRIVATE);
        fm = getFragmentManager();
        listv = (ListView) root.findViewById(R.id.list);

        pdiag = new ProgressDialog(getActivity());
        pdiag.setTitle("Please wait");
        pdiag.setMessage("Loading data ...");
        pdiag.setCancelable(false);
        pdiag.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initChannelLoading(root);
                } catch (TS3ConnectionFailedException ex) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("TSQuery stopped working!\n\nError message: " + ex.getMessage() + "\n\nPlease try again later or check the credential configuration of the server.").setTitle("An error occured")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                }

            }
        }).start();



        return root;
    }

    private void initChannelLoading(View view) {
        if(!pref.getString("selectedserver", "").equals("")) {

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



            final Channel[][] clientslist = new Channel[1][1];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(initTeamSpeakAPI()){
                        ArrayList<Channel> clients = new ArrayList<Channel>();
                        for (Channel c : api.getChannels()) {

                            clients.add(c);
                        }

                        clientslist[0] = clients.toArray(new Channel[0]);

                        //sorting(clientslist[0], 0, clientslist[0].length -1);

                        final CustomListChannels adapter = new CustomListChannels(getActivity(), clientslist[0], api);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listv.setAdapter(adapter);
                                pdiag.dismiss();
                            }
                        });
                        query.exit();
                    }
                }
            }).start();

        } else {
            fm.beginTransaction().replace(R.id.content_frame, new ServerFragment()).commit();
            getActivity().setTitle("TSQuery | Servers");
        }
    }

    private boolean initTeamSpeakAPI()
    {
        try {
            config = new TS3Config();
            config.setHost(HOST);
            config.setDebugLevel(Level.WARNING);
            config.setLoginCredentials(USER, PASSWORD);
            config.setQueryPort(QPORT);
            query = new TS3Query(config);
            query.connect();

            api = query.getApi();
            api.selectVirtualServerByPort(PORT);
            api.setNickname(NICKNAME);
            return true;

        } catch (com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException ex)
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        pdiag.dismiss();
                    } catch(Exception e){}

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Could not establish connection to TeamSpeak server! Check the query credentials and try again!")
                            .setTitle("Application error")
                            .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create().show();
                }
            });

            return false;
        }

    }

    public void sorting(Channel array[], int start, int end) {
        int i = start;
        int k = end;
        if (end - start >= 1) {
            String pivot = array[start].getName();
            while (k > i) {
                while (array[i].getName().toUpperCase().compareTo(pivot.toUpperCase()) <= 0 && i <= end && k > i)
                    i++;
                while (array[k].getName().toUpperCase().compareTo(pivot.toUpperCase()) > 0 && k >= start && k >= i)
                    k--;
                if (k > i)
                    swap(array, i, k);
            }
            swap(array, start, k);
            sorting(array, start, k - 1);
            sorting(array, k + 1, end);
        } else { return; }
    }
    public void swap(Channel array[], int index1, int index2) {
        Channel temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
