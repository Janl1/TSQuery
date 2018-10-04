package de.janl1.tsquery.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.janl1.tsquery.R;

public class LoginFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText host, username, pw, port, qport, nickname, label;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        Button save = (Button)root.findViewById(R.id.login_save);

        pref = getActivity().getSharedPreferences("TSQUERY", Context.MODE_PRIVATE);
        editor = pref.edit();
        label = (EditText)root.findViewById(R.id.login_label);
        host = (EditText)root.findViewById(R.id.login_host);
        username = (EditText)root.findViewById(R.id.login_username);
        pw = (EditText)root.findViewById(R.id.login_password);
        port = (EditText)root.findViewById(R.id.login_port);
        qport = (EditText)root.findViewById(R.id.login_qport);
        nickname = (EditText)root.findViewById(R.id.login_nickname);


        if(pref.getString("serveredit_mode", "").equals("EDIT_SERVER"))
        {
            String server = pref.getString("selectedserver", "");
            String servers = pref.getString("serverconfig", "");

            try {
                JSONArray servers_arr = new JSONArray(servers);

                for (int i = 0; i < servers_arr.length(); i++){
                    JSONObject server_obj = new JSONObject(servers_arr.get(i).toString());
                    if((server_obj.getString("v_login_host") + ":" + server_obj.getString("v_login_port")+ ":" + server_obj.getString("v_login_qport")).equals(server))
                    {
                        label.setText(server_obj.getString("v_label"));
                        host.setText(server_obj.getString("v_login_host"));
                        username.setText(server_obj.getString("v_login_username"));
                        pw.setText(server_obj.getString("v_login_pw"));
                        port.setText(server_obj.getString("v_login_port"));
                        qport.setText(server_obj.getString("v_login_qport"));
                        nickname.setText(server_obj.getString("v_login_nickname"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveData(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    private void saveData(View view) throws JSONException {

        if(label.getText().toString().equals("") || host.getText().toString().equals("") || nickname.getText().toString().equals("")) {
            Snackbar.make(view, "Information missing!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(pref.getString("serveredit_mode", "").equals("EDIT_SERVER"))
        {
            String server = pref.getString("selectedserver", "");
            String servers = pref.getString("serverconfig", "");

            try {
                JSONArray servers_arr = new JSONArray(servers);

                for (int i = 0; i < servers_arr.length(); i++){
                    JSONObject server_obj = new JSONObject(servers_arr.get(i).toString());
                    if((server_obj.getString("v_login_host") + ":" + server_obj.getString("v_login_port")+ ":" + server_obj.getString("v_login_qport")).equals(server))
                    {
                        JSONObject newServerSettings = new JSONObject()
                                .put("v_label", label.getText().toString())
                                .put("v_login_host", host.getText().toString())
                                .put("v_login_username", username.getText().toString())
                                .put("v_login_pw", pw.getText().toString())
                                .put("v_login_port", port.getText().toString())
                                .put("v_login_qport", qport.getText().toString())
                                .put("v_login_nickname", nickname.getText().toString());

                        server_obj = newServerSettings;
                        servers_arr.put(i, server_obj);
                    }
                }

                editor.putString("serverconfig", servers_arr.toString());
                editor.commit();
                Snackbar.make(view, "Server edited!", Snackbar.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(view, "Something went wrong, try again!", Snackbar.LENGTH_SHORT).show();
            }

        } else {
            if(pref.getString("serverconfig","").equals("")) {
                String json = new JSONArray().put(new JSONObject()
                        .put("v_label", label.getText().toString())
                        .put("v_login_host", host.getText().toString())
                        .put("v_login_username", username.getText().toString())
                        .put("v_login_pw", pw.getText().toString())
                        .put("v_login_port", port.getText().toString())
                        .put("v_login_qport", qport.getText().toString())
                        .put("v_login_nickname", nickname.getText().toString())
                        .toString()).toString();
                System.out.println("[TSQUERY-LOG] SERVER ADD INIT :: " + json);
                editor.putString("serverconfig", json);
                editor.putString("selectedserver", host.getText().toString() + ":" + port.getText().toString() + ":" + qport.getText().toString());
                editor.commit();
            } else {
                JSONArray jsonArray = new JSONArray(pref.getString("serverconfig",""));
                jsonArray.put(new JSONObject()
                        .put("v_label", label.getText().toString())
                        .put("v_login_host", host.getText().toString())
                        .put("v_login_username", username.getText().toString())
                        .put("v_login_pw", pw.getText().toString())
                        .put("v_login_port", port.getText().toString())
                        .put("v_login_qport", qport.getText().toString())
                        .put("v_login_nickname", nickname.getText().toString())
                        .toString());
                System.out.println("[TSQUERY-LOG] SERVER ADD :: " + jsonArray.toString());
                editor.putString("serverconfig", jsonArray.toString());
                editor.commit();
            }

            Snackbar.make(view, "Server added!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
